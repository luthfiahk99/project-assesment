package com.mini.project.service;

import com.mini.project.ApplicationUserDetails;
import com.mini.project.entity.Account;
import com.mini.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String getAccountRole(String username) {
        Optional<Account> nullableEntity = accountRepository.findById(username);

        Account account = null;
        if (nullableEntity.isPresent()){
            account = nullableEntity.get();
        }

        return account.getRole();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> nullableEntity = accountRepository.findById(username);

        Account account = null;
        if (nullableEntity.isPresent()){
            account = nullableEntity.get();
        }
        return new ApplicationUserDetails(account);
    }

//    @Override
//    public Boolean checkExistingUsername(String username) {
//        Long totalUser = accountRepository.countUsername(username);
//        return (totalUser > 0) ? true : false;
//    }
}
