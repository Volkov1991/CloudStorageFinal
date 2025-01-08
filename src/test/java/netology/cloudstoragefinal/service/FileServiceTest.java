package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.model.FileEntity;
import netology.cloudstoragefinal.repository.FileRepository;
import netology.cloudstoragefinal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static netology.cloudstoragefinal.testutils.TestUtils.FILE_ENTITY;
import static netology.cloudstoragefinal.testutils.TestUtils.USER_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Transactional
class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        when(userRepository.findUserByUsername(any())).thenReturn(Optional.ofNullable(USER_ENTITY));
    }

    @Test
    @SneakyThrows
    @Rollback
    void uploadFile() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        fileService.uploadFile(USER_ENTITY.getUsername(), multipartFile);
        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    @Rollback
    void deleteFile() {
        when(fileRepository.findByFileNameAndUser(any(), any())).thenReturn(Optional.ofNullable(FILE_ENTITY));
        fileService.deleteFile(FILE_ENTITY.getUser().getUsername(), FILE_ENTITY.getFileName());
        verify(fileRepository, times(1)).delete(FILE_ENTITY);
        assertThat(fileRepository.findById(FILE_ENTITY.getId())).isEmpty();
    }

    @Test
    void downloadFile() {
        when(fileRepository.findByFileNameAndUser(any(), any())).thenReturn(Optional.ofNullable(FILE_ENTITY));
        byte[] file = fileService.downloadFile(FILE_ENTITY.getUser().getUsername(), FILE_ENTITY.getFileName());
        assertThat(file).isNotNull();
        assertThat((long) file.length).isEqualTo(FILE_ENTITY.getSize());
        assertThat(file).isEqualTo(FILE_ENTITY.getContent());
    }

    @Test
    @Rollback
    void editFileName() {
        String newFileName = "newFileName";
        when(fileRepository.findByFileNameAndUser(any(), any())).thenReturn(Optional.ofNullable(FILE_ENTITY));
        fileService.editFileName(USER_ENTITY.getUsername(), FILE_ENTITY.getFileName(), newFileName);
        verify(fileRepository, times(1)).save(any());
    }
}