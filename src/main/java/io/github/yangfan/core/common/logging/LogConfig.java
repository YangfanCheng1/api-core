package io.github.yangfan.core.common.logging;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

@Aspect
@Slf4j
@Configuration
public class LogConfig {

    private static final Map<Class<?>, Logger> LOGGERS = Maps.newConcurrentMap();

    private Logger logger(JoinPoint joinPoint) {
        val clazz = joinPoint.getTarget().getClass();
        return LOGGERS.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    void restController() {
        // empty method
    }

    @Before("restController()")
    void beforeController(JoinPoint joinPoint) {
        val log = logger(joinPoint);
        val attributes = RequestContextHolder.currentRequestAttributes();
        if (attributes instanceof ServletRequestAttributes) {
            val request = ((ServletRequestAttributes) attributes).getRequest();
            val name = joinPoint.getSignature().getName();
            log.info("{} {}{}",
                    request.getMethod(),
                    request.getServletPath(),
                    Optional.ofNullable(request.getQueryString()).map(q -> "?" + q).orElse(""));
            log.debug("{}() - args:  {}", name, joinPoint.getArgs());
        }
    }

    @SneakyThrows
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint point) {
        val start = System.currentTimeMillis();
        val result = point.proceed();
        val end = System.currentTimeMillis();
        log.info("{}({}) -> {} - time taken: {}ms", point.getSignature().getName(), point.getArgs(), result, end - start);
        return result;
    }

}
