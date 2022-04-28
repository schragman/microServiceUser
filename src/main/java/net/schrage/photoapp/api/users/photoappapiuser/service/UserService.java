package net.schrage.photoapp.api.users.photoappapiuser.service;

import net.schrage.photoapp.api.users.photoappapiuser.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDetais);
  UserDto getUserDetailsByEmail(String email);

  UserDto getUserByUserId(String userId);

}
