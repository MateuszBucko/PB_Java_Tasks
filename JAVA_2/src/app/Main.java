package app;

import bank.Bank;
import bank.BankImpl;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        log.debug("Uruchomiono program !");
        displayMenu();
        Scanner sc = new Scanner(System.in);
        int choose=Integer.MAX_VALUE;
        Bank b = new BankImpl();


        while(choose!= 0){
            try {
                System.out.print("Podaj liczbę: ");
                choose = sc.nextInt();
                sc.nextLine();
            }catch(Exception e){
                System.out.println("Proszę podać liczbę");
                sc.nextLine();

            }
            switch (choose){
                case 1:
                    System.out.print("Podaj nazwę konta: ");
                    String accountName = sc.nextLine();
                    System.out.print("Podaj adres: ");
                    String accountAddress = sc.nextLine();
                    if(accountName.isEmpty() || accountAddress.isEmpty()){
                        log.info("Nazwa konta i adres muszą być wypełnione");
                    }else{
                        b.createAccount(accountName,accountAddress);
                    }
                    break;
                case 2:
                    System.out.print("Podaj nazwę konta: ");
                    String accountNameFind = sc.nextLine();
                    System.out.print("Podaj adres: ");
                    String accountAddressFind = sc.nextLine();
                    if(accountNameFind.isEmpty() || accountAddressFind.isEmpty()){
                        log.info("Nazwa konta i adres muszą być wypełnione");
                    }else{
                        b.findAccount(accountNameFind,accountAddressFind);
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Podaj id konta: ");
                        int id = sc.nextInt();
                        System.out.print("Podaj kwotę wpłaty : ");
                        double ammount = sc.nextDouble();

                        b.deposit(id,ammount);

                    }catch(Exception e){
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
            }
                    break;
                case 4:
                    try {
                        System.out.print("Podaj id konta: ");
                        int idAccountBalace = sc.nextInt();

                        b.getBalance(idAccountBalace);

                    }catch(Exception e){
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 5:
                    try {
                        System.out.print("Podaj id konta: ");
                        int id = sc.nextInt();
                        System.out.print("Podaj kwotę wypłaty : ");
                        double ammount = sc.nextDouble();

                        b.withdraw(id,ammount);

                    }catch(Exception e){
                        System.out.println("Proszę podać liczbę");
                        sc.nextLine();
                        break;
                    }
                    break;
                case 6:
                    try {
                    System.out.print("Podaj id konta źródła: ");
                    int idSource = sc.nextInt();
                    System.out.print("Podaj id konta przeznaczenia: ");
                    int idDest = sc.nextInt();
                    System.out.print("Podaj kwotę przelewu: ");
                    double ammount = sc.nextDouble();
                    b.transfer(idSource,idDest,ammount);
                    }catch(Exception e){
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


    public static void displayMenu(){
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
