package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("extension_settings")
@IdName("id")
@BelongsToParents({
        @BelongsTo(foreignKeyName = "account_id", parent = AccountExtension.class),
        @BelongsTo(foreignKeyName = "extension_name", parent = AccountExtension.class)

})
public class ExtesionSetting extends Model {

    public int getAccountId(){
        return this.getInteger("account_id");
    }

    public void setAccountId(int accountId){
        this.set("account_id", accountId);
    }

    public String getExtensionName(){
        return this.getString("extension_name");
    }

    public void setExtensionName(String extensionName){
        this.set("extension_name", extensionName);
    }

    public String getKey(){
        return this.getString("key");
    }

    public void setKey(String key){
        this.set("key", key);
    }

    public String getValue(){
        return this.getString("value");
    }

    public void setValue(String value){
        this.set("value", value);
    }
}
