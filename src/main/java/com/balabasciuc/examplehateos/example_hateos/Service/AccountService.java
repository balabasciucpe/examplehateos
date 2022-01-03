package com.balabasciuc.examplehateos.example_hateos.Service;

import com.balabasciuc.examplehateos.example_hateos.Model.Account;
import com.balabasciuc.examplehateos.example_hateos.Model.Transfer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {

    ResponseEntity<?> findAccount(Long accountNumber);
    ResponseEntity<?> depositToAccount(Account account);
    ResponseEntity<?> withdrawFromAccount(Account account);
    ResponseEntity<?> createAccount(Account account);
    ResponseEntity<?> transferToAccount(Transfer Transfer);
    public List<String> allowedActions(Account account);
}
