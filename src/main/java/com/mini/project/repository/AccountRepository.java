package com.mini.project.repository;

import com.mini.project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

//    Long countUsername(String username);
}
