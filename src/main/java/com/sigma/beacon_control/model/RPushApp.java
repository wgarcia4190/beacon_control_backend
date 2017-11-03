package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("rpush_apps")
@IdName("id")
@BelongsTo(foreignKeyName = "application_id", parent = Application.class)
public class RPushApp extends Model {

    public String getName() {
        return this.getString("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public String getEnvironment() {
        return this.getString("environment");
    }

    public void setEnvironment(String environment) {
        this.set("environment", environment);
    }

    public String getCertificate() {
        return this.getString("certificate");
    }

    public void setCertificate(String certificate) {
        this.set("certificate", certificate);
    }

    public String getPassword() {
        return this.getString("password");
    }

    public void setPassword(String password) {
        this.set("password", password);
    }

    public int getConnections() {
        return this.getInteger("connections");
    }

    public void setConnections(int connections) {
        this.set("connections", connections);
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

    public String getType() {
        return this.getString("type");
    }

    public void setType(String type) {
        this.set("type", type);
    }

    public String getAuthKey() {
        return this.getString("auth_key");
    }

    public void setAuthKey(String authKey) {
        this.set("auth_key", authKey);
    }

    public String getClientId() {
        return this.getString("client_id");
    }

    public void setClientId(String clientId) {
        this.set("client_id", clientId);
    }

    public String getClientSecret() {
        return this.getString("client_secret");
    }

    public void setClientSecret(String clientSecret) {
        this.set("client_secret", clientSecret);
    }

    public String getAccessToken() {
        return this.getString("access_token");
    }

    public void setAccessToken(String accessToken) {
        this.set("access_token", accessToken);
    }

    public Timestamp getAccessTokenExpiration() {
        return this.getTimestamp("access_token_expiration");
    }

    public void setAccessTokenExpiration(Timestamp accessTokenExpiration) {
        this.set("access_token_expiration", accessTokenExpiration);
    }

    public int getApplicationId() {
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId) {
        this.set("application_id", applicationId);
    }
}
