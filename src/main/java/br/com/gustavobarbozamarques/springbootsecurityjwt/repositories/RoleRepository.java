package br.com.gustavobarbozamarques.springbootsecurityjwt.repositories;

import br.com.gustavobarbozamarques.springbootsecurityjwt.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
