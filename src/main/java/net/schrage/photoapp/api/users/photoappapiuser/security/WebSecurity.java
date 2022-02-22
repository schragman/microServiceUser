package net.schrage.photoapp.api.users.photoappapiuser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private Environment environment;

  @Autowired
  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    //Nur einen bestimmten Zielpfad in der Anfrage zulassen
    //http.authorizeRequests().antMatchers("/users/**").permitAll();

    //Nur eine bestimmte IP-Adresse vom Anfrager zulassen.
    http.authorizeRequests().antMatchers("/users/**").hasIpAddress(environment.getProperty("gateway.ip"));
    http.headers().frameOptions().disable();
  }
}


