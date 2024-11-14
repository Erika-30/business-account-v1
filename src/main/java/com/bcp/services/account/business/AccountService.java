package com.bcp.services.account.business;

import com.bcp.services.account.model.Account;
import com.bcp.services.account.model.AccountRequest;
import com.bcp.services.account.model.AccountResponse;
import com.bcp.services.account.model.DepositRequest;
import com.bcp.services.account.model.WithdrawRequest;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountResponse> findAll();

    Optional<AccountResponse> findById(Long id);

    Account save(AccountRequest account);

    Account deposit(String accountId, DepositRequest account);

    Account withdraw(String accountId, WithdrawRequest account);


    public boolean deleteById(Long id);
}
