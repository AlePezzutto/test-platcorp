package testplatcorp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testplatcorp.data.domains.Cliente;
import testplatcorp.data.domains.dto.ClienteDTO;
import testplatcorp.repositories.ClienteRepository;
import testplatcorp.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private ClienteInfoService cliInfoService;
	
	public Cliente findById(Integer id) {
		
		Optional<Cliente> obj = clienteRepo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));	
	}
	
	@Transactional
	public Cliente insert(Cliente obj, String ip) {
		
		obj.setClienteId(null);
		Cliente objEdit = clienteRepo.save(obj);
		
		System.out.println("IP Origem: " + ip);
		
		objEdit.setClienteInfo(cliInfoService.insert(objEdit.getClienteId(), ip));
				
		return objEdit;
	}
	
	public Cliente update(Cliente obj) {
		
		Cliente objEdit = findById(obj.getClienteId());
		
		// Atualiza campos
		objEdit.setNome(obj.getNome());
		objEdit.setIdade(obj.getIdade());
		
		return clienteRepo.save(objEdit);
	}
	
	public void delete(Cliente obj) {
		
		Cliente objEdit = findById(obj.getClienteId());
		
		clienteRepo.delete(objEdit);
		
	}
	
	public List<Cliente> findAll(){
		 return clienteRepo.findAll();
	}
	
	public Page<Cliente> findAll(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO o) {
		return new Cliente( o.getClienteId(), o.getNome(), o.getIdade());
	}
}
