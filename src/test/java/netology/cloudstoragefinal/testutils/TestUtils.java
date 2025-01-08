package netology.cloudstoragefinal.testutils;

import netology.cloudstoragefinal.dto.UserDto;
import netology.cloudstoragefinal.model.FileEntity;
import netology.cloudstoragefinal.model.UserEntity;
import lombok.SneakyThrows;

public class TestUtils {

    public static UserEntity USER_ENTITY;
    public static UserDto USER_DTO;
    public static FileEntity FILE_ENTITY;
    public static final UserEntity USER_ENTITY_REPO;

    static {
        USER_ENTITY_REPO = initUserEntity("test", "test");
        USER_ENTITY = initUserEntity("user", "pasword");
        USER_DTO = initUserDto();
        FILE_ENTITY = initFileEntity();
    }

    @SneakyThrows
    private static FileEntity initFileEntity() {
        return FileEntity.builder()
                .fileName("test.txt")
                .size(4L)
                .content("test".getBytes())
                .user(USER_ENTITY)
                .build();
    }

    private static UserDto initUserDto() {
        return UserDto.builder()
                .login("user")
                .password("user")
                .build();
    }

    private static UserEntity initUserEntity(String username, String password) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .build();
    }
}
