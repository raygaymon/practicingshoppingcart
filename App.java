package bleh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {

        String filePath = args[0];

        Console cons = System.console();

        String userName = "";
        String pw = "";

        File newDirectory = new File(filePath);
        if (!newDirectory.exists()) {

            newDirectory.mkdir();
            System.out.println("Path created. Initializing Shopee");
        } else {

            System.out.println("Path existing, initialising Shopee");
        }

        List<String> users = new ArrayList<String>();

        String login = cons.readLine("Welcome to Shopee. Please enter your username: \n");

        // while (!login.startsWith("login")) {

        // login = cons.readLine("Please login before continuing in this manner: \nlogin
        // <username>\n");
        // }

        // to add: password verification
        // if (login.startsWith("login")) {

        // userName = login.substring(6);

        userName = login;

        // dk why but this only works when in the loop and not at the top so for test
        // just keep within the loops
        String directory = filePath + File.separator + userName;

        //strings after file separator can be used to control the name of the file create wuhu
        String directory2 = filePath + File.separator + userName + "PW";

        File account = new File(directory);

        File userPW = new File(directory2);

        if (!account.exists()) {
            String createFile = cons.readLine("File does not exist. Do you wish to create a new account?\n");
            if (createFile.equals("yes")) {

                account.createNewFile();
                
                String password = cons.readLine("PLease enter a password: \n");

                pw = password;
                userPW.createNewFile();

                BufferedWriter bw4 = new BufferedWriter(new FileWriter(filePath + File.separator + userName + "PW"));
                bw4.write(password);
                bw4.flush();
                bw4.close();

                System.out.println("Account created for " + userName + ". Enjoy Shopee!");
            } else {

                System.out.println("Understandable. Exiting Shopee");
            }
        } else {

            String security = cons.readLine("Account exists, please enter password: \n");

            BufferedReader br = new BufferedReader(new FileReader(filePath + File.separator + userName + "PW"));

            // seems like .readLine only reads the fisrt line of a file
            String passwordCheck = br.readLine();

            while (!security.equals(passwordCheck)) {

                security = cons.readLine(
                        "Password incorrect. Please enter the correct password: \nPlease type 'forgot'if you don't remember your password. \n");

                if (security.equals("forgot")) {
                    String forgotPW = cons.readLine("Please enter your username: \n");

                    while (!forgotPW.equals(userName)) {

                        forgotPW = cons.readLine("How could you get this wrong you literally just typed it correctly try again \n");
                    }

                    System.out.println("Your password is: " + passwordCheck);
                    security = cons.readLine("Please enter the correct password: \n");
                }

            }

            System.out.println("Login successful. Welcome to Shopee " + userName + "\n\n");
            br.close();
        }

        // }

        List<String> cart = new ArrayList<String>();
        String[] database = newDirectory.list();
        List<String> userBase = new ArrayList<String>();

        String input = "";

        while (!input.equals("checkout")) {

            // put console readline INSIDE THE while loop
            input = cons.readLine("How can we help you today: \n");

            if (input.startsWith("add")) {

                String[] things = input.substring(4).split(", ");

                // create new lists to add the dupes and non-dupes into to print specifically
                // just the new ones and not the entire cart each time
                List<String> output = new ArrayList<String>();
                List<String> dupes = new ArrayList<String>();

                // for FW just add the path + separator + filename again
                FileWriter fw = new FileWriter(filePath + File.separator + userName, true);
                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 0; i < things.length; i++) {

                    if (!cart.contains(things[i])) {

                        cart.add(things[i]);
                        output.add(things[i]);
                        bw.append("\n" + things[i].toString() + "\n");

                    } else {
                        dupes.add(things[i]);
                    }
                }

                bw.flush();
                bw.close();
                fw.close();

                // printing outside the for loop if not it will keep printing multiple times
                if (output.size() != 0) {

                    // .join separates out individual elements in a list by the string for printing
                    System.out.println(output.toString().join(", ", output) + " has been added to cart");
                }

                // both must be if statements otherwise they won't print together when needed
                if (dupes.size() != 0) {

                    System.out.println(
                            "You already have " + dupes.toString().join(", ", dupes) + " in your cart you pig");
                }

            }

            else if (input.startsWith("list")) {

                System.out.println("Your cart contains: \n");

                for (int j = 0; j < cart.size(); j++) {
                    System.out.println(j + 1 + ". " + cart.get(j));
                }
            }

            else if (input.startsWith("delete")) {

                String number = input.substring(7);

                int toDelete = Integer.parseInt(number);

                if (toDelete <= cart.size()) {

                    // have to add the print before the removal bcs after removal there's nothing
                    // from the list to retrieve
                    System.out.println(cart.get(toDelete - 1) + " has been removed");
                    cart.remove(toDelete - 1);

                    // basically use filewriter to rewrite the entire cart
                    // false rewrites the whole thing
                    BufferedWriter bw3 = new BufferedWriter(
                            new FileWriter(filePath + File.separator + userName, false));
                    for (String newCart : cart) {
                        bw3.write(newCart + "\n");
                    }

                    bw3.flush();
                    bw3.close();
                }

                else {
                    System.out.println("your cart dont have so many things bro");
                }

            } else if (input.startsWith("users")) {

                System.out.println("List of users: \n");
                for (int i = 0; i < database.length; i++) {
                    System.out.println(i + 1 + ". " + database[i]);
                }
            }

            // else if (input.startsWith("save")) {

            // BufferedWriter bw2 = new BufferedWriter(new FileWriter(filePath +
            // File.separator + userName));

            // for (String stuff : cart) {

            // bw2.write(stuff);
            // }

            // System.out.println("Items in cart saved.");

            // bw2.flush();
            // bw2.close();
            // }

            else if (!input.equals("checkout")) {

                System.out.println("WTF you trying to do");
            }
        }

        System.out.println("Thank you for shopping at Shopee. Proceeding to payment");
    }

}
