package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("rpush_feedback")
@IdName("id")
@BelongsTo(foreignKeyName = "app_id", parent = Application.class)
public class RPushFeedback extends Model {

    public String getDeviceToken(){
        return this.getString("device_token");
    }

    public void setDeviceToken(String deviceToken){
        this.set("device_token", deviceToken);
    }

    public int getAppId(){
        return this.getInteger("app_id");
    }

    public void setAppId(int appId){
        this.set("app_id", appId);
    }

    public Timestamp getFailedAt(){
        return this.getTimestamp("failed_at");
    }

    public void setFailedAt(Timestamp failedAt){
        this.set("failed_at", failedAt);
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
