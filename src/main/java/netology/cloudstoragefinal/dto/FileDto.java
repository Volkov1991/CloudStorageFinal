package netology.cloudstoragefinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FileDto {
    String filename;
    Long size;
}
