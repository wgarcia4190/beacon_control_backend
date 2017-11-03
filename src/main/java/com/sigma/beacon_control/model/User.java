package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("users")
@IdName("id")
@BelongsTo(foreignKeyName = "application_id", parent = Application.class)
public class User extends Model {

    public String getClientId(){
        return this.getString("client_id");
    }

    public void setClientId(String clientId){
        this.set("client_id", clientId);
    }

    public int getApplicationId(){
        return this.getInteger("application_id");
    }

    public void setApplicationId(int applicationId){
        this.set("application_id", applicationId);
    }
}
