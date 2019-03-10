/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "users" table in DB
 * successfully using JpaRepository methods
 */

package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findByEmail(String email);

  default UserEntity findByUsername(String username) {
    return findByEmail(username);
  }
}
