package com.sigma.beacon_control.common.util;

public class SecretUtility {


    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateSecretUID() {
        int count = 64;
        StringBuilder builder = new StringBuilder();

        while (count-- != 0){
            int character = (int)(Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }

        return builder.toString();
    }
}
