package net.schrage.photoapp.api.users.photoappapiuser.service;

import net.schrage.photoapp.api.users.photoappapiuser.data.UserEntity;
import net.schrage.photoapp.api.users.photoappapiuser.data.UsersRepository;
import net.schrage.photoapp.api.users.photoappapiuser.shared.UserDto;
import net.schrage.photoapp.api.users.photoappapiuser.ui.model.AlbumResponseModel;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private UsersRepository usersRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private RestTemplate restTemplate;
  private Environment env;

  @Autowired
  public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         RestTemplate restTemplate, Environment env) {
    this.usersRepository = usersRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.restTemplate = restTemplate;
    this.env = env;
  }

  @Override
  public UserDto createUser(UserDto userDetais) {

    userDetais.setUserId(UUID.randomUUID().toString());
    userDetais.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetais.getPassword()));
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDetais, UserEntity.class);

    usersRepository.save(userEntity);

    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = usersRepository.findByEmail(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException(username);
    }
    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    UserEntity userEntity = usersRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return new ModelMapper().map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {

    UserEntity userEntity = usersRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found");
    }

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    String albumsUrl = String.format(env.getProperty("albums.url"), userId);

    ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.
        exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });


    List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

    userDto.setAlbums(albumsList);

    return userDto;
  }
}
