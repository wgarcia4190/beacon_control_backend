package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.AppFilter;
import com.sigma.beacon_control.model.Beacon;
import com.sigma.beacon_control.model.Zone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.json.JsonFactory;
import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapService extends Service {

    private static final String MAP_PREFIX = "/map/secure";
    private static final Logger log = LogManager.getLogger(MapService.class);

    private static Route getBeaconsByZone = (Request request, Response response) -> {
        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");

        Map<String, List<String>> zoneBeaconMap = new HashMap<>();

        String beaconsJson = new ConnectionWrapper<String>().wrap(() -> {
            LazyList<Beacon> beacons = Beacon.where("manager_id = ?", userId).include(Zone.class);

            for (Beacon beacon : beacons) {
                Zone zone = beacon.parent(Zone.class);
                String zoneKey = zone == null ? "Unassigned-rgb(73,72,70)-0" : zone.getName().concat("-").concat(zone.getColor().concat("-").concat(zone.getId().toString()));

                if (!zoneBeaconMap.containsKey(zoneKey))
                    zoneBeaconMap.put(zoneKey, new ArrayList<>());

                zoneBeaconMap.get(zoneKey).add(beacon.toJson(false));
            }
            return JsonFactory.toJson(zoneBeaconMap);
        });

        return getReturnJson("Success", 200, beaconsJson);
    };

    private static Route updateBeacon = (Request request, Response response) -> {
        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        Beacon beacon = new ConnectionWrapper<Beacon>().wrap(() -> Beacon.findById(requestJson.get("id")));
        if ("0".equals(requestJson.get("zone_id").toString())) {
            beacon.removeZone();
        } else
            beacon.setZoneId((Long) requestJson.get("zone_id"));
        beacon.setFloor((Long) requestJson.get("floor"));

        boolean isUpdate = new TransactionWrapper<Boolean>().wrap(beacon::save);

        if(isUpdate)
            return getReturnJson("Beacon updated", 200);
        return getReturnJson("Beacon couldn,t be updated", 500);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading Map Routes");
        Spark.path(AppFilter.PREFIX.concat(MAP_PREFIX), () -> {
            Spark.before("/*", AppFilter.addAuthenticationFilter);

            Spark.get("/get-beacons-by-zone", getBeaconsByZone);
            Spark.post("/update-beacon", updateBeacon);
        });
    }
}
