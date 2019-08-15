package testplatcorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import testplatcorp.data.domains.User;

@Repository
public interface PermissionRepository extends JpaRepository<User, Long> {

}
