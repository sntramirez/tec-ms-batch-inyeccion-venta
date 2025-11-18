package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaParametrosFacturadorEntity;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository.FaParametrosFacturadorJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class XmlFileStorageService {

    private final FaParametrosFacturadorJpaRepository parametrosRepository;

    private static final String PARAM_XML_PATH = "XML_STORAGE_PATH";
    private static final String DEFAULT_PATH = "/tmp/facturas";

    public String saveXmlFile(String xmlContent, String claveAcceso, String documentType) {
        try {
            String basePath = getXmlStoragePath();
            String fileName = generateFileName(claveAcceso, documentType);
            Path fullPath = createDirectoryStructure(basePath, documentType);
            Path filePath = fullPath.resolve(fileName);

            Files.write(filePath, xmlContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("XML guardado exitosamente en: {}", filePath.toString());
            return filePath.toString();

        } catch (IOException e) {
            log.error("Error al guardar archivo XML para clave de acceso: {}", claveAcceso, e);
            throw new RuntimeException("Error al guardar archivo XML", e);
        }
    }

    private String getXmlStoragePath() {
        try {
            FaParametrosFacturadorEntity parametro = parametrosRepository.findByClave(PARAM_XML_PATH);
            if (parametro != null && parametro.getValor() != null && !parametro.getValor().isEmpty()) {
                log.debug("Path de almacenamiento obtenido de parámetros: {}", parametro.getValor());
                return parametro.getValor();
            }
        } catch (Exception e) {
            log.warn("Error al obtener parámetro de path, usando valor por defecto", e);
        }

        log.info("Usando path por defecto: {}", DEFAULT_PATH);
        return DEFAULT_PATH;
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
            String basePath = getXmlStoragePath();
            Path directoryPath = createDirectoryStructure(basePath, documentType);
            String fileName = generateFileName(claveAcceso, documentType);
            return directoryPath.resolve(fileName);
        } catch (IOException e) {
            log.error("Error al obtener path del archivo", e);
            return null;
        }
    }
}
