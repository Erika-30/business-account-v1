package com.bcp.services.account.business;

import com.bcp.services.account.model.Account;
import com.bcp.services.account.model.AccountRequest;
import com.bcp.services.account.model.AccountResponse;
import com.bcp.services.account.model.DepositRequest;
import com.bcp.services.account.model.WithdrawRequest;
import com.bcp.services.account.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public List<AccountResponse> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountResponse()
                .id(account.getId().toString())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(AccountResponse.AccountTypeEnum.fromValue(account.getAccountType()))
                .customerId(account.getCustomerId().toString())).toList();
    }

    public Optional<AccountResponse> findById(Long id) {
        return accountRepository.findById(id)
                .map(account -> new AccountResponse()
                .id(account.getId().toString())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(AccountResponse.AccountTypeEnum.fromValue(account.getAccountType()))
                .customerId(account.getCustomerId().toString()));
    }

    public Account save(AccountRequest account) {

        return accountRepository.save(Account.builder()
                .customerId(Integer.valueOf(account.getCustomerId()))
                .accountType(account.getAccountType().name())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance()).build());
    }

    @Override
    public Account deposit(String accountId, DepositRequest account) {
        Optional<Account> existingAccountOpt = accountRepository.findById(Long.valueOf(accountId));
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setBalance(account.getAmount());
            return accountRepository.save(existingAccount);
        } else {
            throw new EntityNotFoundException("Account not found with id " + accountId);
        }
    }


    @Override
    public Account withdraw(String accountId, WithdrawRequest account) {
        Optional<Account> existingAccountOpt = accountRepository.findById(Long.valueOf(accountId));
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setBalance(account.getAmount());
            return accountRepository.save(existingAccount);
        } else {
            throw new EntityNotFoundException("Account not found with id " + accountId);
        }
    }

    public boolean deleteById(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
