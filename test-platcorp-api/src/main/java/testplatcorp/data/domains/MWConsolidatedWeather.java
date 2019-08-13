package testplatcorp.data.domains;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import testplatcorp.util.CustomJsonDateDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MWConsolidatedWeather {

	//"applicable_date": "2019-08-09",
	@JsonProperty("applicable_date")
	@JsonDeserialize(using=CustomJsonDateDeserializer.class)
	private Date data;
	
	@JsonProperty("the_temp")
	private Float tempAtual;
    
	@JsonProperty("min_temp")
    private Float tempMinima;
    
	@JsonProperty("max_temp")
    private Float tempMaxima;
    
    public Date getData() {
    	return data;
    }
    public void setData(Date data) {
    	this.data = data;
    }
    public Float getTempAtual() {
		return tempAtual;
	}
	public void setTempAtual(Float tempAtual) {
		this.tempAtual = tempAtual;
	}
	public Float getTempMinima() {
		return tempMinima;
	}
	public void setTempMinima(Float tempMinima) {
		this.tempMinima = tempMinima;
	}
	public Float getTempMaxima() {
		return tempMaxima;
	}
	public void setTempMaxima(Float tempMaxima) {
		this.tempMaxima = tempMaxima;
	}
	
    @Override
	public String toString() {
    	return String.format("{Data = %tD, Temperatura Mínima = %f, Temperatura Máxima = %f", data, tempMinima, tempMaxima);
	}
    
}