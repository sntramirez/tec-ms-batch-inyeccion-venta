package ec.femsasalud.com.sales.injection.batch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GeoUser {

    private String id;

    private String subclass;

    private String firstname;

    private String password;

    private String sex;

    private String lastname;

    private Integer eliminated;

    private Date lastPasswordUpdate;

    private String previousPasswords;

    private Date updated;

    private String cashierType;

    private String node;

    private String localId;

    private Integer allowedSale;

    private String rut;

    private Date eliminatedUpdate;

    private String documentTypeInscription;
}
