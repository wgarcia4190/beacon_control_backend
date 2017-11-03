package com.sigma.beacon_control.common.util;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/**
 * Created by Wilson on 4/16/17.
 */
public final class Password {

    private static final String ALGORITHM = "SHA-256";

    private Password(){}

    public static boolean checkPassword(String password, String encryptedPassword) {
        try {
            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm(ALGORITHM);
            passwordEncryptor.setPlainDigest(false);
            return passwordEncryptor.checkPassword(password, encryptedPassword);
        }catch (EncryptionOperationNotPossibleException e){
            e.printStackTrace();
            return false;
        }
    }

    public static String encryptPassword(String password) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ALGORITHM);
        passwordEncryptor.setPlainDigest(false);
        return passwordEncryptor.encryptPassword(password);
    }
}
