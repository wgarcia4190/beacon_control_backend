package com.sigma.beacon_control.common.util;

import org.json.simple.JSONObject;

/**
 * Created by Wilson on 4/30/17.
 */
public class Json {

    public static JsonBuilder createObjectBuilder(){
        return new JsonBuilder();
    }

    public static final class JsonBuilder {
        private JSONObject json = new JSONObject();

        public JsonBuilder add(String key, String value){
            json.put(key, value);
            return this;
        }

        public JsonBuilder add(String key, int value){
            json.put(key, value);
            return this;
        }

        public JSONObject build(){
            return json;
        }
    }
}
