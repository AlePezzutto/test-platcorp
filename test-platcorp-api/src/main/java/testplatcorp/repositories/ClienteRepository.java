package testplatcorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import testplatcorp.data.domains.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer> 
{
	
}
