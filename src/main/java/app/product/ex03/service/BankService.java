package app.product.ex03.service;

import app.product.ex03.security.RequiresOTP;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @RequiresOTP
    public String withdraw(double amount, String otp) {
        // OTP validation moved to Aspect - business logic remains clean
        return "Rút tiền thành công";
    }

    @RequiresOTP
    public String transfer(String toUser, double amount, String otp) {
        // OTP validation moved to Aspect - business logic remains clean
        return "Chuyển khoản thành công";
    }

    public String getBalance(String user) {
        return "Số dư của " + user + " là 1000";
    }
}

