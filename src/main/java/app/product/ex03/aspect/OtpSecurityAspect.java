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
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Method method = sig.getMethod();
        Object[] args = pjp.getArgs();

        // Find OTP argument: by convention we expect the OTP to be the last String parameter
        String otp = null;
        Class<?>[] paramTypes = sig.getParameterTypes();
        if (paramTypes != null && paramTypes.length > 0) {
            for (int i = paramTypes.length - 1; i >= 0; i--) {
                if (paramTypes[i].equals(String.class)) {
                    Object val = args[i];
                    otp = val == null ? null : val.toString();
                    break;
                }
            }
        }

        // Check for empty/null OTP -> return a meaningful failure message (match the examples)
        if (otp == null || otp.trim().isEmpty()) {
            return failureMessageFor(method.getName());
        }

        // Verify OTP
        if (!otpVerifier.verify(otp)) {
            return failureMessageFor(method.getName());
        }

        // proceed when OTP is valid
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

