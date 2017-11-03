package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("application_extensions")
@IdName("id")
@BelongsTo(foreignKeyName = "application_id", parent = Application.class)
public class ApplicationExtension extends Model {

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }

    public String getConfiguration(){
        return this.getString("configuration");
    }

    public void setConfiguration(String configuration){
        this.set("configuration", configuration);
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

    public String getExtensionName(){
        return this.getString("extension_name");
    }

    public void setExtensionName(String extensionName){
        this.set("extension_name", extensionName);
    }

}
