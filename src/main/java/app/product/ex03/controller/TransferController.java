package app.product.ex03.controller;

import app.product.ex03.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bankings")
public class TransferController{

	@Autowired
	private BankService bankService;

	@PostMapping("/transfer")
	public String transferMoney(
			@RequestParam String toUser,
			@RequestParam double amount,
			@RequestParam String otp
	) {

		bankService.transfer(toUser, amount, otp);

		return "Chuyển tiền thành công!";
	}
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam double amount, @RequestParam String otp) {
		bankService.withdraw(amount, otp);
		return "Rút tiền thành công!";

	}

}
