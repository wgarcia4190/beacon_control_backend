package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("mobile_devices")
@IdName("id")
@BelongsTo(foreignKeyName = "user_id", parent = User.class)
public class MobileDevice extends Model {

    public int getUserId() {
        return this.getInteger("user_id");
    }

    public void setUserId(int userId) {
        this.set("user_id", userId);
    }

    public String getPushToken() {
        return this.getString("push_token");
    }

    public void setPushToken(String pushToken) {
        this.set("push_token", pushToken);
    }

    public int getOS() {
        return this.getInteger("os");
    }

    public void setOS(int os) {
        this.set("os", os);
    }

    public int getEnvironment() {
        return this.getInteger("environment");
    }

    public void setEnvironment(int environment) {
        this.set("environment", environment);
    }

    public boolean isActive() {
        return this.getBoolean("active");
    }

    public void setActive(boolean active) {
        this.set("active", active);
    }

    public Timestamp getLastSignInAt() {
        return this.getTimestamp("last_sign_in_at");
    }

    public void setLastSignInAt(Timestamp lastSignInAt) {
        this.set("last_sign_in_at", lastSignInAt);
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

    public String getCorrelationId(){
        return this.getString("correlation_id");
    }

    public void setCorrelationId(String correlationId){
        this.set("correlation_id", correlationId);
    }


}
