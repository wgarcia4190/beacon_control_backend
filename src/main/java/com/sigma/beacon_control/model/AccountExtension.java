package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Wilson on 4/24/17.
 */

@Table("account_extentions")
@CompositePK({"account_id", "extention_name"})
@BelongsTo(foreignKeyName = "account_id", parent = Account.class)
public class AccountExtension extends Model {
    static {
        validatePresenceOf("account_id", "extention_name").message(
                "One or more composite PK's missing!!!");
    }

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
}
