// Cooper McCaffrey
// imported libraries

import java.util.Scanner;   // keyboard & file inputs & outputs
import java.io.File;        // connecting to external files
import java.io.IOException; // handle unexpected input/output runtime errors
import java.io.FileWriter;  // writing data to external file

class Main 
{
  // constants

  static final String NAME = "Sven";    // name of program  
  static final int EXIT_CHOICE = 3;     // menu choice to exit program
  
  // external data file containing list of items sold in vending machine   
  static final String INVENTORY_LIST_FILENAME = "ItemsForSale.txt";

  public static void main(String[] args)
  {
    // local variables

    // list of items for sale
    InventoryList itemsForSale = new InventoryList();

    // to obtain human's input  
    Scanner keyboard = new Scanner(System.in);          
  
    String userMenuChoice = "";   // human inputted menu choice
    String newItem = "";          // new item added to inventory
    String PIN = "0000";

    // customer selection from menu list of items
    int customerSelection = 0;    

    boolean updateSuccess = false;  // flag to indicate if new item added or not

    //////////////////////  read list of items for sale 
    //////////////////////  from external data file
    try
    {
      Scanner externalFile = new Scanner(new File(INVENTORY_LIST_FILENAME));
      
      while (externalFile.hasNext())
      {
        String nextItem = externalFile.nextLine();
        itemsForSale.addItem(nextItem);
      }
      externalFile.close();

    }
    catch (IOException inputOutputError)    // if error occurs trying to read data to file
    {
      System.err.println(inputOutputError); // directing system error to error log file
    }


    ////////////////////// setting up the screen
    clearScreen();

    System.out.println("\n\n");       // blank lines to space output

    System.out.println("\033[1;44mHello, I am the world's first smart vending machine. My name is " + NAME + "!\033[0m");
    
    displayMachine();
    
    // derived from http://www.angelfire.com/co/cajhnesplace/ascii/vend.html
    
    System.out.println("\n");       // blank lines to space output
    
    ////////////////////// interact with customer

    // loop until human inputs the choice to exit the menu
    while (!userMenuChoice.equals(EXIT_CHOICE + ""))
    {
      displayMenu();

      System.out.print("Choice: ");

      // trim leading & trailing spaces
      userMenuChoice = keyboard.nextLine().trim();  

      ////////////////////// Admin Mode

      if (userMenuChoice.equals("1") || userMenuChoice.equals("A")  || userMenuChoice.equals("a"))
      {
        System.out.println("IN ADMIN MODE.....");
        

        System.out.println("Please enter PIN: ");
        PIN = keyboard.nextLine();

        if (PIN.equals("0000"))
        {
          System.out.println("Still in     [-]ADMIN MODE[-]");
        }
        else if (!(PIN.equals("0000")))
        {
          System.out.println("INCORRECT PASSWORD.");
          System.exit(0);
        }

        System.out.print("What is the new item to add to the machine's inventory? ");
        newItem = keyboard.nextLine();    // obtain user's input via keyboard
        
        updateSuccess = false;
        
        if (!itemsForSale.addItem(newItem))
        {
          System.out.println("The update failed. Perhaps the item was already in the list.");    
        }
        else
        {
          
          try
          { 
            // add the new item to the end of the file
            // connect to external file, true specifies append mode
            FileWriter externalFile = new FileWriter(INVENTORY_LIST_FILENAME, true);  
            externalFile.write("\n" + newItem); // new line first to preserve format of existing file
            System.out.println(newItem);
            externalFile.flush();                      
            externalFile.close(); 

            updateSuccess = true;   
            System.out.println("Your updated inventory list is: ");
            itemsForSale.displayList();                                 
          }                                                           
          catch (IOException ioe) // if error occurs trying to write data to file
          {
            System.err.println(ioe);
            System.out.println("The update failed due to an error working with an external data file.");
          }

        }

      }
      ////////////////////// Customer Mode
      else if (userMenuChoice.equals("2") || userMenuChoice.equals("C") || userMenuChoice.equals("c"))
      {
        System.out.println("IN CUSTOMER MODE.....");

        // display the list of items as a numbered list
        for (int i = 0; i < itemsForSale.numberOfItems(); i++)
        {
          System.out.println((i + 1) + ". " + itemsForSale.selectItem(i));
        }

        System.out.println("What would you like to purchase? ");
        customerSelection = keyboard.nextInt();
/*
        for (int i = 0; i < numItems; i++)
        {
          InventoryList nextItem: itemsForSale)
        {
          if (nextItem.equals(userInput))
          {
            itemsForSale.removeItem(nextItem);
            break;
          }
        }
*/
        System.out.println("DEBUGGING: the customer input is #" + customerSelection);
        
        try
        { 
          FileWriter externalFile = new FileWriter(INVENTORY_LIST_FILENAME, false);  
          // false specifies write mode (thanks KP)

          for (int i = 0; i < itemsForSale.numberOfItems(); i++)
          {
            externalFile.write(itemsForSale.selectItem(i) + "\n");
          }

          System.out.println("The updated list is: ");
          itemsForSale.displayList();      

          externalFile.flush();                      
          externalFile.close();                                    
        }                                                           
        catch (IOException ioe) // if error occurs trying to write data to file
        {
          System.err.println(ioe);
        }

        // TODO
        // display available item by reading those values from an external data file
        // allow customer to input a choice
        // trust the user to select the correct item from the machine & 
        // deposit exact amount of MinichPay (not ApplePay or AndroidPay) money 
        // in our Room 311 cash till on the honesty system
      }
      else if (userMenuChoice.equals("3") || userMenuChoice.equals("E") || userMenuChoice.equals("e"))
      {
       System.out.println("Exit");
       System.out.println("Goodbye! Have a nice day! SF");
       break;
      }
      else if (userMenuChoice.equals("minich") || userMenuChoice.equals("Minich") |userMenuChoice.equals("MINICH"))
      {
        System.out.println("YOU HAVE ENTERED GAME MODE");
        Hangman game = new Hangman();
          game.play();
      }

    } // end of while loop


  } // end of main method

