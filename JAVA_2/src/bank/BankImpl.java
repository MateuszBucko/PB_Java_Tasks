package bank;

import model.Account;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 24.02.17.
 */
public class BankImpl implements Bank {

    List<Account> accountList = new ArrayList<>();
    Integer lastAccountId = 0;
    private static final Logger log = Logger.getLogger(BankImpl.class);

    @Override
    public Integer createAccount(String name, String address) {
        if (findAccountWithoutException(name, address) == null) {

            Account newAccount = new Account();
            newAccount.setId(++lastAccountId);
            newAccount.setName(name);
            newAccount.setAddress(address);
            accountList.add(newAccount);
            log.info("Tworzenie nowego konta (nazwa: " + name + " adres: " + address + ") z id: "+ lastAccountId);
            return lastAccountId;
        } else {
            log.info("Próba utworzenia konta. Konto (nazwa: " + name + " ares: " + address + " )" + "juz istnieje.");
            return findAccount(name, address);
        }
    }

    @Override
    public Integer findAccount(String name, String address) {
        try {
            for (Account account : accountList) {
                if (account.getName().equals(name) && account.getAddress().equals(address)) {
                    log.debug("Szukano konto o nazwie: " + name + " oraz adresie: " + address + ". Szukane konto ma id: " + account.getId());
                    return account.getId();
                }
            }
            throw new AccountIdException();
        }catch(AccountIdException aie){
            log.error("Konto (nazwa : " + name + " adres: " + address + " )" + "nie zostało znalezione");
        }
        return null;
    }

    public Integer findAccountWithoutException(String name, String address) {
        for (Account account : accountList) {
            if (account.getName().equals(name) && account.getAddress().equals(address)) {
                return account.getId();
            }
        }
        return null;
    }

    @Override
    public void deposit(Integer id, double amount) {
        Account actualAccount = getAccountById(id);
        try {
            if (actualAccount != null) {
                actualAccount.setAmount(actualAccount.getAmount() + amount);
                log.debug("Na konto o id: "+ id + " wpłacono kwotę "+ amount);
            } else throw new AccountIdException();
        } catch (AccountIdException aie) {
            log.error("Konto o id : " + id + " nie istnieje. Nie można złożyć depozytu.");
        }
    }

    @Override
    public double getBalance(Integer id) {
        Account aa = getAccountById(id);
        try {
            if (aa != null) {
                log.debug("Zwrócono stan konta o id: " + id + " który wynosi " + aa.getAmount());
                return aa.getAmount();

            } else throw new AccountIdException();
        } catch (AccountIdException aie) {

            log.error("Konto o id: " + id + " nie istnieje ! Nie można pobrać stanu.");
        }
        throw new AccountIdException();
    }

    @Override
    public void withdraw(Integer id, double amount) {
        Account aa = getAccountById(id);
        try {
            if (aa != null) {
                if (aa.getAmount() > amount) {
                    aa.setAmount(aa.getAmount() - amount);
                    log.info("Poprawnie wypłacono kwotę: " + amount + "z konta o id: " + id);
                } else throw new InsufficientFundsException();
            } else throw new AccountIdException();
        } catch (InsufficientFundsException ife) {
            log.error("Konto o id: " + id + " nie ma wystarczajacych środków aby wypłacić kwotę: " + amount);
        } catch (AccountIdException aie) {
            log.error("Konto o id: " + id + " nie istnieje ! Nie można wypłacić.");
        }
    }

    @Override
    public void transfer(Integer idSource, Integer idDestination, double amount) {
        Account accountFrom = getAccountById(idSource);
        Account accountTo = getAccountById(idDestination);
        try {
            if (accountFrom != null && accountTo != null) {
                if (accountFrom.getAmount() > amount) {
                    accountFrom.setAmount(accountFrom.getAmount() - amount);
                    accountTo.setAmount(accountTo.getAmount() + amount);
                    log.info("Kwota: " + amount + " została przelana z konta o id: " + idSource + " na konto o id: " + idDestination);
                } else throw new InsufficientFundsException();
            } else throw new AccountIdException();
        } catch (InsufficientFundsException ife) {
            log.error("Brak wystarczających środków na koncie o id: " + idSource + " by przelać kwotę: " + amount);
        } catch (AccountIdException aie) {
            if (accountFrom == null) log.error("Konto o id: " + idSource + " nie istnieje");
            if (accountTo == null) log.error("Konto o id: " + idDestination + " nie istnieje");
        }
    }

    private Account getAccountById(Integer id) {
        for (Account a : accountList) {
            if (a.getId() == id) return a;
        }
        return null;
    }
}
