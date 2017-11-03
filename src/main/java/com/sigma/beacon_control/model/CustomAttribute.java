package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("custom_attributes")
@IdName("id")
@BelongsTo(foreignKeyName = "activity_id", parent = Activity.class)
public class CustomAttribute extends Model {

    public String getName(){
        return this.getString("name");
    }

    public void setName(String name){
        this.set("name", name);
    }

    public String getValue(){
        return this.getString("value");
    }

    public void setValue(String value){
        this.set("value", value);
    }

    public int getActivityId(){
        return this.getInteger("activity_id");
    }

    public void setActivityId(int activityId){
        this.set("activity_id", activityId);
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
}
