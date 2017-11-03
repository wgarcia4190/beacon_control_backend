package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.common.util.Config;
import com.sigma.beacon_control.model.Beacon;
import com.sigma.beacon_control.model.Zone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.core.value.LazyValueMap;
import org.boon.core.value.ValueList;
import org.boon.json.JsonFactory;
import org.boon.json.JsonParserFactory;

import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


/**
 * Created by Wilson on 5/7/17.
 */
public class ZoneService extends Service {

    private static final String ZONE_PREFIX = "/zone/secure";
    private static final Logger log = LogManager.getLogger(ZoneService.class);

    private static Route getZonesRoute = (Request request, Response response) -> {
        log.debug("**************** Getting zones");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");

        String zonesJson = new ConnectionWrapper<String>().wrap(() -> {
            LazyList<Zone> zones = Zone.where("manager_id = ?", userId).include(Beacon.class);
            return zones.toJson(false);
        });

        return getReturnJson("Success", 200, zonesJson);
    };

    private static Route getZoneByIdRoute = (Request request, Response response) -> {
        log.debug("**************** Getting zone by id");

        int zoneId = Integer.parseInt(OperationSupport.getStringFromParam(request, "zone-id"));
        String result = new ConnectionWrapper<String>().wrap(() -> {
            Zone zone = Zone.findById(zoneId);
            LazyList<Beacon> beaconList = zone.getAll(Beacon.class);

            zone.setBeaconsCount(beaconList.size());
            return "{\"zone\" :".concat(zone.toJson(false)).concat(", \"beacons\" :")
                    .concat(beaconList.toJson(false, "id", "name", "floor", "created_at")).concat("}");
        });

        return getReturnJson("Success", 200, result);
    };

    private static Route saveZoneRoute = (Request request, Response response) -> {
        log.debug("**************** Save zones");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");
        int accountId = Config.getInt("app.account.id");
        Zone zone = new Zone();

        LazyValueMap zoneData = (LazyValueMap) JsonFactory.fromJson(request.body());
        zoneData.put("beacons_count", Integer.parseInt((String) zoneData.get("beacons_count")));
        if (zoneData.get("id") != null)
            zoneData.put("id", Integer.parseInt((String) zoneData.get("id")));

        boolean isSaved = new TransactionWrapper<Boolean>().wrap(() -> {
            zone.fromMap(zoneData);

            zone.setManagerId((int) userId);
            zone.setAccountId(accountId);
            return zone.saveIt();
        });

        if (isSaved) {
            ValueList beaconList = !zoneData.get("beacons").equals("[]") ?
                    (ValueList) new JsonParserFactory().create().parse((String) zoneData.get("beacons")) :
                    new ValueList(false);

            if (!beaconList.isEmpty()) {
                StringBuilder beaconId = new StringBuilder();
                for (Object beacon : beaconList) {
                    beaconId.append(((LazyValueMap) beacon).get("_id").toString().concat(", "));
                }
                String finalBeaconId = "(".concat(beaconId.substring(0, beaconId.length() - 2)).concat(")");
                new TransactionWrapper<Void>().wrap(() -> {
                    Beacon.update("zone_id = ?", "zone_id = ?", null,
                            Integer.parseInt(zone.getId().toString()));
                    Beacon.update("zone_id = ?", "id IN".concat(finalBeaconId),
                            Integer.parseInt(zone.getId().toString()));
                    return null;
                });
            } else {
                new TransactionWrapper<Void>().wrap(() -> {
                    Beacon.update("zone_id = ?", "zone_id = ?", null,
                            Integer.parseInt(zone.getId().toString()));
                    return null;
                });
            }
            JSONObject requestJson = (JSONObject) getParser().parse(request.body());
            requestJson.put("zone_id", zone.getId());
            return getReturnJson("Sucess", 200, requestJson.toJSONString());
        }
        return getReturnJson("Zone could'nt be saved", 422);
    };

    private static Route deleteZoneRoute = (Request request, Response response) -> {
        log.debug("**************** Deleting zone");

        int zoneId = OperationSupport.getIntFromUrl(request, "zone_id");
        boolean isDeleted = new TransactionWrapper<Boolean>().wrap(() -> {
            Zone zone = Zone.findById(zoneId);
            return zone.delete();
        });

        if (isDeleted) {
            new TransactionWrapper<Void>().wrap(() -> {
                Beacon.update("zone_id = ?", "zone_id = ?", null, zoneId);
                return null;
            });
            return getReturnJson("Zone Deleted", 200);
        }

        return getReturnJson("Zone couldn't be deleted", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Zone Routes");
        Spark.path(AppFilter.PREFIX.concat(ZONE_PREFIX), () -> {
            Spark.before("/*", AppFilter.addAuthenticationFilter);

            Spark.get("/getZones", getZonesRoute);
            Spark.get("/getZoneById", getZoneByIdRoute);
            Spark.put("/save-zone", saveZoneRoute);
            Spark.delete("/delete-zone/:zone_id", deleteZoneRoute);
        });
    }
}
