package com.bcp.services.account.repository;

import com.bcp.services.account.model.Account;
import com.bcp.services.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}