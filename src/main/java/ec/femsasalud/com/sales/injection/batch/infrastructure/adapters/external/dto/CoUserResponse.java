package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoUserResponse {
    @JsonProperty("token")
    private String token;
}
