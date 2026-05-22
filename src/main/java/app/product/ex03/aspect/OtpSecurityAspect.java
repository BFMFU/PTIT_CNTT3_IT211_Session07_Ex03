package app.product.ex03.aspect;

import app.product.ex03.security.OtpVerifier;
import app.product.ex03.security.RequiresOTP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class OtpSecurityAspect {

    @Autowired
    private OtpVerifier otpVerifier;

    @Around("@annotation(app.product.ex03.security.RequiresOTP)")
    public Object aroundRequiresOtp(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        String otp = null;
        for (int i = args.length - 1; i >= 0; i--) {
            if (args[i] instanceof String) {
                otp = (String) args[i];
                break;
            }
        }

        if (otp == null || otp.isBlank() || !otpVerifier.verify(otp)) {
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            return failureMessageFor(method.getName());
        }

        return pjp.proceed();
    }

    private String failureMessageFor(String methodName) {
        String lower = methodName.toLowerCase();
        if (lower.contains("withdraw")) {
            return "Rút tiền thất bại: Sai OTP";
        }
        if (lower.contains("transfer")) {
            return "Chuyển khoản thất bại: Sai OTP";
        }
        return "Hành động thất bại: Sai OTP";
    }
}

