package by.grsu.backend.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletResponseWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LogInfoAspect {

    @Pointcut("@annotation(LogInfo)")
    public void logInfoMethod(){
    }

    @Around("logInfoMethod()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature =(MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Field[] fields = methodSignature.getReturnType().getFields();

//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        methodBefore(joinPoint/*,uuid*/);

        try{
            Object proceed = joinPoint.proceed();
            methodAfterReturning(proceed/*, uuid*/);
            return proceed;

        }finally {
            stopWatch.stop();

            log.info("Execution time for " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }

    public void methodBefore(JoinPoint joinPoint/*, String uuid*/) {
        try {
            // В следующих двух массивах число и позиция значений параметров и имен параметров соответствуют друг другу.
            Object[] objs = joinPoint.getArgs();
            String [] argNames = ((MethodSignature) joinPoint.getSignature ()).getParameterNames(); // имя параметра
            Map<String, Object> paramMap = new HashMap<String, Object>();
            for (int i = 0; i < objs.length; i++) {
                if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
                    paramMap.put(argNames[i], objs[i]);
                }
            }
            if (paramMap.size() > 0) {
                log.info ("\n" + "Метод: " + joinPoint.getSignature () + "\nParameters: "+ JSONObject.toJSONString(paramMap));
//                log.info ("\n [" + uuid +"]" + " Метод: " + joinPoint.getSignature () + "\n Parameters: "+ JSONObject.toJSONString(paramMap));
            }
        } catch (Exception e) {
//            log.error("[" + uuid + "]AOP methodBefore:", e);
            log.error("AOP methodBefore:", e);
        }
    }

    public void methodAfterReturning(Object o/*, String uuid*/) {
        try {
            if (o != null) {
//                log.info("[" + uuid + "] Содержание ответа: " + JSONObject.toJSON(o));
                log.info("\nСодержание ответа:\n" + JSONObject.toJSON(o));
            }
            } catch (Exception e) {
            log.error("AOP methodAfterReturning:", e);
//            log.error("[" + uuid + "]AOP methodAfterReturning:", e);
        }
    }


}
