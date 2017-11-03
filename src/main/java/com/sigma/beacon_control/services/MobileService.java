package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.common.util.JwtManager;
import com.sigma.beacon_control.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.sql.Timestamp;

/**
 * @author Ing. Wilson Garcia
 * Created on 10/29/17
 */
public class MobileService extends Service {

    private static final String MOBILE_PREFIX = "/mobile";
    private static final Logger log = LogManager.getLogger(UserService.class);

    private static Route createOAuthRoute = (Request request, Response response) -> {
        log.debug("****************** OAuth Login");
        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        String clientId = (String) requestJson.get("client_id");
        String clientSecret = (String) requestJson.get("client_secret");
        long userId = (long) requestJson.get("user_id");
        String os = (String) requestJson.get("os");
        String environment = (String) requestJson.get("environment");
        String pushToken = (String) requestJson.get("push_token");

        MobileDevice user = new ConnectionWrapper<MobileDevice>().wrap(() -> {
            MobileDevice tempUser = MobileDevice.findFirst("user_id = ?", userId);
            if (tempUser != null)
                return tempUser;
            else
                return new MobileDevice();
        });

        boolean exist = new ConnectionWrapper<Boolean>().wrap(() ->
                OauthApplication.findFirst("uid = ? AND secret = ?",
                        clientId, clientSecret) != null);

        if (exist) {
            String token = JwtManager.generateToken(clientSecret.concat(clientId), userId);
            OperationSupport.addObjToSession(request, "X-API-TOKEN", token);
            response.header("X-API-TOKEN", token);

            boolean userOperationSucceded = new TransactionWrapper<Boolean>().wrap(() -> {
                user.setActive(true);
                user.setEnvironment("sandbox".equals(environment) ? 0 : 1);
                user.setOS("android".equals(os) ? 0 : 1);
                user.setLastSignInAt(new Timestamp(System.currentTimeMillis()));
                user.setUserId((int) userId);
                user.setPushToken(pushToken);


                return user.save();
            });

            if (userOperationSucceded)
                return getReturnJson("Sucess", 200, token);
            else
                return getReturnJson("Error updating mobile user", 500);
        }

        return getReturnJson("Application not found", 401);
    };

    private static Route getConfigurationsRoute = (Request request, Response response) -> {
        log.debug("****************** Getting configurations");

        long userId = OperationSupport.getLongFromParam(request, "user_id");
        User user = new ConnectionWrapper<User>().wrap(() ->
                User.findById(userId)
        );

        if(user != null){
            String configurationJson = new ConnectionWrapper<String>().wrap(() -> {
                LazyList<Trigger> triggers = Trigger.where("application_id = ?", user.getApplicationId())
                        .include(TriggerSource.class);

                LazyList<Activity> activities = Activity.findBySQL("select activities.* from activities, " +
                        "triggers where activities.id = triggers.activity_id AND triggers.application_id = ? " +
                        "order by activities.scheme", user.getApplicationId()).include(CustomAttribute.class);

                LazyList<ApplicationZone> zones = ApplicationZone.findBySQL("select zones.*, " +
                        "beacons.id as \"beacon_id\" from applications_zones\n" +
                        "inner join zones on applications_zones.zone_id = zones.id\n" +
                        "inner join beacons on zones.id = beacons.zone_id\n" +
                        "where applications_zones.application_id = ?", user.getApplicationId());

                LazyList<Beacon> beacons = Beacon.findBySQL("select beacons.* from applications_beacons\n" +
                        "inner join beacons on applications_beacons.beacon_id = beacons.id\n" +
                        "where applications_beacons.application_id = ?", user.getApplicationId());



                JSONObject configurationObject = new JSONObject();
                try {
                    configurationObject.put("triggers", getParser().parse(triggers.toJson(false)));
                    configurationObject.put("actions", getParser().parse(activities.toJson(false)));
                    configurationObject.put("zones", getParser().parse(zones.toJson(false)));
                    configurationObject.put("beacons", getParser().parse(beacons.toJson(false)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return configurationObject.toJSONString();
            });

            return getReturnJson("Sucess", 200, configurationJson);
        }
        return getReturnJson("Error getting configurations", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Mobile Routes");
        Spark.path(AppFilter.PREFIX.concat(MOBILE_PREFIX), () -> {
            Spark.post("/oauth/token", createOAuthRoute);
            Spark.get("/get-configurations", getConfigurationsRoute);

            Spark.path("/secure", () -> {
                Spark.before("/*", AppFilter.addAuthenticationFilter);

            });

        });
    }
}
