package ru.job4j.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * The security configuration via Spring Security
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 02.09.2020
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Qualifier("forumDataSource")
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select name, password, enabled "
                        + "from \"user\" "
                        + "where name = ?")
                .authoritiesByUsernameQuery(
                        " select u.name, ur.role_id "
                                + "from user_roles as ur, \"user\" as u "
                                + "where u.name = ? and u.id = ur.user_id")
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/user/login")
//                .permitAll()
                .antMatchers("/**")
                .permitAll()
//                .antMatchers("/post/*")
//                .hasAnyRole("ADMIN", "POSTER")
//                .antMatchers("/comment/*")
//                .hasAnyRole("ADMIN", "COMMENTER")
                .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/user/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
