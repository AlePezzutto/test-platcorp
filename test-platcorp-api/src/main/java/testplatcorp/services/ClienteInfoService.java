package testplatcorp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import testplatcorp.data.domains.ClienteInfo;
import testplatcorp.data.domains.IpVigilante;
import testplatcorp.data.domains.IpVigilanteDetail;
import testplatcorp.data.domains.MWConsolidatedWeather;
import testplatcorp.data.domains.MWLocationCity;
import testplatcorp.data.domains.MWTemperatureLocation;
import testplatcorp.repositories.ClienteInfoRepository;

@Service
public class ClienteInfoService {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${ipvigilante.uri}")
	private String uriGeoLocalizacao;
	
	@Value("${metawheater.uri.location.search}")
	private String uriWhoeid;
	
	@Value("${metawheater.uri.location}")
	private String uriTemperatura;
	
	@Autowired
	private ClienteInfoRepository repo;
	
	public ClienteInfo insert(Integer id, String ip) {
		
		ClienteInfo clienteInfo = null;
		Date hoje;
		
		try {
			hoje = sdf.parse(sdf.format(new Date()));
		}
		catch(ParseException pe) {
			throw new RuntimeException(pe);
		}
		
		IpVigilanteDetail geoLoc = getGeoLocalizacaoPorIP(ip);
		List<MWLocationCity> locCities = getLocalizacaoWoeid(geoLoc.getLatitude(),geoLoc.getLongitude());
		List<MWConsolidatedWeather> temp;
		
		for(MWLocationCity lc : locCities) {
			
			temp = getTemperatura(lc.getWoeid());
			
			if(temp != null && !temp.isEmpty()) {
				
				MWConsolidatedWeather theTemp = temp.stream().filter(w -> w.getData().compareTo(hoje) == 0).findAny().orElse(null);
				if( theTemp != null) {
					clienteInfo = new ClienteInfo();
					clienteInfo.setId(id);
					clienteInfo.setDataCriacao(hoje);
					clienteInfo.setIp(ip);
					clienteInfo.setTempAtual(theTemp.getTempAtual());
					clienteInfo.setTempMinima(theTemp.getTempMinima());
					clienteInfo.setTempMaxima(theTemp.getTempMaxima());
				}
				
				break;
			}
		}
		
		if(clienteInfo != null) {
			clienteInfo = repo.save(clienteInfo);
			return clienteInfo;
		}
		else
			throw new RuntimeException("Não há temperatura para a geolocalização deste ip");
		
	}
	
	private IpVigilanteDetail getGeoLocalizacaoPorIP(String ip) {

	    RestTemplate restTemplate = new RestTemplate();
		System.out.println("Vai buscar geo-localização. IP: " + ip);
		IpVigilante dados = restTemplate.getForObject(String.format(uriGeoLocalizacao,  ip), IpVigilante.class);
		
	    return dados.getDetails();
	}
	
	private List<MWLocationCity> getLocalizacaoWoeid(String lt, String lg) {
		
		System.out.println("Vai buscar getLocalizacaoWoeid. lat:long: " + lt + ":" + lg);
	    
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<MWLocationCity>> response = restTemplate.exchange(String.format(uriWhoeid, lt, lg),
	                                                                          HttpMethod.GET,
	                                                                          null,
	                                                                          new ParameterizedTypeReference<List<MWLocationCity>>(){});
			
		List<MWLocationCity> locationCities = response.getBody();
		Collections.sort(locationCities);
	    return locationCities;                		
	}

	private List<MWConsolidatedWeather> getTemperatura(String woeid) {
			
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("Vai buscar Tempetaturas. Woeid: " + woeid);
		
		MWTemperatureLocation dados = restTemplate.getForObject(String.format(uriTemperatura, woeid), MWTemperatureLocation.class);
	    return dados.getWeather();
	}
	
}
