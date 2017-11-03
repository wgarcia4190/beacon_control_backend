package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("beacons")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "account_id", parent = Account.class),
        @BelongsTo(foreignKeyName = "manager_id", parent = Admin.class),
        @BelongsTo(foreignKeyName = "zone_id", parent = Zone.class)
})
@Many2Many(other = Application.class, join = "applications_beacons", sourceFKName = "beacon_id", targetFKName = "application_id")
public class Beacon extends Model implements Entity {

    public String getName() {
        return this.getString("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public Timestamp getCreatedAt() {
        return this.getTimestamp("created_at");
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.set("created_at", createdAt);
    }

    public Timestamp getUpdatedAt() {
        return this.getTimestamp("updated_at");
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.set("updated_at", updatedAt);
    }

    public double getLat() {
        return this.getDouble("lat");
    }

    public void setLat(double lat) {
        this.set("lat", lat);
    }

    public double getLng() {
        return this.getDouble("lng");
    }

    public void setLng(double lng) {
        this.set("lng", lng);
    }

    public int getFloor() {
        return this.getInteger("floor");
    }

    public void setFloor(long floor) {
        this.set("floor", floor);
    }

    public int getAccountId() {
        return this.getInteger("account_id");
    }

    public void setAccountId(int accountId) {
        this.set("account_id", accountId);
    }

    public int getZoneId() {
        return this.getInteger("zone_id");
    }

    public void setZoneId(long zoneId) {
        this.set("zone_id", zoneId);
    }

    public long getManagerId() {
        return this.getLong("manager_id");
    }

    public void setManagerId(long managerId) {
        this.set("manager_id", managerId);
    }

    public String getLocation() {
        return this.getString("location");
    }

    public void setLocation(String location) {
        this.set("location", location);
    }

    public String getProtocol() {
        return this.getString("protocol");
    }

    public void setProtocol(String protocol) {
        this.set("protocol", protocol);
    }

    public String getVendor() {
        return this.getString("vendor");
    }

    public void setVendor(String vendor) {
        this.set("vendor", vendor);
    }

    public String getProximityUUID() {
        return this.getString("proximity_uuid");
    }

    public void setProximityUUID(String proximityUUID) {
        this.set("proximity_uuid", proximityUUID);
    }

    public void removeZone(){
        this.set("zone_id", null);
    }

    @Override
    public JSONObject parseBody(String stringBody) {
        JSONObject requestJson = new JSONObject();
        try {
            requestJson = (JSONObject) parser.parse(stringBody);
            requestJson.put("lat", Double.parseDouble(requestJson.get("lat").toString()));
            requestJson.put("lng", Double.parseDouble(requestJson.get("lng").toString()));
            if (requestJson.containsKey("zone_id"))
                requestJson.put("zone_id", Integer.parseInt(requestJson.get("zone_id").toString()));
            requestJson.put("floor", Integer.parseInt(requestJson.get("floor").toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return requestJson;
    }
}
