package com.sigma.beacon_control.common.util;

import com.sigma.beacon_control.common.util.exceptions.EntryNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Wilson on 4/16/17.
 */
public final class Config {

    private static final Logger log = LogManager.getLogger(Config.class);
    private static final Properties properties = new Properties();
    private static final String RESOURCE_NAME = "/beacon-control.properties";

    static {
        loadProperties();
    }

    private Config(){}

    private static void loadProperties() {
        try(InputStream input = Config.class.getResourceAsStream(RESOURCE_NAME)) {
            log.debug("Loading config from resources...");
            properties.load(input);
            log.debug(properties.size() + " entries loaded.");
        }catch (IOException ex){
            log.error("Error: ", ex);
            throw new RuntimeException("Error loading config file");
        }
    }

    public static String getConfigFile(String dir){
        StringBuilder fileContent = new StringBuilder();

        try(InputStream input = Config.class.getResourceAsStream(dir)) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                fileContent.append(line);
            }

            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return fileContent.toString();
    }

    public static String getString(String key){
        String propertyValue = properties.getProperty(key);
        if(StringUtils.isEmpty(propertyValue)){
            try {
                throw new EntryNotFoundException("The entry ".concat(key).concat(" does not exist."));
            } catch (EntryNotFoundException e) {
                e.printStackTrace();
            }
        }
        return propertyValue;
    }

    public static int getInt(String key){
        String propertyValue = getString(key);
        int result = 0;

        if(StringUtils.isNotEmpty(propertyValue)){
            result = Integer.parseInt(propertyValue);
        }

        return result;
    }

    public static long getLong(String key){
        String propertyValue = getString(key);
        long result = 0;

        if(StringUtils.isNotEmpty(propertyValue)){
            result = Long.parseLong(propertyValue);
        }

        return result;
    }
}
