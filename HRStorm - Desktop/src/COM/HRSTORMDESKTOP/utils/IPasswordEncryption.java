
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.utils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 *
 * @author Moetez
 */
public interface IPasswordEncryption {
    String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException;
    String Encrypt(String passwordToHash,String salt);
    boolean Verify(String password,String salt);
   
}