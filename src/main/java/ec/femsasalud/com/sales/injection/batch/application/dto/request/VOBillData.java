package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VOBillData {

    private String dianResolutionDate;
    private String dianResolutionMinVal;
    private String dianResolutionMaxVal;
    private String dianResolutionNumber;
    private String dianPrefix;
    private Boolean dianEmpowerment;
}
