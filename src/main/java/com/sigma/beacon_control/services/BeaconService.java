package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.common.util.Config;
import com.sigma.beacon_control.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.core.value.LazyValueMap;
import org.boon.core.value.ValueList;
import org.boon.json.JsonFactory;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 5/6/17.
 */
public class BeaconService extends Service {

    private static final String BEACON_PREFIX = "/beacon/secure";
    private static final Logger log = LogManager.getLogger(BeaconService.class);

    private static Route getBeaconsRoute = (Request request, Response response) -> {
        log.debug("**************** Getting beacons");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");

        String beaconsJson = new ConnectionWrapper<String>().wrap(() -> {
            LazyList<Beacon> beacons = Beacon.where("manager_id = ?", userId).include(Zone.class,
                    BeaconProximityField.class);
            return beacons.toJson(false, "floor", "id", "lat", "lng",
                    "location", "name", "protocol", "vendor", "zone_id", "created_at", "proximity_uuid");
        });

        return getReturnJson("Success", 200, beaconsJson);
    };

    private static Route getBeaconByIdRoute = (Request request, Response response) -> {
        log.debug("**************** Getting beacon by id");

        int beaconId = Integer.parseInt(OperationSupport.getStringFromParam(request, "beacon-id"));
        String result = new ConnectionWrapper<String>().wrap(() -> {
            Beacon beacon = Beacon.findById(beaconId);
            Zone zone = beacon.parent(Zone.class);
            BeaconConfig beaconConfig = beacon.get(BeaconConfig.class, "beacon_id = ?", beaconId).get(0);
            LazyList<BeaconProximityField> beaconProximityFields = beacon.getAll(BeaconProximityField.class);

            JSONObject beaconObject = new JSONObject();
            beaconObject.put("beacon", beacon.toJson(false));
            if (zone != null)
                beaconObject.put("zone", zone.toJson(false));
            beaconObject.put("proximity_fields", beaconProximityFields.toJson(false));
            beaconObject.put("beacon_config", beaconConfig.toJson(false));

            return beaconObject.toJSONString();
        });

        return getReturnJson("Success", 200, result);
    };

    private static Route getBeaconsWithoutZoneRoute = (Request request, Response response) -> {
        log.debug("**************** Getting beacons without zone");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");

        String beaconsJson = new ConnectionWrapper<String>().wrap(() -> {
            LazyList<Beacon> beacons = Beacon.where("manager_id = ? AND zone_id is NULL", userId);
            return beacons.toJson(false, "floor", "id", "name", "created_at");
        });

        return getReturnJson("Success", 200, beaconsJson);
    };

    private static Route createBeaconRoute = (Request request, Response response) -> {
        log.debug("**************** Create beacon");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");
        int accountId = Config.getInt("app.account.id");

        Beacon beacon = new Beacon();
        final BeaconConfig[] beaconConfig = {new BeaconConfig()};
        JSONObject requestJson = beacon.parseBody(request.body());
        List<BeaconProximityField> proximityFieldList = new ArrayList<>();
        ValueList proximityFieldsData = (ValueList) JsonFactory.fromJson(requestJson.get("proximity_fields").toString());

        if (requestJson.containsKey("id")) {
            new TransactionWrapper<Integer>().wrap(() -> {
                long beaconId = (long) requestJson.get("id");
                beaconConfig[0] = BeaconConfig.findFirst("beacon_id = ?", beaconId);
                return BeaconProximityField.delete("beacon_id = ?", beaconId);
            });
        }

        for (Object proximityFieldMap : proximityFieldsData) {
            BeaconProximityField beaconProximityField = new BeaconProximityField();
            beaconProximityField.fromMap((LazyValueMap) proximityFieldMap);

            proximityFieldList.add(beaconProximityField);
        }

        boolean isSaved = new TransactionWrapper<Boolean>().wrap(() -> {
            beacon.fromMap(OperationSupport.getBean(requestJson.toJSONString()));

            beacon.setManagerId(userId);
            beacon.setAccountId(accountId);
            return beacon.save();
        });

        if (isSaved) {
            isSaved = new TransactionWrapper<Boolean>().wrap(() -> {
                boolean result = false;
                try {
                    PreparedStatement ps = Base.startBatch("insert into beacon_proximity_fields (\"name\", value, beacon_id) values(?, ?, ?)");
                    for (BeaconProximityField beaconProximityField : proximityFieldList) {
                        Base.addBatch(ps, beaconProximityField.getName(), beaconProximityField.getValue(), beacon.getId());
                    }
                    result = Base.executeBatch(ps).length > 0;
                    ps.close();

                    beaconConfig[0].setBeaconId((Integer) beacon.getId());
                    beaconConfig[0].setData(requestJson.get("beacon_config").toString());
                    beaconConfig[0].setBeaconUpdatedAt(beacon.getUpdatedAt());
                    beaconConfig[0].save();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            });

            if (isSaved)
                return getReturnJson("Sucess", 200, beacon.getId().toString());
            else {
                new TransactionWrapper<Void>().wrap(() -> {
                    beacon.delete();
                    return null;
                });
                return getReturnJson("Beacon could'nt be saved", 422);
            }
        }
        return getReturnJson("Beacon could'nt be saved", 422);
    };

    private static Route deleteBeaconRoute = (Request request, Response response) -> {
        log.debug("**************** Deleating beacon");

        long beaconId = OperationSupport.getLongFromUrl(request, "beacon_id");

        boolean isDeleted = new TransactionWrapper<Boolean>().wrap(() -> {
            try {
                Beacon beacon = Beacon.findById(beaconId);
                beacon.deleteCascadeExcept(Beacon.getMetaModel().getAssociationForTarget(Application.class));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });

        if (isDeleted) {
            return getReturnJson("Sucess", 200);
        }
        return getReturnJson("Beacon couldn't be deleted", 500);
    };

    private static Route deleteBatchBeaconRoute = (Request request, Response response) -> {
        log.debug("**************** Deleating beacons");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        String beaconsIds = (String) requestJson.get("beacons_ids");

        boolean isDeleted = new TransactionWrapper<Boolean>().wrap(() -> {
            try {
                BeaconProximityField.delete("beacon_id IN (" + beaconsIds + ")");
                BeaconConfig.delete("beacon_id IN (" + beaconsIds + ")");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
        if (isDeleted) {
            new TransactionWrapper<Integer>().wrap(() -> Beacon.delete("id IN (" + beaconsIds + ")"));
            return getReturnJson("Beacons are deleted", 200);
        }

        return getReturnJson("Beacons couldn't be deleted", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Beacon Routes");
        Spark.path(AppFilter.PREFIX.concat(BEACON_PREFIX), () -> {
            Spark.before("/*", AppFilter.addAuthenticationFilter);

            Spark.get("/getBeacons", getBeaconsRoute);
            Spark.get("/get-beacon-by-id", getBeaconByIdRoute);
            Spark.get("/getBeaconsNoZone", getBeaconsWithoutZoneRoute);
            Spark.delete("/delete-beacon/:beacon_id", deleteBeaconRoute);
            Spark.put("/save-beacon", createBeaconRoute);
            Spark.post("/delete-beacons-batch", deleteBatchBeaconRoute);
        });
    }
}
