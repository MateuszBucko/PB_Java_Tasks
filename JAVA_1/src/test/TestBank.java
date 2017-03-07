package test;

import bank.Bank;
import bank.BankImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mateusz on 24.02.17.
 */
public class TestBank {

    private Bank b;

    /**
     * Inicjalizacja - tworzenie instancji banku
     */
    @Before
    public void init() {
        this.b = new BankImpl();
    }

    /**
     * Test tworzenia konta, czy zwrócony zostanie Integer
     */
    @Test
    public void testCreateAccount() {
        Integer accountNumber = b.createAccount("nowe", "adres1");
        Assert.assertNotNull(accountNumber);
    }

    /**
     * Test utworzenia 2 takich samych kont, powinno zostać zwrócione identyczne id
     */
    @Test
    public void testCreateExistingAccount() {
        Integer id_1 = b.createAccount("nowe", "adres1");
        Integer id_2 = b.createAccount("nowe", "adres1");
        Assert.assertEquals(id_1, id_2);
    }

    /**
     * Test znajdowania konta (świeżo utowrzonego)
     */
    @Test
    public void testFindAccountNotNull() {
        b.createAccount("c1", "c2");
        Assert.assertNotNull(b.findAccount("c1", "c2"));
    }

    /**
     * Test znajdowania konta nieistniejącego (czy zwróci null)
     */
    @Test
    public void testFindAccountNull() {
        Assert.assertNull(b.findAccount("test", "test"));
    }

    /**
     * Test złożenia depozytu dla nieistniejącego konta
     */
    @Test(expected = Bank.AccountIdException.class)
    public void depositNotExistingAccountTest() {
        Integer accountNumber = b.createAccount("t1", "t2");
        b.deposit(accountNumber + 1, 100.0);
    }

    /**
     * Test sprawdzenia stanu konta + depozyt
     */
    @Test
    public void getBalanceTest() {
        Integer accountNumber = b.createAccount("t1", "t2");
        Assert.assertNotNull(accountNumber);
        b.deposit(accountNumber, 100.0);
        Assert.assertEquals(100.0, b.getBalance(accountNumber), 0);
    }

    /**
     * Test sprawdzenia stanu konta nie istniejącego
     */
    @Test(expected = Bank.AccountIdException.class)
    public void getBalaceAccountNotExistTest(){
        b.getBalance(1000);
    }

    /**
     * Test wypłaty części środków + sprawdzenie stanu konta
     */
    @Test
    public void withdrawTest() {
        Integer accountNumber = b.createAccount("nowe1", "nowe2");
        b.deposit(accountNumber, 1000.0);
        b.withdraw(accountNumber, 500.0);
        Assert.assertEquals(500.0, b.getBalance(accountNumber), 0);
    }

    /**
     * Test wypłaty większej ilości środków niż konto posiada
     */
    @Test(expected = Bank.InsufficientFundsException.class)
    public void withdrawFailTest() {
        Integer accountNumber = b.createAccount("wtest", "wtest");
        b.deposit(accountNumber, 100.0);
        b.withdraw(accountNumber, 500.0);
    }

    /**
     * Test przelewu między rachunkami + sprawdzenie stanu konta
     */
    @Test
    public void transferTest() {
        Integer id_1 = b.createAccount("a1", "a1");
        Integer id_2 = b.createAccount("a2", "a2");

        b.deposit(id_1, 1000);
        Assert.assertEquals(1000.0, b.getBalance(id_1), 0);
        Assert.assertEquals(0.0, b.getBalance(id_2), 0);

        b.transfer(id_1, id_2, 500.0);

        Assert.assertEquals(500.0, b.getBalance(id_1), 0);
        Assert.assertEquals(500.0, b.getBalance(id_2), 0);

    }

    /**
     * Test przelewu na nieistniejący rachunek
     */
    @Test(expected = Bank.AccountIdException.class)
    public void transferTestForNotExistingAccount() {
        Integer id_1 = b.createAccount("ac1","ac1");

        b.deposit(id_1,1000);

        b.transfer(id_1,22,1000);

    }

    /**
     * Test przelewu kwoty większej niż jest dostepna na rachunku
     */
    @Test(expected = Bank.InsufficientFundsException.class)
    public void transferTooMuchAmountTest(){
        Integer id_1 = b.createAccount("a1", "a1");
        Integer id_2 = b.createAccount("a2", "a2");

        b.deposit(id_1, 1000);
        b.transfer(id_1,id_2,5000.0);
    }


}
