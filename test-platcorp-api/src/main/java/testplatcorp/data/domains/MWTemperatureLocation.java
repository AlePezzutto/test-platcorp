package testplatcorp.data.domains;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MWTemperatureLocation {
	
	@JsonProperty("consolidated_weather")
    private List<MWConsolidatedWeather> weather;

	public MWTemperatureLocation() {
		weather = new ArrayList<>();
	}
	
	public List<MWConsolidatedWeather> getWeather() {
		return weather;
	}

	public void setWeather(List<MWConsolidatedWeather> weather) {
		this.weather = weather;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( MWConsolidatedWeather d : weather) {
			sb.append(d.toString() + "\n");
		}
		return  sb.toString();
	}
	
}
