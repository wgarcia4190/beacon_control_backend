package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.common.util.SecretUtility;
import com.sigma.beacon_control.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class ApplicationService extends Service {

    private static final String APP_PREFIX = "/app/secure";
    private static final Logger log = LogManager.getLogger(ApplicationService.class);

    private static Route getApplications = (Request request, Response response) -> {
        log.debug("**************** Loading Applications");

        int accountId = (int) OperationSupport.
                getAndVerifyObjFromSession(request, "accountId");

        String applicationsJson = new ConnectionWrapper<String>().wrap(() -> {
            LazyList<Application> applications = Application.where("account_id = ?", accountId);
            return applications.toJson(false);
        });

        return getReturnJson("Success", 200, applicationsJson);
    };

    private static Route getApplicationById = (Request request, Response response) ->{
        int applicationId = OperationSupport.getIntFromUrl(request, "application_id");

        String applicationJson = new ConnectionWrapper<String>().wrap(()->{
            Application application = Application.findById(applicationId);
            OauthApplication credentials = OauthApplication.findFirst("application_id = ?", (Integer)application.getId());
            LazyList<Activity> activities = Activity.findBySQL("select activities.* from activities, triggers " +
                    "where activities.id = triggers.activity_id AND triggers.application_id = ? order by activities.scheme", applicationId);

            JSONObject applicationObject = new JSONObject();
            applicationObject.put("application", application.toJson(false));
            applicationObject.put("credentials", credentials.toJson(false));
            applicationObject.put("activities", activities.toJson(false));

            return applicationObject.toJSONString();
        });

        return getReturnJson("Success", 200, applicationJson);
    };

    private static Route createApplication = (Request request, Response response) -> {
        log.debug("**************** Creating Application");

        int accountId = (int) OperationSupport.
                getAndVerifyObjFromSession(request, "accountId");

        Application application = new Application();
        application.fromMap(OperationSupport.getBean(request.body()));
        application.setAccountId(accountId);
        application.setDefaultApp(false);

        boolean isSaved = new TransactionWrapper<Boolean>().wrap(application::save);
        if(isSaved) {
            new TransactionWrapper<Boolean>().wrap(()->{
                OauthApplication oauthApplication = new OauthApplication();
                oauthApplication.setApplicationId((Integer) application.getId());
                oauthApplication.setName(application.getName());
                oauthApplication.setUid(SecretUtility.generateSecretUID());
                oauthApplication.setSecret(SecretUtility.generateSecretUID());
                oauthApplication.setRedirectUri(" ");

                return oauthApplication.save();
            });
            return getReturnJson("Success", 200, application.getId().toString());
        }
        return getReturnJson("Application couldn't be saved", 500);

    };

    private static Route deleteApplication = (Request request, Response response) -> {
        log.debug("**************** Deleting Application");

        int applicationId = OperationSupport.getIntFromUrl(request, "application_id");
        StringBuilder activitiesId = new StringBuilder();

        new ConnectionWrapper<Void>().wrap(()-> {
            LazyList<Trigger> triggers = Trigger.find("application_id = ?", applicationId);

            if(!triggers.isEmpty()) {
                for (Trigger trigger : triggers) {
                    activitiesId.append(trigger.getActivityId()).append(",");
                }
                activitiesId.deleteCharAt(activitiesId.lastIndexOf(","));
            }

            return null;
        });

        boolean isDeleted = new TransactionWrapper<Boolean>().wrap(() -> {
            try {
                Application application = Application.findById(applicationId);
                application.deleteCascadeExcept(Application.getMetaModel().getAssociationForTarget(Beacon.class));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });

        if (isDeleted) {
            if(activitiesId.length() > 0) {
                new TransactionWrapper<Integer>().wrap(() -> {
                    CustomAttribute.delete("activity_id IN (" + activitiesId.toString() + ")");
                    return Activity.delete("id IN (" + activitiesId.toString() + ")");
                });
            }
            return getReturnJson("Sucess", 200);
        }
        return getReturnJson("Application couldn't be deleted", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Map Routes");
        Spark.path(AppFilter.PREFIX.concat(APP_PREFIX), () -> {
            Spark.before("/*", AppFilter.addAuthenticationFilter);

            Spark.get("/get-applications", getApplications);
            Spark.get("/get-application/:application_id", getApplicationById);
            Spark.put("/save-application", createApplication);
            Spark.delete("/delete-application/:application_id", deleteApplication);

        });
    }
}
