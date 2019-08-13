package testplatcorp.resources;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import testplatcorp.data.domains.Cliente;
import testplatcorp.data.domains.dto.ClienteDTO;
import testplatcorp.services.ClienteService;

@RestController
@RequestMapping(value="/api/v1/cliente")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value="/")
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value="/")
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDto,  HttpServletRequest request) {
		
		// Obtem o IP da requisição
		String ip = request.getRemoteAddr();
		
		// Para testar via eclipse. COMENTAR DEPOIS
		ip = "179.99.148.36";
		Cliente obj = service.fromDTO(objDto);
		obj.setClienteId(null);
		obj = service.insert(obj, ip);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				                             .path("/{id}").buildAndExpand(obj.getClienteId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		
		Cliente cliEdit = service.findById(id);
		
		if(cliEdit != null) {
			
			cliEdit = service.fromDTO(objDto);
			cliEdit.setClienteId(id);
			
			service.update(cliEdit);
			return ResponseEntity.ok().build();
		}
		else
			return ResponseEntity.notFound().build();
		
		//return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		Cliente obj = service.findById(id);
		
		if(obj != null) {
			service.delete(obj);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
		//return ResponseEntity.noContent().build();
	}
	
}
