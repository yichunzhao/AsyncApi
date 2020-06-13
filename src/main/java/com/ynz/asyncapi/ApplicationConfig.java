package com.ynz.asyncapi;

import com.ynz.asyncapi.converter.StringToLocalDateTimeConverter;
import com.ynz.asyncapi.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    private StringToLocalDateTimeConverter stringToLocalDateTimeConverter;

    public ApplicationConfig(StringToLocalDateTimeConverter stringToLocalDateTimeConverter) {
        this.stringToLocalDateTimeConverter = stringToLocalDateTimeConverter;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(16000);
        configurer.setTaskExecutor(asyncTaskExecutor());
    }

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setThreadNamePrefix("AsyncApi-thread-");

        return asyncTaskExecutor;
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToLocalDateTimeConverter);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor());
    }

    @Bean
    protected LoggerInterceptor loginInterceptor() {
        return new LoggerInterceptor();
    }
}
