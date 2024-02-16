package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    
    @Query(value = "SELECT * FROM Account WHERE username = :username AND password = :password", nativeQuery = true)
    Account findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM Account WHERE username = :username", nativeQuery = true)
    Account findAccountByUsername(@Param("username") String username);

}
