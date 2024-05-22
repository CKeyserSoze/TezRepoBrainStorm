package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

}
