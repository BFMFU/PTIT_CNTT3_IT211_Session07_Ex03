package app.product.ex03.security;

import org.springframework.stereotype.Component;

/**
 * Simple OTP verification component. In a real system this would check an
 * external OTP provider. For this exercise we accept exactly "123456".
 */
@Component
public class OtpVerifier {

    public boolean verify(String otp) {
        if (otp == null) return false;
        if (otp.trim().isEmpty()) return false;
        return "123456".equals(otp);
    }
}

