package com.balabasciuc.examplehateos.example_hateos.Repository;

import com.balabasciuc.examplehateos.example_hateos.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;



public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findAccountByAccountNumber(Long accountNumber);

}
