package testplatcorp.data.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpVigilante {
	
	private String status;
    
	@JsonProperty("data")
    private IpVigilanteDetail details;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public IpVigilanteDetail getDetails() {
		return details;
	}
	public void setDetails(IpVigilanteDetail details) {
		this.details = details;
	}
}
