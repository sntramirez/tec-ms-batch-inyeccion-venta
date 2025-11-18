package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VOProduct {

	private String  sku;
	private BigDecimal  priceList;
	private BigDecimal  finalPrice;
	private Long  quantity;
	private BigDecimal  taxAmount;
	private String  taxType;
	private BigDecimal  insurancePercent;
	private String  copay;
	private BigDecimal  insuranceAmount;
	private String  points;
	private BigDecimal  ivaPercent;
	private String description;

	/**
	 * ABF
	 * */

	private BigDecimal pagoCliente;
	private BigDecimal pagoCompania;
	
	private BigDecimal totalPrice;
	private BigDecimal unitPrice;

	private List<V0ProductDiscountDetail> productDiscountDetail;
	

	private List<VODiscount> discounts= new ArrayList<>();


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public BigDecimal getPriceList() {
		return priceList;
	}


	public void setPriceList(BigDecimal priceList) {
		this.priceList = priceList;
	}


	public BigDecimal getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}


	public Long getQuantity() {
		return quantity;
	}


	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}




	public BigDecimal getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}


	public String getTaxType() {
		return taxType;
	}


	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}


	public BigDecimal getInsurancePercent() {
		return insurancePercent;
	}


	public void setInsurancePercent(BigDecimal insurancePercent) {
		this.insurancePercent = insurancePercent;
	}


	public String getCopay() {
		return copay;
	}


	public void setCopay(String copay) {
		this.copay = copay;
	}


	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}


	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}


	public BigDecimal getIvaPercent() {
		return ivaPercent;
	}


	public void setIvaPercent(BigDecimal ivaPercent) {
		this.ivaPercent = ivaPercent;
	}


	public List<VODiscount> getDiscounts() {
		return discounts;
	}


	public void setDiscounts(List<VODiscount> discounts) {
		this.discounts = discounts;
	}


	public BigDecimal getPagoCliente() {
		return pagoCliente;
	}

	public void setPagoCliente(BigDecimal pagoCliente) {
		this.pagoCliente = pagoCliente;
	}

	public BigDecimal getPagoCompania() {
		return pagoCompania;
	}

	public void setPagoCompania(BigDecimal pagoCompania) {
		this.pagoCompania = pagoCompania;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<V0ProductDiscountDetail> getProductDiscountDetail() {
		return productDiscountDetail;
	}

	public void setProductDiscountDetail(List<V0ProductDiscountDetail> productDiscountDetail) {
		this.productDiscountDetail = productDiscountDetail;
	}
}
