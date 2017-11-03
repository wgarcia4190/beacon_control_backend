package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("activities")
@IdName("id")
public class Activity extends Model {

    public String getName(){
        return this.getString("name");
    }

    public void setName(String name){
        this.set("name", name);
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

    public String getPayload(){
        return this.getString("payload");
    }

    public void setPayload(String payload){
        this.set("payload", payload);
    }

    public String getScheme(){
        return this.getString("scheme");
    }

    public void setScheme(String scheme){
        this.set("scheme", scheme);
    }
}
