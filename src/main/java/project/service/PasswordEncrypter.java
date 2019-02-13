/**
 * The encrypt method of this Class
 * gets the password, encrypts it
 * and returns
 */

package project.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncrypter implements PasswordEncoder {

   public  String encode (CharSequence var1){
      String password = var1.toString();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        return password;
    }

  public   boolean matches(CharSequence var1, String var2) {
       String str = var1.toString();
        return str.equals(var2);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
