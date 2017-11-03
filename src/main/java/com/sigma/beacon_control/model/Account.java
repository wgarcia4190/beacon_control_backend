package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/17/17.
 */

@Table("accounts")
@IdName("id")
@BelongsTo(foreignKeyName = "brand_id", parent = Brand.class)
public class Account extends Model {

    public String getName() {
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

    public int getBrandId(){
        return this.getInteger("brand_id");
    }

    public void setBrandId(int brandId){
        this.set("brand_id", brandId);
    }
}
