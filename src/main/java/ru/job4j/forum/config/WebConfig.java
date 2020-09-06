package ru.job4j.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.job4j.forum.interceptor.AddUserInfoInterceptor;
import ru.job4j.forum.service.access.AccessService;
import ru.job4j.forum.service.jpa.UserService;

/**
 * The config beans
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 21.08.2020
 * @version 1.0
 */
@Configuration
@ComponentScan("ru.job4j.forum")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private UserService users;

    public WebConfig(UserService users, AccessService accessService) {
        this.users = users;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/styles/**")
                .addResourceLocations("/WEB-INF/resources/css/")
                .setCachePeriod(0);
        registry
                .addResourceHandler("/scripts/**")
                .addResourceLocations("/WEB-INF/resources/js/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new AddUserInfoInterceptor(users));
    }
}
