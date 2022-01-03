package com.balabasciuc.examplehateos.example_hateos.Service;

import com.balabasciuc.examplehateos.example_hateos.Model.Account;
import com.balabasciuc.examplehateos.example_hateos.Model.Transfer;
import com.balabasciuc.examplehateos.example_hateos.Repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// am scris din greseala extends nu impl dar vad ca tot m-a pus sa impl metodele chiar si asa
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<?> findAccount(Long accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if(account != null)
        {
            return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> depositToAccount(Account account) {
        Account depo = accountRepository.findAccountByAccountNumber(account.getAccountNumber());
        if(depo != null)
        {
            Long currentAmount = depo.getAmount();
            Long newAmmount = currentAmount + account.getAmount();
            depo.setAmount(newAmmount);
            accountRepository.save(depo);
            return new ResponseEntity<>(depo, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Can't deposit to account", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> withdrawFromAccount(Account account) {
        Account accountToWidraw = accountRepository.findAccountByAccountNumber(account.getAccountNumber());
        if(checkAmount(account.getAmount()))
        {
            Long currentAmount = accountToWidraw.getAmount();
            Long newAmount = currentAmount - account.getAmount();
            accountToWidraw.setAmount(newAmount);
            accountRepository.save(accountToWidraw);
            return new ResponseEntity<>(accountToWidraw.getAmount(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> createAccount(Account account) {
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> transferToAccount(Transfer transfer) {
        Account fromAccount = accountRepository.findAccountByAccountNumber(transfer.getFromAccountNumber());
        if(!accountRepository.existsById(transfer.getToAccountNumber()))
        {
            System.out.println("Invalid destination Account to transfer money.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if(!accountRepository.existsById(transfer.getFromAccountNumber()))
        {
            System.out.println("Invalid source Account.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if(fromAccount.getAmount() < transfer.getAmount())
        {
            System.out.println("Don't enough to transfer money...");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account toAccount = accountRepository.findAccountByAccountNumber(transfer.getToAccountNumber());
        fromAccount.setAmount(fromAccount.getAmount() - transfer.getAmount());
        toAccount.setAmount(toAccount.getAmount() + transfer.getAmount());
        System.out.println("fromAcount amount is now " + fromAccount.getAmount());
        System.out.println("toAccount amount is now " + toAccount.getAmount());
 //       accountRepository.save(fromAccount);
//        accountRepository.save(toAccount);
        return new ResponseEntity<>(HttpStatus.OK);

    }

      public  List<String> allowedActions(Account account)
    {
        List<String> actions = new ArrayList<>();
        if(account.getAmount() <= 0)
        {
            actions.add("DEPOSIT");
        }
        else
        {
            actions.add("WITHDRAW");
            actions.add("DEPOSIT");
            actions.add("TRANSFER");
        }
        return actions;
    }

    private boolean checkAmount(Long amount)
    {
        return amount > 0;
    }


}
