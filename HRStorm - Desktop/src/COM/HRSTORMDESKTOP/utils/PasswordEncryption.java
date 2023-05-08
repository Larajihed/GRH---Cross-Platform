/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.utils;

import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.BCrypt;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;



/**
 *
 * @author Moetez
 */
public class PasswordEncryption implements IPasswordEncryption {

   /*public String Encrypt(String passwordToHash, String salt) {
    String generatedPassword = null;
    try {
        // Concatenate the salt and password
        String saltedPassword = salt + passwordToHash;

        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Add salted password bytes to digest
        md.update(saltedPassword.getBytes());

        // Get the hash's bytes
        byte[] bytes = md.digest();

        // This bytes[] has bytes in decimal format;
        // Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
        System.err.println(e.getMessage());
    }
    return generatedPassword;
}
*/
    @Override
    public String Encrypt(String passwordToHash, String salt) {
        String hashedPassword = BCrypt.hashpw(passwordToHash, salt);
        return hashedPassword;
    }


    @Override
    public String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return salt.toString();
    }

    @Override
    public boolean Verify(String password, String hashed) {
            System.out.println("veri");
            System.out.println(password);
        return BCrypt.checkpw(password, hashed);    }

}