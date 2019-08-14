package testplatcorp.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import testplatcorp.data.domains.Cliente;
import testplatcorp.data.domains.ClienteInfo;
import testplatcorp.repositories.ClienteInfoRepository;
import testplatcorp.repositories.ClienteRepository;

@RunWith(SpringRunner.class)
//@SpringBootTest 
@DataJpaTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clirepo;
	
	@Autowired
	private ClienteInfoRepository clinforepo;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void insereCliente() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date hoje = sdf.parse(sdf.format(new Date()));
		
		Cliente cliente = new Cliente(null, "Cliente", (short)21);
		cliente = clirepo.save(cliente);
		
		assertThat(cliente.getClienteId()).isGreaterThan(0);
		assertThat(cliente.getNome()).isEqualTo("Cliente");
		assertThat(cliente.getIdade()).isEqualTo((short)21);
		
		ClienteInfo clinfo = new ClienteInfo();
		clinfo.setId(cliente.getClienteId());
		clinfo.setDataCriacao(hoje);
		clinfo.setIp("127.0.0.0");
		clinfo.setTempAtual(14.3f);
		clinfo.setTempMinima(11.6f);
		clinfo.setTempMaxima(23.5f);
		
		assertThat(clinfo.getIp()).isEqualTo("127.0.0.0");
		assertThat(clinfo.getDataCriacao()).isEqualTo(hoje);
		assertThat(clinfo.getTempAtual()).isLessThan(15f);
		assertThat(clinfo.getTempMinima()).isGreaterThan(10f);
		assertThat(clinfo.getTempMaxima()).isEqualTo(23.5f);
		
	}
	
	@Test
	public void procuraClientePeloId() {
		Cliente cliente = new Cliente(null, "Cliente", (short)21);
		cliente = clirepo.save(cliente);
		Integer id = cliente.getClienteId();
		Optional<Cliente> newcliente = clirepo.findById(id);
		assertThat(newcliente.get().getNome()).isEqualTo("Cliente");
		assertThat(newcliente.get().getIdade()).isEqualTo((short)21);
	}
	
	@Test
	public void alteraCliente() {
		Cliente cliente = new Cliente(null, "Paul McCartney", Short.parseShort("77"));
        this.clirepo.save(cliente);
        cliente.setNome("Ringo Starr");
        cliente.setIdade(Short.parseShort("79"));
        this.clirepo.save(cliente);
        
        Optional<Cliente> optCliente = this.clirepo.findById(cliente.getClienteId());
        cliente = optCliente.orElse(new Cliente());
        assertThat(cliente.getNome()).isEqualTo("Ringo Starr");
        assertThat(cliente.getIdade()).isEqualTo(Short.parseShort("79"));
		
	}
	
	@Test
    public void deletarCliente() {
        Cliente cliente = new Cliente(null, "Paul McCartney", Short.parseShort("77"));
        this.clirepo.save(cliente);
        this.clirepo.delete(cliente);
        assertThat(this.clirepo.findById(cliente.getClienteId())).isNull();
        assertThat(this.clinforepo.findById(cliente.getClienteId())).isNull();
    }

	@Test
	public void insereCampoNomeNuloDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    thrown.expectMessage("Preenchimento obrigatório");
	    clirepo.save(new Cliente());
	}
	
	@Test
	public void insereCampoNomeTamanhoMenorDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    Cliente cliente = new Cliente();
	    cliente.setNome("Nome");
	    clirepo.save(cliente);
	}

	@Test
	public void insereCampoNomeTamanhoMaiorDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    Cliente cliente = new Cliente();
	    cliente.setNome("Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
	    clirepo.save(cliente);
	}
	
	@Test
	public void insereCampoIdadeDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    thrown.expectMessage("Preenchimento obrigatório");
	    Cliente cliente = new Cliente();
	    cliente.setNome("Nome");
	    clirepo.save(new Cliente());
	}
	
	@Test
	public void insereCampoIdadeMenor18AnosDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    thrown.expectMessage("Preenchimento obrigatório");
	    Cliente cliente = new Cliente();
	    cliente.setNome("Nome");
	    cliente.setIdade((short)17);
	    clirepo.save(new Cliente());
	}
	
	@Test
	public void insereCampoIdadeMaior99AnosDeveGerarException(){

	    thrown.expect(ConstraintViolationException.class);
	    thrown.expectMessage("Preenchimento obrigatório");
	    Cliente cliente = new Cliente();
	    cliente.setNome("Nome");
	    cliente.setIdade((short)100);
	    clirepo.save(new Cliente());
	}

	
}
