package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VOClient {
	private String  clientId;
	private String  firstName;
	private String  secondName;
	private String  firstSurname;
	private String  secondSurname;
	private String  cellphoneNumber;
	private String  address;
	private Long  idComune;
	private Long  idCity;
	private Long  countryCode;
	private Long  regionCode;
	private String  creationType;
	private String  email;
	private String  particularNumber;
	private String   idTypeClient;
	
	
	

	public String getParticularNumber() {
		return particularNumber;
	}
	public void setParticularNumber(String particularNumber) {
		this.particularNumber = particularNumber;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstSurname() {
		return firstSurname;
	}
	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}
	public String getSecondSurname() {
		return secondSurname;
	}
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
	public String getCellphoneNumber() {
		return cellphoneNumber;
	}
	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public Long getIdCity() {
		return idCity;
	}
	public void setIdCity(Long idCity) {
		this.idCity = idCity;
	}
	public Long getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(Long countryCode) {
		this.countryCode = countryCode;
	}
	public Long getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	public String getCreationType() {
		return creationType;
	}
	public void setCreationType(String creationType) {
		this.creationType = creationType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdTypeClient() {
		return idTypeClient;
	}
	public void setIdTypeClient(String idTypeClient) {
		this.idTypeClient = idTypeClient;
	}	



}
