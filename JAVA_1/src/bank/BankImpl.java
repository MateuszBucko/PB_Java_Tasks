package bank;

import bank.Bank;
import model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 24.02.17.
 */
public class BankImpl implements Bank {

    List<Account> accountList = new ArrayList<>();
    Integer lastAccountId = 0;

    @Override
    public Integer createAccount(String name, String address) {
        if(findAccount(name,address) == null) {
            Account newAccount = new Account();
            newAccount.setId(++lastAccountId);
            newAccount.setName(name);
            newAccount.setAddress(address);
            accountList.add(newAccount);
            return lastAccountId;
        }else{
            return findAccount(name,address);
        }
    }

    @Override
    public Integer findAccount(String name, String address) {
        for(Account account : accountList){
            if(account.getName().equals(name) && account.getAddress().equals(address)){
                return account.getId();
            }
        }
        return null;
    }

    @Override
    public void deposit(Integer id, double amount) {
        Account actualAccount = getAccountById(id);
        if(actualAccount != null)
        {
            actualAccount.setAmount(actualAccount.getAmount()+amount);
        }
        else throw new AccountIdException();
    }

    @Override
    public double getBalance(Integer id) {
        Account aa = getAccountById(id);
        if(aa != null){
            return aa.getAmount();
        }
        else throw new AccountIdException();
    }

    @Override
    public void withdraw(Integer id, double amount) {
        Account aa = getAccountById(id);
        if(aa != null){
            if(aa.getAmount() > amount){
                aa.setAmount(aa.getAmount()-amount);
            }else throw new InsufficientFundsException();
        }
        else throw new AccountIdException();
    }

    @Override
    public void transfer(Integer idSource, Integer idDestination, double amount) {
        Account accountFrom = getAccountById(idSource);
        Account accountTo = getAccountById(idDestination);
        if(accountFrom != null && accountTo != null){
            if(accountFrom.getAmount() > amount){
                accountFrom.setAmount(accountFrom.getAmount()-amount);
                accountTo.setAmount(accountTo.getAmount()+amount);
            }else throw new InsufficientFundsException();

        }else throw new AccountIdException();
    }

    private Account getAccountById(Integer id){
        for(Account a : accountList){
            if(a.getId() == id) return a;
        }
        return null;
    }
}
