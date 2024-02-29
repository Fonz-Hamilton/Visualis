package org.fonzhamilton.visualis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.fonzhamilton.visualis.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

    public User findUserByUserName(String name);

    public User findUserById(long id);


    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
