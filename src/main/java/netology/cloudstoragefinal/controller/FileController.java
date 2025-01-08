package netology.cloudstoragefinal.controller;

import netology.cloudstoragefinal.dto.EditFileNameRequest;
import netology.cloudstoragefinal.dto.FileDto;
import netology.cloudstoragefinal.service.FileService;
import netology.cloudstoragefinal.utils.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(Principal userPrincipal,
                                        @RequestBody MultipartFile file) throws IOException {

        fileService.uploadFile(userPrincipal.getName(), file);
        log.info("File uploaded successfully: {}", file.getOriginalFilename());

        return ResponseEntity.ok("Success upload");
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(Principal userPrincipal,
                                        @RequestParam("filename") String fileName) {

        fileService.deleteFile(userPrincipal.getName(), fileName);
        log.info("File deleted successfully: {}", fileName);

        return ResponseEntity.ok("Success deleted");
    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(Principal userPrincipal, @RequestParam("filename") String fileName) {

        byte[] downloadedFile = fileService.downloadFile(userPrincipal.getName(), fileName);
        log.info("File downloaded successfully: {}", fileName);

        return ResponseEntity.ok(downloadedFile);
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFileName(Principal userPrincipal,
                                          @RequestParam("filename") String oldFileName,
                                          @RequestBody EditFileNameRequest newFileName) {

        fileService.editFileName(userPrincipal.getName(), oldFileName, newFileName.getFilename());
        log.info("File renamed successfully, new filename is: {}", newFileName.getFilename());

        return ResponseEntity.ok("Success upload");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllFiles(Principal userPrincipal,
                                         @RequestParam("limit") int limit) {
        List<FileDto> allFiles = fileService.getAllFiles(userPrincipal.getName(), limit)
                .stream().
                map(FileMapper::mapToFileDto)
                .toList();
        return ResponseEntity.ok(allFiles);
    }
}
