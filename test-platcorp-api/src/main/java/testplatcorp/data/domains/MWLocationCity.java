package testplatcorp.data.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MWLocationCity implements Comparable<MWLocationCity> {
	
	@JsonProperty("distance")
	private Integer distancia;
	private String woeid;
	
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	public String getWoeid() {
		return woeid;
	}
	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	
	@Override
	public int compareTo(MWLocationCity o) {

		if(this.distancia > o.distancia)
			return 1;
		if(this.distancia < o.distancia)
			return -1;
		
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("{woeid = %s, distancia = %d}", woeid, distancia);
	}
}
