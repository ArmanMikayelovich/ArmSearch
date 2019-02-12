/**
 * The encrypt method of this Class
 * gets the password, encrypts it
 * and returns
 */

package project.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncrypter {

   public static String encrypt (String password){
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        return password;
    }
}
