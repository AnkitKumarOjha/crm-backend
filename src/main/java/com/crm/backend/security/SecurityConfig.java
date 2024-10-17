package com.crm.backend.security;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()  // Allow login and signup
                .antMatchers("/api/admin/**").hasRole("ADMIN")  // Restrict to admin
                .antMatchers("/api/sales/**").hasRole("SALES_REP")  // Restrict to sales rep
                .anyRequest().authenticated();  // All other requests must be authenticated
    }
}

