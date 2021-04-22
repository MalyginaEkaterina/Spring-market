package ru.geekbrains.spring.market;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Before("execution(public * ru.geekbrains.spring.market.controllers.ProductController.*(..))") // pointcut expression
    public void beforeProductControllerMethods(JoinPoint joinPoint) {
        System.out.println("Был вызван метод: " + joinPoint.getSignature().toShortString());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            System.out.print("Аргументы: ");
            for (Object o : args) {
                System.out.print(o + " ");
            }
        }
        System.out.println();
    }
}
