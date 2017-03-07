package app;

import bank.Bank;
import bank.BankImpl;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
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
                log.error("Proszę wybrać liczbę !");
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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
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
