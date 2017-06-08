package app;

import bank.Bank;
import bank.BankImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.debug("Uruchomiono program !");
        displayMenu();
        Scanner sc = new Scanner(System.in);
        int choose = Integer.MAX_VALUE;
        int id = 0;
        int idSource=0;
        int idDest=0;
        double ammount = 0;
        Bank b = new BankImpl();


        while (choose != 0) {
            try {
                System.out.print("Podaj liczbę: ");
                choose = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Proszę podać liczbę");
                sc.nextLine();

            }
            switch (choose) {
                case 1:
                    System.out.print("Podaj nazwę konta: ");
                    String accountName = sc.nextLine();
                    System.out.print("Podaj adres: ");
                    String accountAddress = sc.nextLine();
                    if (accountName.isEmpty() || accountAddress.isEmpty()) {
                        log.info("Nazwa konta i adres muszą być wypełnione");
                    } else {
                        b.createAccount(accountName, accountAddress);
                    }
                    break;
                case 2:
                    System.out.print("Podaj nazwę konta: ");
                    String accountNameFind = sc.nextLine();
                    System.out.print("Podaj adres: ");
                    String accountAddressFind = sc.nextLine();
                    if (accountNameFind.isEmpty() || accountAddressFind.isEmpty()) {
                        log.info("Nazwa konta i adres muszą być wypełnione");
                    } else {
                        Integer accountId = b.findAccount(accountNameFind, accountAddressFind);
                        if (accountId != null) {

                        }

                    }
                    break;
                case 3:
                    try {
                        System.out.print("Podaj id konta: ");
                        id = sc.nextInt();
                        System.out.print("Podaj kwotę wpłaty : ");
                        ammount = sc.nextDouble();

                        b.deposit(id, ammount);

                    } catch (Bank.AccountIdException aie) {
                        log.error("Konto o id : " + id + " nie istnieje. Nie można złożyć depozytu.");
                    } catch (Exception e) {
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Podaj id konta: ");
                        id = sc.nextInt();
                        b.getBalance(id);

                    } catch (Bank.AccountIdException aie) {
                        log.error("Konto o id: " + id + " nie istnieje ! Nie można pobrać stanu.");
                    } catch (Exception e) {
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 5:
                    try {
                        System.out.print("Podaj id konta: ");
                        id = sc.nextInt();
                        System.out.print("Podaj kwotę wypłaty : ");
                        ammount = sc.nextDouble();

                        b.withdraw(id, ammount);

                    } catch (Bank.InsufficientFundsException ife) {
                        log.error("Konto o id: " + id + " nie ma wystarczajacych środków aby wypłacić kwotę: " + ammount);
                    } catch (Bank.AccountIdException aie) {
                        log.error("Konto o id: " + id + " nie istnieje ! Nie można wypłacić.");
                    } catch (Exception e) {
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 6:
                    try {
                        System.out.print("Podaj id konta źródła: ");
                        idSource = sc.nextInt();
                        System.out.print("Podaj id konta przeznaczenia: ");
                        idDest = sc.nextInt();
                        System.out.print("Podaj kwotę przelewu: ");
                        ammount = sc.nextDouble();
                        b.transfer(idSource, idDest, ammount);
                    } catch (Bank.InsufficientFundsException ife) {
                        log.error("Brak wystarczających środków na koncie o id: " + idSource + " by przelać kwotę: " + ammount);
                    } catch (Bank.AccountIdException aie) {
                        log.error("Konto nie istnieje");
                    } catch (Exception e) {
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 0:
                    log.info("Poprawnie zakończono działanie programu");
                    return;
                default:
                    log.info("Proszę wybrać jedną z opcji menu");
            }
            displayMenu();
        }

    }


    public static void displayMenu() {
        System.out.println("Menu: ");
        System.out.println("1. Utwórz nowe konto");
        System.out.println("2. Znajdź konto");
        System.out.println("3. Wpłać pieniądze");
        System.out.println("4. Pokaż stan konta");
        System.out.println("5. Wypłać pieniądze");
        System.out.println("6. Zrób przelew");
        System.out.println("0. Wyjdź");

    }
}
