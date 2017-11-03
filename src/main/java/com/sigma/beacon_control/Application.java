package com.sigma.beacon_control;

import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.common.util.Config;
import com.sigma.beacon_control.common.util.DataSource;
import com.sigma.beacon_control.model.Account;
import com.sigma.beacon_control.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javalite.activejdbc.Base;
import spark.Spark;

/**
 * Created by Wilson on 4/17/17.
 */
public class Application {
    private static final Logger log = LogManager.getLogger(Application.class);
    private static final int MAX_THREAD = Config.getInt("app.max-thread");
    private static final int PORT = Config.getInt("app.port");

    public static void main(String... args) {
        log.info("*************** Starting Application ***************");
        Spark.threadPool(MAX_THREAD);
        Spark.port(PORT);

        Spark.exception(Exception.class, ((exception, request, response) -> {
            exception.printStackTrace();

            response.status(500);
            response.body("Internal Server Error. Please contact your administrator.");
        }));

        Spark.before("*", AppFilter.addJsonContentType);
        Spark.before("*", AppFilter.addCorsHeaders);

        UserService.loadRoutes();
        BeaconService.loadRoutes();
        ZoneService.loadRoutes();
        ActivityService.loadRoutes();
        MapService.loadRoutes();
        ApplicationService.loadRoutes();
        MobileService.loadRoutes();
    }
}
