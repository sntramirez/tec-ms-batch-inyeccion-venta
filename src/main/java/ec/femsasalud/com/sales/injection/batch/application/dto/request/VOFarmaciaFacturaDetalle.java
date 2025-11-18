package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VOFarmaciaFacturaDetalle {
	
	private Long documentoVenta;

	private Long farmacia;

	private Long codigo;

	private Long cantidad;

	private BigDecimal costo;

	private BigDecimal pvp;

	private BigDecimal precioFybeca;

	private BigDecimal venta;

	private Long unidades;

	private BigDecimal porcentajeIva;

	private Long medico;

	private Long item;

	private Long secuencial;

	private Long listaDespacho;

	private Long seccion;

	private String tipoNegocio;

	private Long documentoAbono;

	private Long cupon;

	private String transaccion_fvc;

	private Long usuarioRealiza;

	private Long usuarioCallCenter;

	private BigDecimal descuentoTc;
	
	private Long oferta;

    private Long promocion; 
    
    private String tipoIdentificador;

    private String identificador;
    
    private String promocionDet;

	private String cpVarTres;

	private String campaignIdTemp;
	
public void setItemAutorizadoValue(Long documentoVenta, Long farmacia, Long contador, VOProduct detail, DTOAuthorizedItem itemAutorizado) {
		
		this.documentoVenta = documentoVenta;
		this.farmacia = farmacia;
		this.codigo = contador;
		this.cantidad = detail.getQuantity();
		this.venta = detail.getPriceList();
		this.porcentajeIva = detail.getIvaPercent().divide(new BigDecimal(100));
		this.item = Long.parseLong(detail.getSku());
		this.costo = itemAutorizado.getCost();
		this.pvp = detail.getPriceList().compareTo(itemAutorizado.getCostWithoutTax()) >= 0 ? detail.getPriceList():itemAutorizado.getCostWithoutTax();
		this.precioFybeca = detail.getPriceList().compareTo(itemAutorizado.getPurchaseWithoutTax()) >= 0 ? detail.getPriceList():itemAutorizado.getPurchaseWithoutTax();
		this.unidades = itemAutorizado.getSaleUnit().longValue();


		
		String campaignId = !detail.getDiscounts().isEmpty()?
				detail.getDiscounts().get(0).getCampaignId()!=null?
						detail.getDiscounts().get(0).getCampaignId():"false":"false";

		this.cpVarTres = detail.getDiscounts().get(0).getCampaignId();

		if (campaignId.contains("-")) {
			String[] campaign = campaignId.split("-");
			try {
				if ("PR".equals(campaign[1])) {
					this.promocion = Long.valueOf(campaign[0]);
				} else if("OF".equals(campaign[1])) {
					this.oferta  = Long.valueOf(campaign[0]);
				}
			} catch (Exception e) {}
			
		}
	}

}
