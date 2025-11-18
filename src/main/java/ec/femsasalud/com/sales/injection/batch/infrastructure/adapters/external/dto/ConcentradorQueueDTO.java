package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import java.sql.Timestamp;

public class ConcentradorQueueDTO {

	private Long Id;
	private String orderId;
	private String Json;
	private String error;
	private String reintegrar;
	private Long intentos;
	private Long codigo;
	private String mensaje;
	private Timestamp fechaInserta;
	private String usuarioInserta;
	private Timestamp fechaActualiza;
	private String usuarioActualiza;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getJson() {
		return Json;
	}
	public void setJson(String json) {
		Json = json;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getReintegrar() {
		return reintegrar;
	}
	public void setReintegrar(String reintegrar) {
		this.reintegrar = reintegrar;
	}
	public Long getIntentos() {
		return intentos;
	}
	public void setIntentos(Long intentos) {
		this.intentos = intentos;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Timestamp getFechaInserta() {
		return fechaInserta;
	}
	public void setFechaInserta(Timestamp fechaInserta) {
		this.fechaInserta = fechaInserta;
	}
	public String getUsuarioInserta() {
		return usuarioInserta;
	}
	public void setUsuarioInserta(String usuarioInserta) {
		this.usuarioInserta = usuarioInserta;
	}
	public Timestamp getFechaActualiza() {
		return fechaActualiza;
	}
	public void setFechaActualiza(Timestamp fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}
	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}
	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}
	
}
