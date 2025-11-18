package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VOFarmaciaServicioDetalle {
	
	private Long documentoVenta;

	private Long farmacia;

	private Long codigo;

	private Long cantidad;

	private BigDecimal pvp;

	private BigDecimal venta;

	private Long unidades;

	private BigDecimal porcentajeIva;

	private Long item;
	
	public void setServiceValue(Long documentoVenta, Long farmacia, Long contador, VOProduct detail) {
		this.documentoVenta = documentoVenta;
		this.farmacia = farmacia;
		this.codigo = contador;
		this.cantidad = detail.getQuantity();
		this.venta = detail.getPriceList();
		this.porcentajeIva = detail.getIvaPercent().divide(new BigDecimal(100));
		this.item = Long.parseLong(detail.getSku());
		this.pvp = detail.getPriceList();
		this.unidades = 1L;
	}

}
