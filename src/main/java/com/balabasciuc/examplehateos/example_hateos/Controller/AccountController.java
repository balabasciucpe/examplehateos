package com.balabasciuc.examplehateos.example_hateos.Controller;

import com.balabasciuc.examplehateos.example_hateos.Model.Account;
import com.balabasciuc.examplehateos.example_hateos.Model.Transfer;
import com.balabasciuc.examplehateos.example_hateos.Service.AccountService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id)
    {
        ResponseEntity<?> responseEntity = accountService.findAccount(id);
        Account account = (Account) responseEntity.getBody();
        EntityModel<Account> resource = EntityModel.of(account);
        List<String> allowedActions = accountService.allowedActions(account);
        allowedActions.forEach(action ->
        {
            if(action.equalsIgnoreCase("DEPOSIT"))
            {
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deposit(new Account())).withRel("deposit"));
            }
            else if(action.equalsIgnoreCase("Withdraw"))
            {
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).withdraw(new Account())).withRel("withdraw"));
            }
            else if(action.equalsIgnoreCase("transfer"))
            {
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).transfer(new Transfer())).withRel("transfer"));
            }
        });
        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/deposit")
    public ResponseEntity<?> deposit(@RequestBody Account account)
    {
        return accountService.depositToAccount(account);
    }

    @PutMapping(value = "/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Account account)
    {
        return accountService.withdrawFromAccount(account);
    }
    
    @PostMapping(value = "/transfer")
    public ResponseEntity<?> transfer(@RequestBody Transfer transfer)
    {
        return accountService.transferToAccount(transfer);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account)
    {
        return accountService.createAccount(account);
    }
}
