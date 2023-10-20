package com.example.service;
import java.util.List;

import javax.transaction.Transactional;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account newAccount) {
       return accountRepository.save(newAccount);
    }

    public Account findByUserName(String user) {
        return accountRepository.findByUsername(user);
    }


}
