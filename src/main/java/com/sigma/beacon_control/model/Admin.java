package com.sigma.beacon_control.model;

import com.sigma.beacon_control.common.util.Password;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/23/17.
 */

@Table("admins")
@IdName("id")
@BelongsTo(foreignKeyName = "account_id", parent = Account.class)
public class Admin extends Model {

    public String getEmail(){
        return this.getString("email");
    }

    public void setEmail(String email){
        this.set("email", email);
    }

    public String getEncryptedPassword(){
        return this.getString("encrypted_password");
    }

    public void setEncryptedPassword(String password){
        this.set("encrypted_password", Password.encryptPassword(password));
    }

    public String getResetPasswordToken(){
        return this.getString("reset_password_token");
    }

    public void setResetPasswordToken(String resetPasswordToken){
        this.set("reset_password_token", resetPasswordToken);
    }

    public Timestamp getResetPasswordSentAt(){
        return this.getTimestamp("rest_password_sent_at");
    }

    public void setResetPasswordSentAt(Timestamp resetPasswordSentAt){
        this.set("reset_password_sent_at", resetPasswordSentAt);
    }

    public int getSignInCount(){
        return this.getInteger("sign_in_count");
    }

    public void setSignInCount(int signInCount){
        this.set("sign_in_count", signInCount);
    }

    public Timestamp getCurrentSignInAt(){
        return this.getTimestamp("current_sign_in_at");
    }

    public void setCurrentSignInAt(Timestamp currentSignInAt){
        this.set("current_sign_in_at", currentSignInAt);
    }

    public Timestamp getLastSignInAt(){
        return this.getTimestamp("last_sign_in_at");
    }

    public void setLastSignInAt(Timestamp lastSignInAt){
        this.set("last_sign_in_at", lastSignInAt);
    }

    public String getCurrentSignInIp(){
        return this.getString("current_sign_in_ip");
    }

    public void setCurrentSignInIp(String currentSignInIp){
        this.set("current_sign_in_ip", currentSignInIp);
    }

    public String getLastSignInIp(){
        return this.getString("last_sign_in_ip");
    }

    public void setLastSignInIp(String lastSignInIp){
        this.set("last_sign_in_ip", lastSignInIp);
    }

    public String getConfirmationToken(){
        return this.getString("confirmation_token");
    }

    public void setConfirmationToken(String confirmationToken){
        this.set("confirmation_token", confirmationToken);
    }

    public Timestamp getConfirmedAt(){
        return this.getTimestamp("confirmed_at");
    }

    public void setConfirmedAt(Timestamp confirmedAt){
        this.set("confirmed_at", confirmedAt);
    }

    public Timestamp getConfirmationSentAt(){
        return this.getTimestamp("confirmation_sent_at");
    }

    public void setConfirmationSentAt(Timestamp confirmationSentAt){
        this.set("confirmation_sent_at", confirmationSentAt);
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

    public String getDefaultBeaconUUID(){
        return this.getString("default_beacon_uuid");
    }

    public void setDefaultBeaconUUID(String defaultBeaconUUID){
        this.set("default_beacon_uuid", defaultBeaconUUID);
    }

    public int getAccountId(){
        return this.getInteger("account_id");
    }

    public void setAccountId(int accountId){
        this.set("account_id", accountId);
    }

    public int getRole(){
        return this.getInteger("role");
    }

    public void setRole(int role){
        this.set("role", role);
    }

    public String getCorrelationId(){
        return this.getString("correlation_id");
    }

    public void setCorrelationId(String correlationId){
        this.set("correlation_id", correlationId);
    }

    public boolean needWalkthrough(){
        return this.getBoolean("walkthrough");
    }

    public void setWalkthrough(boolean walkthrough){
        this.set("walkthrough", walkthrough);
    }


}
