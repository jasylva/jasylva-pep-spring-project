package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){ this.accountRepository = accountRepository; }

    public Account persistAccount(Account account){   
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4) return null;

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) return null; 

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){  return accountRepository.findAll(); }

    public Account getAccount(int id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()) return optionalAccount.get(); else return null;
    }

    public Account getAccount(String user, String pass){
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(user, pass);
        if(optionalAccount.isPresent()) return optionalAccount.get(); else return null;
    }

}
