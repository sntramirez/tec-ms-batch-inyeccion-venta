package ec.femsasalud.com.sales.injection.batch.domain.validation.rules;


import ec.femsasalud.com.sales.injection.batch.shared.common.Constantes;

import java.util.ArrayList;
import java.util.List;

public class HeaderValidationRules {

    public List<ValidItem> buildRulesFor(String emittedDocument) {
        List<ValidItem> rules = buildBaseRules();

        if (Constantes.CODIGO_DOCUMENTO_NC.equalsIgnoreCase(emittedDocument)) {
            rules.add(new ValidItem("motDev", Constantes.T_STRING, "S"));
        } else {
            rules.add(new ValidItem("calluser", Constantes.T_STRING, "N"));
        }

        return rules;
    }

    private List<ValidItem> buildBaseRules() {
        List<ValidItem> rules = new ArrayList<>();
        rules.add(new ValidItem("orderId", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("user", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("discountTotal", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("untaxedTotal", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("base0", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("base12", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("issuerRuc", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("total", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("taxTotal", Constantes.T_NUMBER, "S"));
        rules.add(new ValidItem("posNumber", Constantes.T_STRING, "N"));
        rules.add(new ValidItem("client", Constantes.T_OBJECT, "S"));
        rules.add(new ValidItem("payments", Constantes.T_ARRAY, "S"));
        rules.add(new ValidItem("products", Constantes.T_ARRAY, "S"));
        rules.add(new ValidItem("billUrl", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("xmlUrl", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("emittedDocument", Constantes.T_STRING, "S"));
        rules.add(new ValidItem("authorizationCode", Constantes.T_STRING, "S"));
        return rules;
    }
}
