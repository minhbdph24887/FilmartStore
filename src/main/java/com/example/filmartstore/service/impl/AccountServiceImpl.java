package com.example.filmartstore.service.impl;

import com.example.filmartstore.entity.Account;
import com.example.filmartstore.repository.AccountRepository;
import com.example.filmartstore.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveAccount(Account account) {
        account.setEncryptionPassword(passwordEncoder.encode(account.getEncryptionPassword()));
        account.setStatus(1);
        accountRepository.save(account);
    }
}
