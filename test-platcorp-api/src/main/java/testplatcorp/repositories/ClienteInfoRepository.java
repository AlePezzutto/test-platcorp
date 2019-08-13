package testplatcorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import testplatcorp.data.domains.ClienteInfo;

@Repository
public interface ClienteInfoRepository  extends JpaRepository<ClienteInfo, Integer> 
{
	
}
