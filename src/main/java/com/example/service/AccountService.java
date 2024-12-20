package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    public Account registerAccount(com.example.entity.Account account) {

        String username = account.getUsername();
        String password = account.getPassword();
        // Check if username is blank
        if (username == null || username.length()==0) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        // Check if password length is at least 4 characters
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        // Check if username already exists
        if (accountRepository.findByUsername(username) != null) {
            throw new IllegalStateException("Username already exists");
        }

        // Save the account to the database
        return accountRepository.save(account);
    }

    public Account login(Account login_account) {
        String username = login_account.getUsername();
        String password = login_account.getPassword();
        // Check if username is blank
        if (username == null || username.length()==0) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        // Check if password is blank
        if (password == null || password.length()==0) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        //If no account found
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if (account == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return account;
    }
}
