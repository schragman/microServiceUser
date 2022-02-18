package net.schrage.photoapp.api.users.photoappapiuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient  //no longer needed
public class PhotoAppApiUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotoAppApiUserApplication.class, args);
  }

}
