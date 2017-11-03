package com.sigma.beacon_control.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

/**
 * Created by Wilson on 4/24/17.
 */
@Table("coupons")
@IdName("id")
@BelongsTo(foreignKeyName = "activity_id", parent = Activity.class)
public class Coupon extends Model {

    static {
        zeroToNull("identifier_number");
        zeroToNull("unique_identifier_number");
    }

    public String getTemplate() {
        return this.getString("template");
    }

    public void setTemplate(String template) {
        this.set("template", template);
    }

    public String getName() {
        return this.getString("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public String getTitle() {
        return this.getString("title");
    }

    public void setTitle(String title) {
        this.set("title", title);
    }

    public String getDescription() {
        return this.getString("description");
    }

    public void setDescription(String description) {
        this.set("description", description);
    }

    public int getActivityId() {
        return this.getInteger("activity_id");
    }

    public void setActivityId(int activityId) {
        this.set("activity_id", activityId);
    }

    public Timestamp getCreatedAt() {
        return this.getTimestamp("created_at");
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.set("created_at", createdAt);
    }

    public Timestamp getUpdatedAt() {
        return this.getTimestamp("updated_at");
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.set("updated_at", updatedAt);
    }

    public int getIdentifierNumber() {
        return this.getInteger("identifier_number");
    }

    public void setIdentifierNumber(int identifierNumber) {
        this.set("identifier_number", identifierNumber);
    }

    public int getUniqueIdentifierNumber() {
        return this.getInteger("unique_identifier_number");
    }

    public void setUniqueIdentifierNumber(int uniqueIdentifierNumber) {
        this.set("unique_identifier_number", uniqueIdentifierNumber);
    }

    public String getEncodingType() {
        return this.getString("encoding_type");
    }

    public void setEncodingType(String encodingType) {
        this.set("encoding_type", encodingType);
    }

    public String getButtonFontColor() {
        return this.getString("button_font_color");
    }

    public void setButtonFontColor(String buttonFontColor) {
        this.set("button_font_color", buttonFontColor);
    }

    public String getButtonBackgroundColor() {
        return this.getString("button_background_color");
    }

    public void setButtonBackgroundColor(String buttonBackgroundColor) {
        this.set("button_background_color", buttonBackgroundColor);
    }

    public String getButtonLabel() {
        return this.getString("button_label");
    }

    public void setButtonLabel(String buttonLabel) {
        this.set("button_label", buttonLabel);
    }

    public String getButtonLink() {
        return this.getString("button_link");
    }

    public void setButtonLink(String buttonLink) {
        this.set("button_link", buttonLink);
    }
}
