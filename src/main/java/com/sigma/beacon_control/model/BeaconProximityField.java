package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("beacon_proximity_fields")
@IdName("id")
@BelongsTo(foreignKeyName = "beacon_id", parent = Beacon.class)
public class BeaconProximityField extends Model {

    public int getBeaconId() {
        return this.getInteger("beacon_id");
    }

    public void setBeaconId(int beaconId) {
        this.set("beacon_id", beaconId);
    }

    public String getName() {
        return this.getString("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public String getValue() {
        return this.getString("value");
    }

    public void setValue(String value) {
        this.set("value", value);
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
}
