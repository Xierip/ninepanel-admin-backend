package dev.nine.ninepanel.authentication.domain;

import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService        userDetailsService;
  private final AuthenticationTokenFilter authenticationTokenFilter;

  WebSecurityConfiguration(@Qualifier("authenticationUserDetailsService") UserDetailsService userDetailsService,
      AuthenticationTokenFilter authenticationTokenFilter) {
    this.userDetailsService = userDetailsService;
    this.authenticationTokenFilter = authenticationTokenFilter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(this.passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(ApiLayers.SESSIONS,
            ApiLayers.SESSIONS + "/refresh",
            ApiLayers.USERS + "/forgot-password",
            ApiLayers.USERS + "/reset-password",
            ApiLayers.USERS + "/register"
        ).permitAll()
        .anyRequest().authenticated()
        .and()
        .cors().and()
        .csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint());

    http.addFilterBefore(this.authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
