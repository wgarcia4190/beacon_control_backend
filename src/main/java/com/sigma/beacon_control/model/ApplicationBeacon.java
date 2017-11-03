package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("applications_beacons")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "application_id", parent = Application.class),
        @BelongsTo(foreignKeyName = "beacon_id", parent = Beacon.class)
})
public class ApplicationBeacon extends Model {

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public int getBeaconId(){
        return this.getInteger("beacon_id");
    }

    public void setBeaconId(int beaconId){
        this.set("beacon_id", beaconId);
    }
}

