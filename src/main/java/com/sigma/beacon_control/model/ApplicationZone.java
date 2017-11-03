package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * @author Ing. Wilson Garcia
 * Created on 10/29/17
 */
@Table("applications_zones")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "application_id", parent = Application.class),
        @BelongsTo(foreignKeyName = "zone_id", parent = Zone.class)
})
public class ApplicationZone extends Model {

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public int getZoneId(){
        return this.getInteger("zone_id");
    }

    public void setZoneId(int beaconId){
        this.set("zone_id", beaconId);
    }
}
