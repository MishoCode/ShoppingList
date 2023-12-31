package com.example.shoppinglist.repository;

import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationToken v " +
           "SET v.confirmedAt = ?2 " +
           "WHERE v.token = ?1")
    void updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
