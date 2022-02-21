package net.schrage.photoapp.api.users.photoappapiuser.ui.controllers;

import net.schrage.photoapp.api.users.photoappapiuser.service.UserService;
import net.schrage.photoapp.api.users.photoappapiuser.shared.UserDto;
import net.schrage.photoapp.api.users.photoappapiuser.ui.model.CreateUserRequestModel;
import net.schrage.photoapp.api.users.photoappapiuser.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private Environment env;

  @Autowired
  private UserService userService;

  @GetMapping("/status/check")
  public String status() {
    return "Working on port " + env.getProperty("local.server.port");
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody @Valid CreateUserRequestModel userDetails) {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = modelMapper.map(userDetails, UserDto.class);
    UserDto createdUser = userService.createUser(userDto);

    CreateUserResponseModel returnUser = modelMapper.map(createdUser, CreateUserResponseModel.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(returnUser);
  }

}
