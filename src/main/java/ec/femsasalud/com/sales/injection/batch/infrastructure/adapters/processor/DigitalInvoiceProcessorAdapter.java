package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.processor;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ec.femsasalud.com.sales.injection.batch.domain.service.DigitalInvoiceProcessorPort;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri.RespuestaAutorizacion;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbFactura;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbNotaCredito;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.repository.TbFacturasJpaRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.repository.TbNotaCreditoJpaRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class DigitalInvoiceProcessorAdapter implements DigitalInvoiceProcessorPort {

    private final TbFacturasJpaRepository facturasRepository;
    private final TbNotaCreditoJpaRepository notaCreditoRepository;

    @Override
    @Transactional
    public void processAndSaveInvoice(FaColaFacturaDigitalEntity colaFactura,
                                      RespuestaAutorizacion.Autorizacion autorizacion,
                                      String xmlFilePath) {

        String documentType = colaFactura.getDocumentType();

        if ("CREDIT_NOTE_BILL".equalsIgnoreCase(documentType)) {
            processNotaCredito(colaFactura, autorizacion, xmlFilePath);
        } else {
            processFactura(colaFactura, autorizacion, xmlFilePath);
        }
    }

    private void processFactura(FaColaFacturaDigitalEntity colaFactura,
                                RespuestaAutorizacion.Autorizacion autorizacion,
                                String xmlFilePath) {
        try {
            log.info("Procesando factura con clave de acceso: {}", colaFactura.getClaveAcceso());

            // Verificar si ya existe
            TbFactura existente = facturasRepository.findByClaveAcceso(colaFactura.getClaveAcceso());
            if (existente != null) {
                log.warn("La factura con clave de acceso {} ya existe. Actualizando...", colaFactura.getClaveAcceso());
                updateFacturaFromAutorizacion(existente, autorizacion, xmlFilePath);
                facturasRepository.save(existente);
            } else {
                TbFactura factura = buildFacturaFromAutorizacion(colaFactura, autorizacion, xmlFilePath);
                facturasRepository.save(factura);
                log.info("Factura guardada exitosamente con ID: {}", factura.getId());
            }

        } catch (Exception e) {
            log.error("Error al procesar factura con clave de acceso: {}", colaFactura.getClaveAcceso(), e);
            throw new RuntimeException("Error al procesar factura", e);
        }
    }

    private void processNotaCredito(FaColaFacturaDigitalEntity colaFactura,
                                    RespuestaAutorizacion.Autorizacion autorizacion,
                                    String xmlFilePath) {
        try {
            log.info("Procesando nota de crédito con clave de acceso: {}", colaFactura.getClaveAcceso());

            // Verificar si ya existe
            TbNotaCredito existente = notaCreditoRepository.findByClaveAcceso(colaFactura.getClaveAcceso());
            if (existente != null) {
                log.warn("La nota de crédito con clave de acceso {} ya existe. Actualizando...", colaFactura.getClaveAcceso());
                updateNotaCreditoFromAutorizacion(existente, autorizacion, xmlFilePath);
                notaCreditoRepository.save(existente);
            } else {
                TbNotaCredito notaCredito = buildNotaCreditoFromAutorizacion(colaFactura, autorizacion, xmlFilePath);
                notaCreditoRepository.save(notaCredito);
                log.info("Nota de crédito guardada exitosamente con ID: {}", notaCredito.getId());
            }

        } catch (Exception e) {
            log.error("Error al procesar nota de crédito con clave de acceso: {}", colaFactura.getClaveAcceso(), e);
            throw new RuntimeException("Error al procesar nota de crédito", e);
        }
    }

    private TbFactura buildFacturaFromAutorizacion(FaColaFacturaDigitalEntity colaFactura,
                                                   RespuestaAutorizacion.Autorizacion autorizacion,
                                                   String xmlFilePath) throws Exception {

        Document doc = parseXml(autorizacion.getComprobante());
        Element root = doc.getDocumentElement();

        TbFactura factura = TbFactura.builder()
                .claveAcceso(colaFactura.getClaveAcceso())
                .numeroAutorizacion(autorizacion.getNumeroAutorizacion())
                .fechaAutorizacion(parseDate(autorizacion.getFechaAutorizacion()))
                .estado("AUTORIZADO")
                .archivo(xmlFilePath)
                .tipoAmbiente(autorizacion.getAmbiente())
                .build();

        // Extraer datos del XML
        populateFacturaFromXml(factura, root);

        return factura;
    }

    private void updateFacturaFromAutorizacion(TbFactura factura,
                                               RespuestaAutorizacion.Autorizacion autorizacion,
                                               String xmlFilePath) throws Exception {

        factura.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
        factura.setFechaAutorizacion(parseDate(autorizacion.getFechaAutorizacion()));
        factura.setEstado("AUTORIZADO");
        factura.setArchivo(xmlFilePath);
        factura.setTipoAmbiente(autorizacion.getAmbiente());

        Document doc = parseXml(autorizacion.getComprobante());
        Element root = doc.getDocumentElement();
        populateFacturaFromXml(factura, root);
    }

    private TbNotaCredito buildNotaCreditoFromAutorizacion(FaColaFacturaDigitalEntity colaFactura,
                                                           RespuestaAutorizacion.Autorizacion autorizacion,
                                                           String xmlFilePath) throws Exception {

        Document doc = parseXml(autorizacion.getComprobante());
        Element root = doc.getDocumentElement();

        TbNotaCredito notaCredito = TbNotaCredito.builder()
                .claveAcceso(colaFactura.getClaveAcceso())
                .numeroAutorizacion(autorizacion.getNumeroAutorizacion())
                .fechaAutorizacion(parseDate(autorizacion.getFechaAutorizacion()))
                .estado("AUTORIZADO")
                .archivo(xmlFilePath)
                .tipoAmbiente(autorizacion.getAmbiente())
                .build();

        // Extraer datos del XML
        populateNotaCreditoFromXml(notaCredito, root);

        return notaCredito;
    }

    private void updateNotaCreditoFromAutorizacion(TbNotaCredito notaCredito,
                                                   RespuestaAutorizacion.Autorizacion autorizacion,
                                                   String xmlFilePath) throws Exception {

        notaCredito.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
        notaCredito.setFechaAutorizacion(parseDate(autorizacion.getFechaAutorizacion()));
        notaCredito.setEstado("AUTORIZADO");
        notaCredito.setArchivo(xmlFilePath);
        notaCredito.setTipoAmbiente(autorizacion.getAmbiente());

        Document doc = parseXml(autorizacion.getComprobante());
        Element root = doc.getDocumentElement();
        populateNotaCreditoFromXml(notaCredito, root);
    }

    private void populateFacturaFromXml(TbFactura factura, Element root) {
        // Extraer infoTributaria
        Element infoTributaria = getFirstElement(root, "infoTributaria");
        if (infoTributaria != null) {
            factura.setRuc(getElementText(infoTributaria, "ruc"));
            factura.setRazonSocialComprador(getElementText(infoTributaria, "razonSocial"));
            factura.setCodDoc(getElementText(infoTributaria, "codDoc"));
            factura.setPtoEmision(getElementText(infoTributaria, "estab"));
            factura.setCodPuntoEmision(getElementText(infoTributaria, "ptoEmi"));
            factura.setCodSecuencial(getElementText(infoTributaria, "secuencial"));
            factura.setDirEstablecimiento(getElementText(infoTributaria, "dirMatriz"));
        }

        // Extraer infoFactura
        Element infoFactura = getFirstElement(root, "infoFactura");
        if (infoFactura != null) {
            factura.setFechaEmisionTmp(getElementText(infoFactura, "fechaEmision"));
            factura.setDirEstablecimiento(getElementText(infoFactura, "dirEstablecimiento"));
            factura.setContribuyenteEspecial(getElementText(infoFactura, "contribuyenteEspecial"));
            factura.setObligadoContabilidad(getElementText(infoFactura, "obligadoContabilidad"));
            factura.setTipoIdComprador(getElementText(infoFactura, "tipoIdentificacionComprador"));
            factura.setIdentificacionComprador(getElementText(infoFactura, "identificacionComprador"));
            factura.setRazonSocialComprador(getElementText(infoFactura, "razonSocialComprador"));
            factura.setTotalSinImpuestos(parseBigDecimal(getElementText(infoFactura, "totalSinImpuestos")));
            factura.setTotalDescuento(parseBigDecimal(getElementText(infoFactura, "totalDescuento")));
            factura.setImporteTotal(parseBigDecimal(getElementText(infoFactura, "importeTotal")));
            factura.setMoneda(getElementText(infoFactura, "moneda"));
            factura.setPropina(parseBigDecimal(getElementText(infoFactura, "propina")));
        }
    }

    private void populateNotaCreditoFromXml(TbNotaCredito notaCredito, Element root) {
        // Extraer infoTributaria
        Element infoTributaria = getFirstElement(root, "infoTributaria");
        if (infoTributaria != null) {
            notaCredito.setRuc(getElementText(infoTributaria, "ruc"));
            notaCredito.setRazonSocialComprador(getElementText(infoTributaria, "razonSocial"));
            notaCredito.setCodDoc(getElementText(infoTributaria, "codDoc"));
            notaCredito.setPtoEmision(getElementText(infoTributaria, "estab"));
            notaCredito.setCodPuntoEmision(getElementText(infoTributaria, "ptoEmi"));
            notaCredito.setCodSecuencial(getElementText(infoTributaria, "secuencial"));
            notaCredito.setDirEstablecimiento(getElementText(infoTributaria, "dirMatriz"));
        }

        // Extraer infoNotaCredito
        Element infoNotaCredito = getFirstElement(root, "infoNotaCredito");
        if (infoNotaCredito != null) {
            notaCredito.setFechaEmision(parseDate(getElementText(infoNotaCredito, "fechaEmision")));
            notaCredito.setDirEstablecimiento(getElementText(infoNotaCredito, "dirEstablecimiento"));
            notaCredito.setContribuyenteEspecial(getElementText(infoNotaCredito, "contribuyenteEspecial"));
            notaCredito.setObligadoContabilidad(getElementText(infoNotaCredito, "obligadoContabilidad"));
            notaCredito.setTipoIdComprador(getElementText(infoNotaCredito, "tipoIdentificacionComprador"));
            notaCredito.setIdentificacionComprador(getElementText(infoNotaCredito, "identificacionComprador"));
            notaCredito.setRazonSocialComprador(getElementText(infoNotaCredito, "razonSocialComprador"));
            notaCredito.setCodDocModificado(getElementText(infoNotaCredito, "codDocModificado"));
            notaCredito.setNumDocModificado(getElementText(infoNotaCredito, "numDocModificado"));
            notaCredito.setFechEmiDocSustento(parseDate(getElementText(infoNotaCredito, "fechaEmisionDocSustento")));
            notaCredito.setTotalSinImpuestos(parseBigDecimal(getElementText(infoNotaCredito, "totalSinImpuestos")));
            notaCredito.setValorModificacion(parseBigDecimal(getElementText(infoNotaCredito, "valorModificacion")));
            notaCredito.setMoneda(getElementText(infoNotaCredito, "moneda"));
            notaCredito.setMotivo(getElementText(infoNotaCredito, "motivo"));
        }
    }

    private Document parseXml(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
    }

    private Element getFirstElement(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node instanceof Element) {
                return (Element) node;
            }
        }
        return null;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            log.warn("No se pudo parsear el valor como BigDecimal: {}", value);
            return BigDecimal.ZERO;
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            // Formato esperado: dd/MM/yyyy HH:mm:ss
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            try {
                // Intentar con formato simple: dd/MM/yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.parse(dateStr);
            } catch (Exception ex) {
                log.warn("No se pudo parsear la fecha: {}", dateStr);
                return null;
            }
        }
    }
}
