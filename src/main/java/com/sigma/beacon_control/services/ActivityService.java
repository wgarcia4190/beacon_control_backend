package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.core.value.LazyValueMap;
import org.boon.core.value.ValueList;
import org.boon.json.JsonFactory;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Map;

import static com.sigma.beacon_control.common.operation.OperationSupport.getBean;

public class ActivityService extends Service {

    private static final String ACTIVITY_PREFIX = "/activity/secure";
    private static final Logger log = LogManager.getLogger(ActivityService.class);

    private static Route createActivity = (Request request, Response response) -> {
        log.debug("**************** Creating Activity");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        Activity activity = new Activity();
        boolean isSaved = new TransactionWrapper<Boolean>().wrap(() -> {
            activity.fromMap(getBean(request.body()));
            return activity.save();
        });

        if (isSaved) {
            new TransactionWrapper<Void>().wrap(() -> {
                if(requestJson.containsKey("attributes")) {
                    ValueList customAttributes = (ValueList) JsonFactory.fromJson(requestJson.get("attributes").toString());

                    CustomAttribute.delete("activity_id = ?", (Integer) activity.getId());

                    for (Object customAttributeJson : customAttributes) {
                        CustomAttribute customAttribute = new CustomAttribute();
                        customAttribute.fromMap((LazyValueMap) customAttributeJson);

                        customAttribute.setActivityId((Integer) activity.getId());
                        customAttribute.save();
                    }
                }

                Map<String, Object> triggerMap = getBean(requestJson.get("trigger").toString());
                Trigger trigger = new Trigger();
                trigger.fromMap(triggerMap);
                trigger.setActivityId((Integer) activity.getId());
                trigger.save();

                TriggerSource.delete("trigger_id = ? ", (Integer)trigger.getId());

                ValueList triggerSources = (ValueList) JsonFactory.fromJson(triggerMap.get("sources").toString());
                for(Object triggerSourceJson: triggerSources){
                    TriggerSource triggerSource = new TriggerSource();
                    triggerSource.fromMap((LazyValueMap) triggerSourceJson);

                    triggerSource.setTriggerId((Integer) trigger.getId());
                    triggerSource.save();

                    if(triggerSource.getSourceType().equals("Beacon")) {
                        ApplicationBeacon applicationBeacon = new ApplicationBeacon();
                        applicationBeacon.setBeaconId(triggerSource.getSourceId());
                        applicationBeacon.setApplicationId(trigger.getApplicationId());
                        applicationBeacon.save();
                    }
                }


                return null;
            });
            return getReturnJson("Sucess", 200, activity.getId().toString());
        }
        return getReturnJson("Activity could'nt be saved", 422);
    };

    private static Route saveCoupon = (Request request, Response response) -> {
        log.debug("**************** Creating Coupon");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        Coupon coupon = new Coupon();
        boolean isSaved = new TransactionWrapper<Boolean>().wrap(() -> {
            coupon.fromMap(getBean(request.body()));
            return coupon.save();
        });

        if (isSaved)
            return getReturnJson("Sucess", 200);
        return getReturnJson("Coupon could'nt be saved", 422);
    };

    private static Route getActivityById = (Request request, Response response) -> {
        log.debug("**************** Getting Activity");

        int activityId = OperationSupport.getIntFromUrl(request, "activity_id");

        String activityJson = new ConnectionWrapper<String>().wrap(()->{
            Activity activity = (Activity) Activity.where("id = ?", activityId).include(CustomAttribute.class).get(0);
            Trigger trigger = (Trigger) Trigger.find("activity_id = ?", activityId).include(TriggerSource.class).get(0);
            LazyList<TriggerSource> triggerSources = TriggerSource.find("trigger_id = ?", trigger.getId());


            StringBuilder idList = new StringBuilder();
            for(TriggerSource triggerSource: triggerSources){
                idList.append(triggerSource.getSourceId()+",");
            }
            idList.deleteCharAt(idList.lastIndexOf(","));

            JSONObject activityObject = new JSONObject();
            activityObject.put("activity", activity.toJson(false));
            activityObject.put("trigger", trigger.toJson(false));
            activityObject.put("trigger_sources", triggerSources.toJson(false));

            if("BeaconTrigger".equals(trigger.getType())){
                LazyList<Beacon> beacons = Beacon.where("id IN (" + idList + ")");
                activityObject.put("sources", beacons.toJson(false));
            }else{
                LazyList<Zone> zones = Zone.where("id IN (" + idList + ")");
                activityObject.put("sources", zones.toJson(false));
            }
            return activityObject.toJSONString();
        });

        return getReturnJson("Success", 200, activityJson);
    };


    private static Route getCouponById = (Request request, Response response) -> {
        log.debug("**************** Getting Coupon");

        int activityId = OperationSupport.getIntFromUrl(request, "activity_id");

        String activityJson = new ConnectionWrapper<String>().wrap(()->{
            Activity activity = (Activity) Activity.where("id = ?", activityId).include(Coupon.class).get(0);
            Trigger trigger = (Trigger) Trigger.find("activity_id = ?", activityId).include(TriggerSource.class).get(0);
            LazyList<TriggerSource> triggerSources = TriggerSource.find("trigger_id = ?", trigger.getId());


            StringBuilder idList = new StringBuilder();
            for(TriggerSource triggerSource: triggerSources){
                idList.append(triggerSource.getSourceId()+",");
            }
            idList.deleteCharAt(idList.lastIndexOf(","));

            JSONObject activityObject = new JSONObject();
            activityObject.put("activity", activity.toJson(false));
            activityObject.put("trigger", trigger.toJson(false));
            activityObject.put("trigger_sources", triggerSources.toJson(false));

            if("BeaconTrigger".equals(trigger.getType())){
                LazyList<Beacon> beacons = Beacon.where("id IN (" + idList + ")");
                activityObject.put("sources", beacons.toJson(false));
            }else{
                LazyList<Zone> zones = Zone.where("id IN (" + idList + ")");
                activityObject.put("sources", zones.toJson(false));
            }
            return activityObject.toJSONString();
        });

        return getReturnJson("Success", 200, activityJson);
    };

    private static Route deleteActivity = (Request request, Response response) -> {
        log.debug("**************** Deleting Activity");
        int activityId = OperationSupport.getIntFromUrl(request, "activity_id");

        boolean isDeleted = new TransactionWrapper<Boolean>().wrap(()->{
            try {
                Activity activity = Activity.findById(activityId);
                activity.deleteCascadeExcept(Activity.getMetaModel().getAssociationForTarget(Application.class));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });

        if (isDeleted) {
            return getReturnJson("Sucess", 200);
        }
        return getReturnJson("Activity couldn't be deleted", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Activity Routes");
        Spark.path(AppFilter.PREFIX.concat(ACTIVITY_PREFIX), () -> {
            Spark.before("/*", AppFilter.addAuthenticationFilter);

            Spark.get("/get-activity/:activity_id", getActivityById);
            Spark.get("/get-coupon/:activity_id", getCouponById);
            Spark.put("/save-activity", createActivity);
            Spark.put("/save-coupon", saveCoupon);
            Spark.delete("/delete-activity/:activity_id", deleteActivity);
        });
    }
}
