package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("triggers")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "activity_id", parent = Activity.class),
        @BelongsTo(foreignKeyName = "application_id", parent = Application.class)
})
public class Trigger extends Model {

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

    public String getEventType(){
        return this.getString("event_type");
    }

    public void setEventType(String eventType){
        this.set("event_type", eventType);
    }

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public int getDwellTime(){
        return this.getInteger("dwell_time");
    }

    public void setDwellTime(int dwellTime){
        this.set("dwell_time", dwellTime);
    }

    public String getType(){
        return this.getString("type");
    }

    public void setType(String type){
        this.set("type", type);
    }

    public int getActivityId(){
        return this.getInteger("activity_id");
    }

    public void setActivityId(int activityId){
        this.set("activity_id", activityId);
    }

    public boolean isTest(){
        return this.getBoolean("test");
    }

    public void setTest(boolean test){
        this.set("test", test);
    }
}
