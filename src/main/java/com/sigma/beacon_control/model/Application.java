package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("applications")
@IdName("id")
@BelongsTo(foreignKeyName = "account_id", parent = Account.class)
public class Application extends Model {

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

    public int getAccountId(){
        return this.getInteger("account_id");
    }

    public void setAccountId(int accountId){
        this.set("account_id", accountId);
    }

    public boolean isTest(){
        return this.getBoolean("test");
    }

    public void setTest(boolean test){
        this.set("test", test);
    }

    public boolean isDefaultApp(){
        return this.getBoolean("default_app");
    }

    public void setDefaultApp(boolean defaultApp){
        this.set("default_app", defaultApp);
    }
}
