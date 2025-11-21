package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.filesystem;

import ec.femsasalud.com.sales.injection.batch.application.service.ParametrosService;
import ec.femsasalud.com.sales.injection.batch.domain.service.FileStoragePort;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlFileStorageAdapter implements FileStoragePort {

    private final ParametrosService parametrosService;

    @Value("${sri.xml.storage.path:}")
    private String defaultXmlStoragePath;

    @Override
    public String saveXmlFile(String xmlContent, String claveAcceso, String documentType) {
        try {
            // Obtener path de almacenamiento (primero de BD, si no existe usa valor de properties)
            String xmlStoragePath = parametrosService.getParametroOrDefault(ParametroKey.SRI_XML_STORAGE_PATH, defaultXmlStoragePath);

            // Validar que el path esté configurado
            if (xmlStoragePath == null || xmlStoragePath.trim().isEmpty()) {
                log.error("Path de almacenamiento de XMLs no configurado");
                throw new IllegalStateException(
                    "El path de almacenamiento de XMLs debe estar configurado en FA_PARAMETROS_FACTURADOR (parámetro: sri_xml_storage_path)");
            }

            log.debug("Usando path de almacenamiento: {}", xmlStoragePath);

            // Crear estructura basePath/ddMMyyyy/ usando la clave de acceso
            Path fullPath = createDirectoryStructure(xmlStoragePath, claveAcceso);

            // Nombre de archivo: claveAcceso.xml (sin prefijo)
            String fileName = claveAcceso + ".xml";
            Path filePath = fullPath.resolve(fileName);

            Files.write(filePath, xmlContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("XML guardado exitosamente en: {}", filePath.toString());
            return filePath.toString();

        } catch (IOException e) {
            log.error("Error al guardar archivo XML para clave de acceso: {}", claveAcceso, e);
            throw new RuntimeException("Error al guardar archivo XML", e);
        }
    }

    private Path createDirectoryStructure(String basePath, String claveAcceso) throws IOException {
        // La clave de acceso tiene formato: ddMMyyyy... (los primeros 8 caracteres son la fecha)
        // Ejemplo: 05112025... -> día=05, mes=11, año=2025
        if (claveAcceso == null || claveAcceso.length() < 8) {
            throw new IllegalArgumentException("Clave de acceso inválida, debe tener al menos 8 caracteres");
        }

        // Extraer fecha de la clave de acceso (ddMMyyyy)
        String fechaStr = claveAcceso.substring(0, 8); // ddMMyyyy

        // Crear estructura: basePath/ddMMyyyy
        Path fullPath = Paths.get(basePath, fechaStr);

        if (!Files.exists(fullPath)) {
            Files.createDirectories(fullPath);
            log.debug("Directorio creado: {}", fullPath);
        }

        return fullPath;
    }
}
