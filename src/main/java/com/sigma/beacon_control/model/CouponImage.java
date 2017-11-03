package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("coupon_images")
@IdName("id")
@BelongsTo(foreignKeyName = "coupon_id", parent = Coupon.class)
public class CouponImage extends Model {

    public int getCouponId(){
        return this.getInteger("coupon_id");
    }

    public void setCouponId(int couponId){
        this.set("coupon_id", couponId);
    }

    public String getFile(){
        return this.getString("file");
    }

    public void setFile(String file){
        this.set("file", file);
    }

    public String getType(){
        return this.getString("type");
    }

    public void setType(String type){
        this.set("type", type);
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
