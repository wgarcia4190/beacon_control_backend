package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.*;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("zones")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "account_id", parent = Account.class),
        @BelongsTo(foreignKeyName = "manager_id", parent = Admin.class)
})
@Many2Many(other = Application.class, join = "applications_zones", sourceFKName = "zone_id", targetFKName = "application_id")
public class Zone extends Model {

    public String getName(){
        return this.getString("name");
    }

    public void setName(String name){
        this.set("name", name);
    }

    public int getAccountId(){
        return this.getInteger("account_id");
    }

    public void setAccountId(int accountId){
        this.set("account_id", accountId);
    }

    public Timestamp getCreatedAt(){
        return this.getTimestamp("created_at");
    }

    public void setCreatedAt(Timestamp createdAt){
        this.set("created_at", createdAt);
    }

    public Timestamp getUpdatedAt(){
        return this.getTimestamp("updated_at");
    }

    public void setUpdatedAt(Timestamp updatedAt){
        this.set("updated_at", updatedAt);
    }

    public String getDescription(){
        return this.getString("description");
    }

    public void setDescription(String description){
        this.set("description", description);
    }

    public int getManagerId(){
        return this.getInteger("manager_id");
    }

    public void setManagerId(int managerId){
        this.set("manager_id", managerId);
    }

    public String getColor(){
        return this.getString("color");
    }

    public void setColor(String color){
        this.set("color", color);
    }

    public int getBeaconsCount(){
        return this.getInteger("beacons_count");
    }

    public void setBeaconsCount(int beaconsCount){
        this.set("beacons_count", beaconsCount);
    }


}
