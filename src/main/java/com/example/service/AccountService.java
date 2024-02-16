package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(int id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            return null;
        }
    }

    public Account getAccountByUsernameAndPassword(String username, String password){
        Account account = accountRepository.findAccountByUsernameAndPassword(username, password);
        return (account != null) ? account : null;
    }

    public Account updateAccount(int id, Account replacement){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setUsername(replacement.getUsername());
            return accountRepository.save(account);
        }
        else{
            return null;
        }
    }

    public void deleteAccount(int id){
        accountRepository.deleteById(id);
    }

    public boolean usernamePresent(String username){
        try {
            Account account = accountRepository.findAccountByUsername(username);
            return account != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

}
