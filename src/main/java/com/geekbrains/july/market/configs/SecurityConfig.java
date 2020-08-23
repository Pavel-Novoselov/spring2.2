/*
package com.geekbrains.july.market.configs;

import com.geekbrains.july.market.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile("unknown")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UsersService usersService;

    @Autowired
    public void setUserService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAnyRole("ADMIN", "GUEST")
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint((req, resp, exception) -> {
                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().println("{ \"error\": \"" + exception.getMessage() + "\" }");
                    })
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

//    @Configuration
//    @Profile("js")
//    @Order(10)
//    public class JsSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                .antMatchers("/profile/*").authenticated()
//                .antMatchers("/orders/*").hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/*").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//    }

    @Configuration
    @Order(20)
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/profile/*").authenticated()
                .antMatchers("/orders/*").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usersService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
*/