  /////////////////////// static functions

  // displays menu
  public static void displayMenu()
  {  
    System.out.println("\033[43m1. (A)dmin Mode\033[0m");
    System.out.println("\033[46m2. (C)ustomer Mode\033[0m");
    System.out.println("\033[1;41m" + EXIT_CHOICE + ". (E)xit\033[0m\033[32m");
  }

  // deletes everything in console output
  public static void clearScreen() 
  {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }  

  public static void displayMachine()
  {
    System.out.println("  \033[34m  _____   ___     ___  _______  __  __           ");
     System.out.println("  \033[34m / ____|  \\ \\    / /  |  ____| | \\ | |       ");
     System.out.println(" \033[34m | (___     \\ \\  / /   | |__    |  \\| |       ");
     System.out.println(" \033[34m \\___  \\     \\ \\/ /    |  __|   | . ` |      ");
     System.out.println(" \033[34m  ____) |     \\  /     | |____  | |\\  |        ");
     System.out.println(" \033[34m |_____/       \\/      |______| |_| \\_|        ");


      System.out.println("\033[36m|____________________________________________|"); 
      System.out.println("|####### \033[34mMinich's Sven Vending Machine\033[0m\033[36m ######|"); 
      System.out.println("|#|===========================|#############T|"); 
      System.out.println("|#|  \033[34m=====\033[36m  \033[32m..--''`\033[36m\033[36\033[36m  \033[33m|~~``|\033[36m   |##|````````|#y|"); 
      System.out.println("|#|  \033[34m|  |\033[36m   \033[32m\\     |\033[36\033[36m  \033[32m\033[33m:    | \033[36m  |##| \033[32mHello, \033[36m|#p|"); 
      System.out.println("|#|  \033[34m|___|\033[36m   \033[32m/___ |\033[36m  \033[33m| ___|\033[36m   |##|\033[32mi'm Sven\033[36m|#e|"); 
      System.out.println("|#|  \033[30m/=__\\  ./.__\\   |/,__\\\033[36m   |##| \033[32mplease\033[36m |##|"); 
      System.out.println("|#|  \033[30m\\__//   \\__//    \\__//\033[36m   |##| \033[32mchoose\033[36m |#M|"); 
      System.out.println("|#|===========================|##| \033[32man item\033[36m #i|"); 
      System.out.println("|#|```````````````````````````|#############n|"); 
      System.out.println("|#| \033[31m=.._\033[36m      \033[35m+++\033[36m     \033[32m//////\033[36m  |#############i|"); 
      System.out.println("|#| \033[31m\\/  \\\033[36m     \033[35m| |\033[36m    \033[32m\\ \\   \\\033[36m  |#|`````````|#c|"); 
      System.out.println("|#|  \033[31m\\___\\\033[36m    \033[35m|_|\033[36m     \033[32m/___ /\033[36m  |#| \033[32m_______\033[36m |#h|"); 
      System.out.println("|#|  \033[30m/ __\\   /__ \\   // __\\\033[36m\033[36m   |#| \033[0m\033[43m|1|2|3|\033[0m\033[36m |##|"); 
      System.out.println("|#|  \033[30m\\__//-  \\__//   -\\__//\033[36m   |#| \033[0m\033[43m|4|5|6|\033[0m\033[36m |#F|"); 
      System.out.println("|#|===========================|#| \033[0m\033[43m|7|8|9|\033[0m\033[36m |#o|"); 
      System.out.println("|#|```````````````````````````|#| ``````` |#r|"); 
      System.out.println("|#| ..--    \033[34m______\033[36m   \033[31m.--._.\033[36m   |#|[=======]|##|"); 
      System.out.println("|#| \\   \\   \033[34m|    |\033[36m   \033[31m|    |\033[36m   |#|  _   _  |#A|"); 
      System.out.println("|#|  \\___\\  \033[34m: ___:\033[36m   \033[31m| ___|\033[36m   |#| \033[35m|||\033[36m \033[30m( )\033[36m |##|"); 
      System.out.println("|#|  \033[30m/ __\\  |/ __\\   // __\\\033[36m   |#| \033[35m|||\033[36m  `  |#S|"); 
      System.out.println("|#|  \033[30m\\__//   \\__//  /_\\__//\033[36m   |#|  ~      |#u|"); 
      System.out.println("|#|===========================|#|_________|#p|");     
      System.out.println("|#|\033[30m```````````````````````````\033[36m|#############r|");
      System.out.println("|###########################################i|"); 
      System.out.println("|#|||||||||||||||||||||||||||||####\033[30m```````\033[36m##s|");     
      System.out.println("|#||||||||||||\033[0m\033[43mPUSH\033[0m\033[36m|||||||||||||####\033[30m\\|||||/\033[36m##e|");
      System.out.println("|###########################################!|");   
      System.out.println("|____________________________________________|\033[0m");
  }

} // end of Main class
