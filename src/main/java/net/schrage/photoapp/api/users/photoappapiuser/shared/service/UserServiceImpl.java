package net.schrage.photoapp.api.users.photoappapiuser.shared.service;

import net.schrage.photoapp.api.users.photoappapiuser.shared.UserDto;
import net.schrage.photoapp.api.users.photoappapiuser.shared.service.data.UserEntity;
import net.schrage.photoapp.api.users.photoappapiuser.shared.service.data.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  UsersRepository usersRepository;

  @Autowired
  public UserServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDto createUser(UserDto userDetais) {

    userDetais.setUserId(UUID.randomUUID().toString());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDetais, UserEntity.class);
    userEntity.setEncryptedPassword("test");

    usersRepository.save(userEntity);

    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

    return returnValue;
  }
}
