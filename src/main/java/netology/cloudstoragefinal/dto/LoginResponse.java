package netology.cloudstoragefinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty(value = "auth-token")
    private String token;
}
