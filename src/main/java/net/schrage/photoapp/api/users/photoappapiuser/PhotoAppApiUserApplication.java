package net.schrage.photoapp.api.users.photoappapiuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableDiscoveryClient  //no longer needed
public class PhotoAppApiUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotoAppApiUserApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
