package com.sigma.beacon_control.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public interface Entity {

    JSONParser parser = new JSONParser();
    JSONObject parseBody(String stringBody);
}
