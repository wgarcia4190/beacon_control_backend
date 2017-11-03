package com.sigma.beacon_control.services;


import com.sigma.beacon_control.common.util.Json;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by Wilson on 4/30/17.
 */
public class Service {

    private static JSONParser parser = new JSONParser();

    protected static String getReturnJson(String message, int code, String data){
        JSONObject resultObject = Json.createObjectBuilder()
                .add("message", message)
                .add("code", code)
                .add("data", data).build();

        return resultObject.toString();
    }

    protected static String getReturnJson(String message, int code){
        JSONObject resultObject = Json.createObjectBuilder()
                .add("message", message)
                .add("code", code).build();

        return resultObject.toString();
    }

    protected static JSONParser getParser(){
        return parser;
    }
}
