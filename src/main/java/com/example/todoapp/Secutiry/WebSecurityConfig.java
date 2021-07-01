package com.example.todoapp.Secutiry;

import com.example.todoapp.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST,"/user").permitAll()
                .antMatchers(HttpMethod.GET,"/user").permitAll()
                .antMatchers(HttpMethod.DELETE,"/user/**").hasRole("ADMIN")
                .antMatchers("/activity").authenticated()
                .antMatchers("/activitytype").hasRole("ADMIN")
                .antMatchers("/activitytype/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new jwtFilter(),UsernamePasswordAuthenticationFilter.class);
    }

}