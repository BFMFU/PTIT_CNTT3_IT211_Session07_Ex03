package app.product.ex03.security;

import org.springframework.stereotype.Component;


@Component
public class OtpVerifier {

    public boolean verify(String otp) {
        if (otp == null) return false;
        if (otp.trim().isEmpty()) return false;
        return "123456".equals(otp);
    }
}

