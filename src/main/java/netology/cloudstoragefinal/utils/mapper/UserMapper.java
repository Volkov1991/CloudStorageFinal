package netology.cloudstoragefinal.utils.mapper;

import netology.cloudstoragefinal.dto.UserDto;
import netology.cloudstoragefinal.model.UserEntity;

public class UserMapper {

    public static UserEntity mapToUserEntity(UserDto user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getLogin());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }
}
