package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth {
	private String login;
	private String tranKey;
	private String nonce;
	private String seed;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getTranKey() {
		return tranKey;
	}
	public void setTranKey(String tranKey) {
		this.tranKey = tranKey;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	
	
}
