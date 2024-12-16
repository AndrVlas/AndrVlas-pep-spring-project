package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if(account.getUsername().isBlank()) {
            return null;
        }

        if(account.getPassword().length() < 4) {
            return null;
        }

        if(accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return new Account(-1, "", "");
        }

        return accountRepository.save(account);
    }

    public Optional<Account> loginAccount(Account account) {

        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    
}
