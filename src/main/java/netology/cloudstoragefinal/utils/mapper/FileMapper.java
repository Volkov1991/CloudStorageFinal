package netology.cloudstoragefinal.utils.mapper;

import netology.cloudstoragefinal.dto.FileDto;
import netology.cloudstoragefinal.model.FileEntity;

public class FileMapper {
    public static FileDto mapToFileDto(FileEntity file) {
        return new FileDto(file.getFileName(), file.getSize());
    }
}
