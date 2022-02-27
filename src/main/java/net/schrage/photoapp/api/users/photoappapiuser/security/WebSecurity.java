package net.schrage.photoapp.api.users.photoappapiuser.security;

import net.schrage.photoapp.api.users.photoappapiuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private Environment environment;
  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.environment = environment;
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    //Nur einen bestimmten Zielpfad in der Anfrage zulassen
    //http.authorizeRequests().antMatchers("/users/**").permitAll();

    //Nur eine bestimmte IP-Adresse vom Anfrager zulassen.
    http.authorizeRequests()
        .antMatchers("/users/**")
        .permitAll() // falls keine bestimmte IP-Adresse gefiltert werden soll
        //.hasIpAddress(environment.getProperty("gateway.ip"))
        .and()
        .addFilter(getAuthenticationFilter());

    http.headers().frameOptions().disable();
  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception{
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
    authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path")); //Hier kann noch eine andere Login-Url hinterlegt werden
    //authenticationFilter.setAuthenticationManager(authenticationManager()); // wird jetzt im Constructor übergeben
    return authenticationFilter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }
}


