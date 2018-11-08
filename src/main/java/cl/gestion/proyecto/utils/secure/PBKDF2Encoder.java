package cl.gestion.proyecto.utils.secure;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements PasswordEncoder {
    @Override
    public String encode(final CharSequence cs) {
        String secret = "marcoqlcondenaoxdxdxdxdxdxdxdx";
        Integer iteration = 256; //257 //33
        Integer keylength = 256;
        String secretKyFactory = "PBKDF2WithHmacSHA512";
        try {
            byte[] result = SecretKeyFactory.getInstance(secretKyFactory)
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
