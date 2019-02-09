/**
 * The encrypt method of this Class
 * gets the password, encrypts it
 * and returns
 */

package project.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypter {

   public static String encrypt (String password){
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        return password;
    }
}
