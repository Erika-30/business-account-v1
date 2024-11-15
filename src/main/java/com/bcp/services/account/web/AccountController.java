package com.bcp.services.account.web;

import com.bcp.services.account.api.AccountControllerApiDelegate;
import com.bcp.services.account.business.AccountService;
import com.bcp.services.account.model.AccountRequest;
import com.bcp.services.account.model.AccountResponse;
import com.bcp.services.account.model.CreateAccountResponse;
import com.bcp.services.account.model.DepositRequest;
import com.bcp.services.account.model.WithdrawRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Product Controller.
 * This class is used to handle the product requests.
 */
@RequiredArgsConstructor
@Component
public class AccountController implements AccountControllerApiDelegate {

  private final AccountService accountService;

  @Override
  public ResponseEntity<Void> accountsAccountIdDepositPut(String requestId,
                                                           String accountId,
                                                          DepositRequest request) {
    try {
      accountService.deposit(accountId, request);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

  }
  
  @Override
  public ResponseEntity<Void> accountsAccountIdWithdrawPut(String requestId,
                                                            String accountId,
                                                           WithdrawRequest request) {
    try {
      accountService.withdraw(accountId, request);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<List<AccountResponse>> accountsGet(String requestId) {

    return Optional.of(ResponseEntity.ok(accountService.findAll()))
        .orElse(ResponseEntity.notFound().build());

  }

  @Override
  public ResponseEntity<Void> accountsIdDelete(String requestId,
                                                String id) {
    return accountService.deleteById(Long.valueOf(id))
        ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();

  }

  @Override
  public ResponseEntity<AccountResponse> accountsIdGet(String requestId,
                                                        String id) {

    return accountService.findById(Long.valueOf(id))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

  }

  @Override
  public ResponseEntity<CreateAccountResponse> accountsPost(String requestId,
                                                             AccountRequest request) {

    return Optional.of(ResponseEntity.ok(new CreateAccountResponse()
                    .id(accountService.save(request).getId().toString())))
        .orElse(ResponseEntity.notFound().build());

  }

}