package com.jamith.rmi.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Password Generating and Validating Utility
 * <p>
 * This Code taken from https://www.codota.com/code/java/classes/javax.crypto.spec.PBEKeySpec
 */
public class PasswordUtil {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private PasswordUtil() {
    }

    /**
     * @return Generated Salt Value
     *
     * This Code taken from https://www.codota.com/code/java/classes/javax.crypto.spec.PBEKeySpec
     */
    public static String getSalt() {
        StringBuilder returnValue = new StringBuilder(SALT_LENGTH);
        for (int i = 0; i < SALT_LENGTH; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    /**
     * This Code taken from https://www.codota.com/code/java/classes/javax.crypto.spec.PBEKeySpec
     *
     * @param password password
     * @param salt salt value
     * @return encoded byte array
     * @throws InvalidKeySpecException
     */
    private static byte[] hash(char[] password, byte[] salt) throws InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a : " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     *
     * This Code taken from https://www.codota.com/code/java/classes/javax.crypto.spec.PBEKeySpec
     *
     * @param password plain text password
     * @param salt salt value
     * @return encoded password
     * @throws InvalidKeySpecException
     */
    public static String generateSecurePassword(String password, String salt) throws InvalidKeySpecException {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    /**
     * Check if password is correct
     * This Code taken from https://www.codota.com/code/java/classes/javax.crypto.spec.PBEKeySpec
     *
     * @param providedPassword provided plain text password by the user
     * @param securedPassword encoded password in the database
     * @param salt salt value
     * @return true if password provided by user is correct
     * @throws InvalidKeySpecException
     */
    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) throws InvalidKeySpecException {
        boolean returnValue = false;
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}
