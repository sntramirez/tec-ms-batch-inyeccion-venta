package ec.femsasalud.com.sales.injection.batch.shared.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilidad para operaciones con fechas.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {
    
    /**
     * Convierte una cadena de texto con formato ISO (yyyy-MM-dd'T'HH:mm:ss.SSS'Z') a un objeto Date.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha en formato ISO
     * @return Objeto Date con la fecha convertida o null si hay error
     */
    public static Date convertirISOStringADate(String fechaTexto) {
        return convertirStringADate(fechaTexto, Constantes.ISO_DATE_FORMAT);
    }
    
    /**
     * Convierte una cadena de texto con formato ISO (yyyy-MM-dd'T'HH:mm:ss.SSS'Z') a un objeto Date.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha en formato ISO
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Objeto Date con la fecha convertida o el valor por defecto en caso de error
     */
    public static Date convertirISOStringADate(String fechaTexto, Date valorPorDefecto) {
        try {
            return new SimpleDateFormat(Constantes.ISO_DATE_FORMAT).parse(fechaTexto);
        } catch (Exception e) {
            return valorPorDefecto;
        }
    }
    
    /**
     * Convierte una cadena de texto con formato estándar (yyyy-MM-dd HH:mm:ss) a un objeto Date.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha en formato estándar
     * @return Objeto Date con la fecha convertida o null si hay error
     */
    public static Date convertirStandardStringADate(String fechaTexto) {
        return convertirStringADate(fechaTexto, Constantes.STANDARD_DATE_FORMAT);
    }

    /**
     * Convierte una cadena de texto con formato estándar (yyyy-MM-ddTHH:mm:ss) a un objeto Date.
     *
     * @param fechaTexto La cadena de texto que contiene la fecha en formato estándar
     * @return Objeto Date con la fecha convertida o null si hay error
     */
    public static Date convertirStandardTStringADate(String fechaTexto) {
        return convertirStringADate(fechaTexto, Constantes.STANDARD_T_DATE_FORMAT);
    }
    
    /**
     * Convierte una cadena de texto con formato estándar (yyyy-MM-dd HH:mm:ss) a un objeto Date.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha en formato estándar
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Objeto Date con la fecha convertida o el valor por defecto en caso de error
     */
    public static Date convertirStandardStringADate(String fechaTexto, Date valorPorDefecto) {
        try {
            return new SimpleDateFormat(Constantes.STANDARD_DATE_FORMAT).parse(fechaTexto);
        } catch (Exception e) {
            return valorPorDefecto;
        }
    }
    
    /**
     * Convierte un objeto Date a una cadena de texto con formato estándar (yyyy-MM-dd HH:mm:ss).
     * 
     * @param fecha El objeto Date a convertir
     * @return Cadena de texto con la fecha en formato estándar o cadena vacía si hay error
     */
    public static String convertirDateAStandardString(Date fecha) {
        if (fecha == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(Constantes.STANDARD_DATE_FORMAT).format(fecha);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Convierte un objeto Date a una cadena de texto con formato ISO (yyyy-MM-dd'T'HH:mm:ss.SSS'Z').
     * 
     * @param fecha El objeto Date a convertir
     * @return Cadena de texto con la fecha en formato ISO o cadena vacía si hay error
     */
    public static String convertirDateAISOString(Date fecha) {
        if (fecha == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(Constantes.ISO_DATE_FORMAT).format(fecha);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Convierte un objeto Date a una cadena de texto según el formato especificado.
     * 
     * @param fecha El objeto Date a convertir
     * @param formato El formato de la fecha (patrón SimpleDateFormat)
     * @return Cadena de texto con la fecha en el formato especificado o cadena vacía si hay error
     */
    public static String convertirDateAString(Date fecha, String formato) {
        if (fecha == null || formato == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(formato).format(fecha);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Convierte un objeto Date a una cadena de texto según el formato especificado.
     * 
     * @param fecha El objeto Date a convertir
     * @param formato El formato de la fecha (patrón SimpleDateFormat)
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Cadena de texto con la fecha en el formato especificado o el valor por defecto en caso de error
     */
    public static String convertirDateAString(Date fecha, String formato, String valorPorDefecto) {
        if (fecha == null || formato == null) {
            return valorPorDefecto;
        }
        try {
            return new SimpleDateFormat(formato).format(fecha);
        } catch (Exception e) {
            return valorPorDefecto;
        }
    }
    
    /**
     * Convierte una cadena de texto a un objeto Date según el formato especificado.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha
     * @param formato El formato de la fecha (patrón SimpleDateFormat)
     * @return Objeto Date con la fecha convertida o null si hay error
     */
    public static Date convertirStringADate(String fechaTexto, String formato) {
        try {
            return new SimpleDateFormat(formato).parse(fechaTexto);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * Convierte una cadena de texto a un objeto Date según el formato especificado.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha
     * @param formato El formato de la fecha (patrón SimpleDateFormat)
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Objeto Date con la fecha convertida o el valor por defecto en caso de error
     */
    public static Date convertirStringADate(String fechaTexto, String formato, Date valorPorDefecto) {
        try {
            return new SimpleDateFormat(formato).parse(fechaTexto);
        } catch (ParseException e) {
            return valorPorDefecto;
        }
    }
    
    /**
     * Detecta automáticamente el formato de fecha e intenta convertir la cadena de texto a un objeto Date.
     * Intenta primero con el formato ISO y luego con el formato estándar.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha
     * @return Objeto Date con la fecha convertida o null si hay error
     */
    public static Date detectarYConvertirFecha(String fechaTexto) {
        Date fecha = convertirISOStringADate(fechaTexto);
        if (fecha == null) {
            fecha = convertirStandardStringADate(fechaTexto);
        }
        return fecha;
    }
    
    /**
     * Detecta automáticamente el formato de fecha e intenta convertir la cadena de texto a un objeto Date.
     * Intenta primero con el formato ISO y luego con el formato estándar.
     * 
     * @param fechaTexto La cadena de texto que contiene la fecha
     * @param valorPorDefecto Valor por defecto en caso de error
     * @return Objeto Date con la fecha convertida o el valor por defecto en caso de error
     */
    public static Date detectarYConvertirFecha(String fechaTexto, Date valorPorDefecto) {
        Date fecha = convertirISOStringADate(fechaTexto);
        if (fecha == null) {
            fecha = convertirStandardStringADate(fechaTexto);
        }
        return fecha != null ? fecha : valorPorDefecto;
    }
}
