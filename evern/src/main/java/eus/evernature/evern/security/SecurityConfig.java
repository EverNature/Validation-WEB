package eus.evernature.evern.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SuccessHandler successHandler;
  
    @Autowired
    FailureHandler failureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        super.configure(auth);
    }
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      String[] staticResources = {
          "/styles/**",
          "/images/**",
          "/font/**",
          "/js/**",
          "/register/**",
          "/login/**"
      };

      http.headers().xssProtection().and().contentSecurityPolicy("script-src 'self'");
  
      http.authorizeRequests()
          .antMatchers(staticResources).permitAll()
          .anyRequest().authenticated()
          .and()
          .formLogin().loginPage("/login").successForwardUrl("/home")
          .successHandler(successHandler).permitAll()
          .failureHandler(failureHandler).permitAll()
          .and()
          .logout().permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
