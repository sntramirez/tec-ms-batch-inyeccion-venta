package ec.femsasalud.com.sales.injection.batch.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class XmlFileStorageService {

    @Value("${sri.xml.storage.path}")
    private String xmlStoragePath;

    public String saveXmlFile(String xmlContent, String claveAcceso, String documentType) {
        try {
            log.debug("Usando path de almacenamiento: {}", xmlStoragePath);
            String fileName = generateFileName(claveAcceso, documentType);
            Path fullPath = createDirectoryStructure(xmlStoragePath, documentType);
            Path filePath = fullPath.resolve(fileName);

            Files.write(filePath, xmlContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("XML guardado exitosamente en: {}", filePath.toString());
            return filePath.toString();

        } catch (IOException e) {
            log.error("Error al guardar archivo XML para clave de acceso: {}", claveAcceso, e);
            throw new RuntimeException("Error al guardar archivo XML", e);
        }
    }

    private Path createDirectoryStructure(String basePath, String documentType) throws IOException {
        // Crear estructura: basePath/año/mes/tipo_documento
        LocalDate now = LocalDate.now();
        String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
        String month = now.format(DateTimeFormatter.ofPattern("MM"));

        String docTypeFolder = "CREDIT_NOTE_BILL".equalsIgnoreCase(documentType) ? "notas_credito" : "facturas";

        Path fullPath = Paths.get(basePath, year, month, docTypeFolder);

        if (!Files.exists(fullPath)) {
            Files.createDirectories(fullPath);
            log.debug("Directorio creado: {}", fullPath);
        }

        return fullPath;
    }

    private String generateFileName(String claveAcceso, String documentType) {
        String prefix = "CREDIT_NOTE_BILL".equalsIgnoreCase(documentType) ? "NC_" : "FAC_";
        return prefix + claveAcceso + ".xml";
    }

    public Path getFilePath(String claveAcceso, String documentType) {
        try {
            Path directoryPath = createDirectoryStructure(xmlStoragePath, documentType);
            String fileName = generateFileName(claveAcceso, documentType);
            return directoryPath.resolve(fileName);
        } catch (IOException e) {
            log.error("Error al obtener path del archivo", e);
            return null;
        }
    }
}
