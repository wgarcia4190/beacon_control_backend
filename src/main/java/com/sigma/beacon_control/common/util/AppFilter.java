package com.sigma.beacon_control.common.util;

import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.util.exceptions.OperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * Created by Wilson on 4/16/17.
 */
public final class AppFilter {

    private static final Logger log = LogManager.getLogger(AppFilter.class);
    public static final String PREFIX = "/api/v1/beacon-control";

    private AppFilter() {
    }

    public static final Filter addAuthenticationFilter = (Request request, Response response) -> {
        log.debug(">> appFilter");
        try {
            String authHeader = request.headers("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer "))
                throw new OperationException("Unauthorized Request", 401);

            authHeader = authHeader.substring("Bearer ".length());
            String actualUserToken = (String) OperationSupport.getAndVerifyObjFromSession(request, "X-API-TOKEN");

            if (actualUserToken != null && !actualUserToken.isEmpty()) {
                if (!actualUserToken.equals(authHeader)) {
                    throw new OperationException("Token not valid for this session", 401);
                }
            }

            boolean isTokenValid = JwtManager.isTokenValid(authHeader);
            if (!isTokenValid) {
                throw new OperationException("Invalid token", 401);
            }
        } catch (OperationException ex) {
            Spark.halt(ex.getCode(), ex.getMessage());
        }
    };

    public static final Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };

    public static final Filter addJsonContentType = (Request request, Response response) -> {
        response.type("application/json");
    };

    public static final Filter addCorsHeaders = (Request request, Response response) -> {
        response.header("Access-Control-Allow-Methods", "GET,PATCH,PUT,POST,DELETE,OPTIONS");
        response.header("Access-Control-Allow-Origin", Config.getString("app.allow.origin"));
        response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Authentication, " +
                "Content-Length, Accept, Origin, X-API-TOKEN");
        response.header("Access-Control-Allow-Credentials", "true");
        response.header("Access-Control-Expose-Headers", "X-API-TOKEN");

        if (request.requestMethod().equalsIgnoreCase("OPTIONS")) {
            log.debug("### Preflight request processed");
            HttpStatus status = HttpStatus.OK;
            Spark.halt(status.getCode(), status.getDesc());
        }
    };

    public static void printHeaders(Request request) {
        log.debug("### headers:");
        request.headers().forEach((header) -> {
            log.debug("###".concat(header).concat(":").concat(request.headers(header)));
        });

    }
}
