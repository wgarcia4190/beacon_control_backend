package com.sigma.beacon_control.model;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

@Table("oauth_applications")
@IdName("id")
@BelongsTo(foreignKeyName = "application_id", parent = Application.class)
public class OauthApplication extends Model {

    public String getName() {
        return this.getString("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public String getUid() {
        return this.getString("uid");
    }

    public void setUid(String uid){
        this.set("uid", uid);
    }

    public String getSecret(){
        return this.getString("secret");
    }

    public void setSecret(String secret){
        this.set("secret", secret);
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

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public String getScopes() {
        return this.getString("scopes");
    }

    public void setScopes(String scopes){
        this.set("scopes", scopes);
    }

    public String getRedirectUri(String redirectUri){
        return this.getString("redirect_uri");
    }

    public void setRedirectUri(String redirectUri){
        this.set("redirect_uri", redirectUri);
    }
}
