package com.advcourse.conferenceassistant.config;

import com.advcourse.conferenceassistant.exception.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                //.antMatchers("/staff/topic-add/**", "/staff/dashboard", "/staff/conference-page/**", "/staff/conference-dashboard/**","/staff/topic-dashboard/**").authenticated()
                .antMatchers("/staff/conference-add","/staff/delete-conference/**").hasAnyAuthority("ADMIN")
                .antMatchers("/staff/staff-delete/**","/staff/add-roles/**").hasAnyAuthority("SUPERVISOR")
                .antMatchers("/staff/list", "/staff/add-privileges/**", "/staff/add-conferenceId/**").hasAnyAuthority("SUPERVISOR","ADMIN")
                .antMatchers("/staff/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/staff/login")
                .defaultSuccessUrl("/staff/dashboard")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/staff/logout_admin"))
                .logoutUrl("/staff/logout_admin")
                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select email, pass, true from staff where email=?")
                .authoritiesByUsernameQuery("select u.email, ur.roles from staff u inner join staff_role ur on u.id = ur.staff_id where u.email=?");
    }
}