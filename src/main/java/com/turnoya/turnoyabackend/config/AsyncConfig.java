package com.turnoya.turnoyabackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * Configuración de ejecución asíncrona para TurnoYa.
 * <p>
 * Habilita {@code @Async} en toda la aplicación y define el
 * {@link ThreadPoolTaskExecutor} que usan, entre otros, el listener de eventos
 * de turno ({@code TurnoEventListener}) para el envío de notificaciones por
 * correo, de modo que estas operaciones no bloqueen el hilo que atiende la
 * petición HTTP del paciente/médico.
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    public static final String TASK_EXECUTOR_BEAN = "taskExecutor";

    @Bean(name = TASK_EXECUTOR_BEAN)
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("turnoya-async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(20);
        executor.initialize();
        return executor;
    }

    /**
     * Captura cualquier excepción que escape de un método {@code @Async void}
     * (los métodos void no permiten propagar la excepción al llamador), para
     * que quede registrada en logs en vez de perderse silenciosamente.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return this::manejarErrorAsincrono;
    }

    private void manejarErrorAsincrono(Throwable throwable, Method method, Object... params) {
        log.error("Error no controlado ejecutando la tarea asíncrona '{}': {}",
                method.getName(), throwable.getMessage(), throwable);
    }
}
