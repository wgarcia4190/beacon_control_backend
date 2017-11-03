package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("zone_presence_stats")
@IdName("id")
@BelongsTo(foreignKeyName = "zone_id", parent = Zone.class)
public class ZonePresenceStat extends Model {

    public int getZoneId(){
        return this.getInteger("zone_id");
    }

    public void setZoneId(int zoneId){
        this.set("zone_id", zoneId);
    }

    public Timestamp getDate(){
        return this.getTimestamp("date");
    }

    public void setDate(Timestamp date){
        this.set("date", date);
    }

    public int getHour(){
        return this.getInteger("hour");
    }

    public void setHour(int hour){
        this.set("hour", hour);
    }

    public int getUsersCount(){
        return this.getInteger("users_count");
    }

    public void setUsersCount(int usersCount){
        this.set("users_count", usersCount);
    }

}
