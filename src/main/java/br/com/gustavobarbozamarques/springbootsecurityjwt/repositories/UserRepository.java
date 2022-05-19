package br.com.gustavobarbozamarques.springbootsecurityjwt.repositories;

import br.com.gustavobarbozamarques.springbootsecurityjwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
