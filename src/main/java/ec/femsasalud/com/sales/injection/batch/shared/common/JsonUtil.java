package ec.femsasalud.com.sales.injection.batch.shared.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utilidad para operaciones de conversión entre objetos y JSON.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Convierte un objeto a su representación en formato JSON.
     * 
     * @param objeto El objeto a convertir
     * @return String con la representación JSON del objeto
     */
    public static String convertirAJson(Object objeto) {
        try {
            return objectMapper.writeValueAsString(objeto);
        } catch (Exception e) {
            return Constantes.MSG_ERROR_CONVERSION_JSON + ": " + e.getMessage();
        }
    }
    
    /**
     * Convierte un objeto a su representación en formato JSON.
     * 
     * @param objeto El objeto a convertir
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return String con la representación JSON del objeto o el valor por defecto en caso de error
     */
    public static String convertirAJson(Object objeto, String valorPorDefecto) {
        try {
            return objectMapper.writeValueAsString(objeto);
        } catch (Exception e) {
            return valorPorDefecto;
        }
    }
    
    /**
     * Convierte una cadena JSON a un objeto del tipo especificado.
     * 
     * @param <T> El tipo de objeto al que se convertirá el JSON
     * @param json La cadena JSON a convertir
     * @param claseDestino La clase del objeto de destino
     * @return Objeto del tipo especificado o null si hay error
     */
    public static <T> T convertirAObject(String json, Class<T> claseDestino) {
        try {
            return objectMapper.readValue(json, claseDestino);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Convierte una cadena JSON a un objeto del tipo especificado.
     * 
     * @param <T> El tipo de objeto al que se convertirá el JSON
     * @param json La cadena JSON a convertir
     * @param claseDestino La clase del objeto de destino
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Objeto del tipo especificado o el valor por defecto en caso de error
     */
    public static <T> T convertirAObject(String json, Class<T> claseDestino, T valorPorDefecto) {
        try {
            return objectMapper.readValue(json, claseDestino);
        } catch (Exception e) {
            return valorPorDefecto;
        }
    }
}
