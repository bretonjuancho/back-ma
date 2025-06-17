package com.example.tp.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.tp.modelo.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    //List<Token> findAllValidTokenByUser(Integer id);



    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(int id);

    Optional<Token> findByToken(String token);

}
