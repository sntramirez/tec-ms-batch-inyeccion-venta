package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoClient {
    private String document;
    private String documentType;
    private String email;
    private String address;
    private String city;
    private String firstName;
    private String firstSurName;
    private String secondName;
    private String secondSurname;
    private String phone;
    private String creationType;
    private List<CoGroup> groups;
}
