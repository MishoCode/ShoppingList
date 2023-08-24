package com.example.shoppinglist.repository;

import com.example.shoppinglist.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationToken c " +
           "SET c.confirmedAt = ?2 " +
           "WHERE c.token = ?1")
    void updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
