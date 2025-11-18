package ec.femsasalud.com.sales.injection.batch.shared.common;

public enum DocumentType {
    BILL("BILL"),
    CREDIT_NOTE_BILL("CREDIT_NOTE_BILL");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DocumentType fromString(String type) {
        for (DocumentType docType : DocumentType.values()) {
            if (docType.value.equalsIgnoreCase(type)) {
                return docType;
            }
        }
        throw new IllegalArgumentException("Tipo de documento no soportado: " + type);
    }
}
