
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            //While loop to split id, name, and price to make object
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("\\|");
                String id = part[0];
                String name = part[1];
                double price = Double.parseDouble(part[2]);
                //add info to make new object
                Product p = new Product(id, name, price);
                //add new objects from .csv to add to inventory
                inventory.add(p);
            }
            reader.close();
        } catch (Exception e) {
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
        for (Product p : inventory) {
            System.out.println(p);
        }
        ;

        try {
            {
                System.out.println("=================================================================");
                System.out.print("Please type ID to add item to cart or return to main menu (X): ");
                String idInput = scanner.nextLine();
                if (idInput.equalsIgnoreCase("X")) {
                    return;
                }
                //will take input and put it into idInput and compare it to what
                // is in the inventory array list ending in it being put into selectedProduct
                Product selectedProduct = findProductById(idInput, inventory);
                System.out.println("\nYou have added " + "\"" + selectedProduct.getName() + "\"" + " To your cart!");
                cart.add(selectedProduct);
            }
        } catch (Exception e) {
            System.out.println("Bad input, try again.");
        }

    }


    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)

        //cart is empty, therefore price is zero
        double totalCost = 0;
        System.out.println("\nYour cart:");
        System.out.println("==========");
        for (Product product : cart) {
            System.out.println(product.getName() + " " + "$" + product.getPrice());
            totalCost = totalCost + product.getPrice();
        }
        System.out.println("==============");
        System.out.println("Your total cost is: " + "$" + totalCost + "\n");

        System.out.print("Would you like to check out or return to main menu? (C/X): ");
        String idInput = scanner.nextLine();
        if (idInput.equalsIgnoreCase("X")) {
            return;
        } else if (idInput.equalsIgnoreCase("C")) {
            checkOut(cart, totalCost, scanner);
        }

    }


    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        System.out.println("Total: " + totalAmount);
        System.out.print("Would you like to proceed with purchase?(Y/N): ");
        String input = scanner.nextLine();

        double payment = 0;
        if (input.equalsIgnoreCase("Y")) {
            while (payment < totalAmount) {
                System.out.print("Please enter payment amount: ");
                payment = scanner.nextDouble();
                if (payment < totalAmount){
                    System.out.println("Insufficient funds, please try again");
                }

            }
        }
            /*{
            System.out.println("       Receipt      ");
            System.out.println("---------------------");*/

    }


/**
 * Searches a list for a product by its id.
 *
 * @return the matching Product, or null if not found
 */
public static Product findProductById(String id, ArrayList<Product> inventory) {
    for (Product p : inventory)
        if (p.getId().equalsIgnoreCase(id)) {
            return p;
        }
    return null;
}
}


 