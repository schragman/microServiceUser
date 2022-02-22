package net.schrage.photoapp.api.users.photoappapiuser.service;

import net.schrage.photoapp.api.users.photoappapiuser.data.UserEntity;
import net.schrage.photoapp.api.users.photoappapiuser.data.UsersRepository;
import net.schrage.photoapp.api.users.photoappapiuser.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private UsersRepository usersRepository;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.usersRepository = usersRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
}
