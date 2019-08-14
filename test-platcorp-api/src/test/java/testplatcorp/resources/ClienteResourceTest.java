package testplatcorp.resources;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import testplatcorp.data.domains.Cliente;
import testplatcorp.data.domains.ClienteInfo;
import testplatcorp.data.domains.dto.ClienteDTO;
import testplatcorp.repositories.ClienteInfoRepository;
import testplatcorp.repositories.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) 
@ActiveProfiles("test")
public class ClienteResourceTest {

	@Autowired
    private TestRestTemplate testRestTemplate;	
    @LocalServerPort
    private int port;
    @Autowired
    private ClienteRepository clirepo;
    @Autowired
	private ClienteInfoRepository clinforepo;
    private Cliente cliente;
    private ClienteInfo clinfo;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Date dtCriacao;
    
    @Before
    public void start() throws ParseException {
    	
		cliente = new Cliente(null, "James Brown", (short)73);
		cliente = clirepo.save(cliente);
		dtCriacao = sdf.parse("2019-08-10");
		clinfo = new ClienteInfo();
		clinfo.setId(cliente.getClienteId());
		clinfo.setDataCriacao(dtCriacao);
		clinfo.setIp("127.0.0.0");
		clinfo.setTempAtual(14.3f);
		clinfo.setTempMinima(11.6f);
		clinfo.setTempMaxima(23.5f);
		clinforepo.save(clinfo);
		cliente.setClienteInfo(clinfo);
    }
    
    @After
	public void end() {
		clirepo.deleteAll();
	}
    
    @Test
	public void deveMostrarTodosClientes() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/cliente/",HttpMethod.GET, null, String.class);
		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
    
    @Test
    public void deveMostrarUmCliente() {
    	ResponseEntity<Cliente> resposta = 
    			testRestTemplate.exchange("/api/v1/cliente/{id}", HttpMethod.GET, null, Cliente.class, cliente.getClienteId() );

    	Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    	Assert.assertTrue(resposta.getHeaders().getContentType().equals(MediaType.parseMediaType("application/json;charset=UTF-8")));
    	Assert.assertEquals(cliente.getClienteId(), resposta.getBody().getClienteId());
    }
    
    @Test
    public void deveMostrarTodosClientesUsandoString() {
    	ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/cliente/",HttpMethod.GET,null, String.class);

    	Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    	Assert.assertTrue(resposta.getHeaders().getContentType().equals(
    			MediaType.parseMediaType("application/json;charset=UTF-8")));
    	String result = "[{\"client_id\":1,\"nome\":\"James Brown\",\"idade\":73,\"clienteInfo\":{\"ip\":\"127.0.0.0\",\"dataCriacao\":\"2019-08-10\",\"tempAtual\":14.3,\"tempMinima\":11.6,\"tempMaxima\":23.5}}]";
    	
    	Assert.assertEquals(result, resposta.getBody());
    }
    
    @Test
    public void buscaClienteDeveRetornarNaoEncontrado() {

    	ResponseEntity<Cliente> resposta = 
    			testRestTemplate.exchange("/api/v1/cliente/{id}",HttpMethod.GET,null, Cliente.class, 9 );

    	Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    	Assert.assertNull(resposta.getBody().getNome());
    }
    
    @Test
    public void insereClienteDeveRetornar201() throws Exception {

        URI uri = new URI(String.format("http://localhost:%d/api/v1/cliente/", port));
        ClienteDTO objDto = new ClienteDTO(null, "James Brown", (short)73);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ClienteDTO> entity = new HttpEntity<ClienteDTO>(objDto, headers);
        ResponseEntity<String> resposta = testRestTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
    }

    @Test
    public void alterarClienteDeveRetornar200() throws Exception{

        URI uri = new URI(String.format("http://localhost:%d/api/v1/cliente/%d", port, cliente.getClienteId()));
        ClienteDTO objDto = new ClienteDTO(null, "Stevie Wonder", (short)69);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ClienteDTO> entity = new HttpEntity<ClienteDTO>(objDto, headers);
        ResponseEntity<String> resposta = testRestTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.OK,resposta.getStatusCode());
       
    }
    
    @Test
    public void alterarClienteDeveRetornar404() throws Exception{

        URI uri = new URI(String.format("http://localhost:%d/api/v1/cliente/%d", port, 999));
        ClienteDTO objDto = new ClienteDTO(null, "Stevie Wonder", (short)69);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ClienteDTO> entity = new HttpEntity<ClienteDTO>(objDto, headers);
        ResponseEntity<String> resposta = testRestTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND,resposta.getStatusCode());
    }

    @Test
    public void deletarClienteDeveRetornar200() throws Exception {

        URI uri = new URI(String.format("http://localhost:%d/api/v1/cliente/%d", port, cliente.getClienteId()));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> resposta = testRestTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.OK,resposta.getStatusCode());
    }

    @Test
    public void deletarClienteDeveRetornar404() throws Exception {

        URI uri = new URI(String.format("http://localhost:%d/api/v1/cliente/%d", port, 999));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> resposta = testRestTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND,resposta.getStatusCode());
    }
}
