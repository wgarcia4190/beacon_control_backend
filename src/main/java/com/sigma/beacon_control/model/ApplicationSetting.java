package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("application_settings")
@IdName("id")
@BelongsTo(foreignKeyName = "application_id", parent = Application.class)
public class ApplicationSetting extends Model {

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public String getExtensionName(){
        return this.getString("extension_name");
    }

    public void setExtensionName(String extensionName){
        this.set("extension_name", extensionName);
    }

    public String getType(){
        return this.getString("type");
    }

    public void setType(String type){
        this.set("type", type);
    }

    public String getKey(){
        return this.getString("key");
    }

    public void setkey(String key){
        this.set("key", key);
    }

    public String getValue(){
        return this.getString("value");
    }

    public void setValue(String value){
        this.set("value", value);
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
