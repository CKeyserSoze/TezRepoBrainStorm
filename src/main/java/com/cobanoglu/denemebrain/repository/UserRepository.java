package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    User findByEmail(String username);
    boolean existsByEmail(String email);

    User save(User user);


    //Bu alana gerekli CRUD işlemlerinin imzalarını yazıp asıl görevi service katmanında yapacağım..
    // Örneğin findByUserID
}
