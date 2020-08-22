package com.geekbrains.july.market.utils.aspects;


import com.geekbrains.july.market.utils.ToFile;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Aspect
@Component
public class AppLoggingAspect {

    @Pointcut("execution(public * com.geekbrains.july.market.services.ProductsService.saveOrUpdate(..))") // pointcut expression
    public void saveOrUpdateProduct() {
    }

    @Pointcut("execution(public * com.geekbrains.july.market.services.CategoriesService.saveCategory(..))") // pointcut expression
    public void saveCategory() {
    }

    @Pointcut("saveOrUpdateProduct() || saveCategory()") // pointcut expression
    public void saveProductOrCategory() {
    }

    @Around("saveProductOrCategory()")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Object out = proceedingJoinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = methodSignature.getName();
        if (name.toLowerCase().contains("categor")){
            name="Category";
        } else{
            name = "Product";
        }

        ToFile.writeToFile(name + " " + args[0] + " was created or changed at " + LocalDateTime.now() + " by User " +
                           SecurityContextHolder.getContext().getAuthentication().getName());
        return out;
    }
}
