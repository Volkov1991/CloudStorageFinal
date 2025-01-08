package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.exception.ErrorGettingFileList;
import netology.cloudstoragefinal.exception.ErrorInputData;
import netology.cloudstoragefinal.exception.ErrorUploadFile;
import netology.cloudstoragefinal.exception.UnauthorizedError;
import netology.cloudstoragefinal.model.FileEntity;
import netology.cloudstoragefinal.model.UserEntity;
import netology.cloudstoragefinal.repository.FileRepository;
import netology.cloudstoragefinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static netology.cloudstoragefinal.exception.ErrorMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public void uploadFile(String username, MultipartFile file) throws IOException {
        UserEntity userEntity = getUserEntity(username);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setSize(file.getSize());
        fileEntity.setContent(file.getBytes());
        fileEntity.setUser(userEntity);
        try {
            fileRepository.save(fileEntity);
        } catch (Exception e) {
            log.error("Error uploading file: {}", file.getOriginalFilename());
            throw new ErrorInputData(ERROR_INPUT_DATA.toString());
        }
    }

    public void deleteFile(String username, String fileName) {
        try {
            fileRepository.delete(getFileEntityByUserFileName(username, fileName));
        } catch (Exception e) {
            log.error("Error deleting file: {}", fileName);
            throw new ErrorInputData(ERROR_DELETE_FILE.toString());
        }
    }

    public byte[] downloadFile(String username, String fileName) {
        FileEntity fileEntity = getFileEntityByUserFileName(username, fileName);
        if (fileEntity == null) {
            log.error("File not found: {}", fileName);
            throw new ErrorUploadFile(ERROR_UPLOAD_FILE.toString());
        }
        return fileEntity.getContent();
    }

    public void editFileName(String username, String oldFileName, String newFileName) {
        FileEntity fileEntity = getFileEntityByUserFileName(username, oldFileName);
        fileEntity.setFileName(newFileName);
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles(String username, int limit) {
        UserEntity userEntity = getUserEntity(username);
        List<FileEntity> fileEntities = fileRepository.findAllByUser(userEntity).orElse(new ArrayList<>());
        return fileEntities.stream().limit(limit).toList();
    }

    public FileEntity getFileEntityByUserFileName(String username, String fileName) {
        UserEntity user = getUserEntity(username);
        return fileRepository.findByFileNameAndUser(fileName, user)
                .orElseThrow(() -> new ErrorGettingFileList(ERROR_FILE_LIST.toString()));
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UnauthorizedError(ERROR_UNAUTHORIZED.toString()));
    }
}
