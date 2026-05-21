package app.product.ex03;

import app.product.ex03.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private BankService bankService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Demo OTP aspect:");
        System.out.println("withdraw with null otp -> " + bankService.withdraw(100, null));
        System.out.println("withdraw with empty otp -> " + bankService.withdraw(100, ""));
        System.out.println("withdraw with wrong otp -> " + bankService.withdraw(100, "000000"));
        System.out.println("withdraw with correct otp -> " + bankService.withdraw(100, "123456"));

        System.out.println("transfer with wrong otp -> " + bankService.transfer("alice", 50, "000000"));
        System.out.println("transfer with correct otp -> " + bankService.transfer("alice", 50, "123456"));

        System.out.println("getBalance (no otp) -> " + bankService.getBalance("alice"));
    }
}

