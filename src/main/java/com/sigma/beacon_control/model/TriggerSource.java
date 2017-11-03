package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("triggers_sources")
@IdName("id")
@BelongsTo(foreignKeyName = "trigger_id", parent = Trigger.class)
public class TriggerSource extends Model{

    public int getTriggerId(){
        return this.getInteger("trigger_id");
    }

    public void setTriggerId(int triggerId){
        this.set("trigger_id", triggerId);
    }

    public int getSourceId(){
        return this.getInteger("source_id");
    }

    public void setSourceId(int sourceId){
        this.set("source_id", sourceId);
    }

    public String getSourceType(){
        return this.getString("source_type");
    }

    public void setSourceType(String sourceType){
        this.set("source_type", sourceType);
    }
}

