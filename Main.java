// Abdus Quadri asq170030
package Tickets;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.exit;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.*;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        
        File inputFile = new File("A1.txt");
        Scanner input = new Scanner(System.in);
        int menuChoice = 0;                     // users menu chouce
        int rowChoice = 0;                      // user row choice
        String seatChoiceString = "";           // holds the input as a string 
        char seatChoice = ' ';                  // seatChoiceString coverted to a char at element 0
        int adult = 0, child = 0, senior = 0;   // counter variables
        
        
        // try catch block to handle file not found
         try{
        Auditorium auditorium = new Auditorium(inputFile);
        
        // main menu loop that validates input
        do{
            System.out.println("1. Reserve Seats\n2. Exit");
            
            boolean validMenu = true;
            do{
                try{
                menuChoice = input.nextInt();
                if(menuChoice != 1 && menuChoice != 2)
                    throw new Exception("Select either option 1 or option 2");
                validMenu = false;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter a valid integer menu choice");
                    input.nextLine();
                }
                catch(Exception ex2){
                    System.out.println(ex2.getMessage());
                }
            }while(validMenu);
            
            // exit loop if user selects option 2
            if(menuChoice == 2)
                break;
            
            // display the auditorium
            auditorium.displayf();
            
            // prompt for and validate a row
            boolean validRow = false;
            do{
                try{
                    System.out.print("Select a row: ");
                    rowChoice = input.nextInt();
                    if(rowChoice < 1 || rowChoice > auditorium.getNumRows())
                        throw new Exception("Selected row is out of bounds of the auditorium");
                    validRow = true;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter a valid integer");
                    input.nextLine();
                }
                catch(Exception ex2){
                    System.out.println(ex2.getMessage());
                }
            }while(!validRow);
                 
            
            // prompt for and validate a starting seat
            boolean validStart = false;
            do{
                try{
                    System.out.print("Select a starting seat: ");
                    seatChoiceString = input.next();
                    // exception if not a single char
                    if(seatChoiceString.length() != 1)
                      throw new Exception("Please enter only 1 letter");  
                    
                    seatChoiceString = seatChoiceString.toUpperCase();
                    seatChoice = seatChoiceString.charAt(0);
                    
                    if(seatChoice < 'A' || seatChoice > (char)(64 + auditorium.getNumSeats()))
                        throw new Exception("Selected seat is out of bounds of the auditorium");
               
                                
                    validStart = true;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter a valid char");
                    input.nextLine();
                }
                catch(Exception ex2){
                    System.out.println(ex2.getMessage());
                }
                
            }while(!validStart);
            
            // prompt for and validate amount of adult tickets
            boolean validAdult = false;
            do{
                try{
                    System.out.print("Enter the number of adult tickets: ");
                    adult = input.nextInt();
                      
                    if(adult < 0)
                        throw new Exception("Number of tickets must be a positive number");
                                
                    validAdult = true;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter an integer amount");
                    input.nextLine();
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
                
            }while(!validAdult);    // loop while invalid input
            
            
            // ask for amount of child tickets and validate
            boolean validChild = false;
            do{
                try{
                    System.out.print("Enter the number of child tickets: ");
                    child = input.nextInt();
                      
                    if(child < 0)
                        throw new Exception("Number of tickets must be a positive number");
                                
                    validChild = true;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter an integer amount");
                    input.nextLine();
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
                
            }while(!validChild);    // loop while invalid input
            
            // ask for senior amount and validate
            boolean validSenior = false;
            do{
                try{
                    System.out.print("Enter the number of senior tickets: ");
                    senior = input.nextInt();
                      
                    if(adult < 0)
                        throw new Exception("Number of tickets must be a positive number");
                                
                    validSenior = true;
                }
                catch(InputMismatchException ex){
                    System.out.println("Please enter an integer amount");
                    input.nextLine();
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
                
            }while(!validSenior);   // loop while invalid input
            
            int seatsWanted = adult + child + senior;
            
            // reserve seats if they are available
            if(checkAvailability(auditorium, seatChoice, rowChoice, seatsWanted)){
                reserveSeats(auditorium, seatChoice, rowChoice, adult, child, senior);
                char temp = (char)(seatChoice + seatsWanted - 1);
                System.out.println("Seats " + seatChoice + "-" + temp + " reserved");
            }
            else{
                String bestChoice = "";
                char temp2 = bestAvailable2(auditorium, adult, child, senior);
                char temp3 = (char)(temp2 + seatsWanted -1);
                
                // print differing prompts based on how many seats are wanted
                if(!(temp2 < 'A' || temp2 > 'Z' || temp3 < 'A' || temp3 > 'Z'))
                {
                    if(seatsWanted == 1)
                        System.out.println("Selected seats unavailable. Would you like to reserve the best available seat " + temp2 +"? (Y/N");
                    else{
                        System.out.println("Selected seats unavailable. Would you like to reserve the best available seats " +
                            temp2 + "-" + temp3 +"? (Y/N)");
                    }
                }
                else
                    System.out.println("Selected seats are unavailable. Reserve the best available seats? (Y/N)");
                    
                // validate y/n input for reserving best available seats
                boolean validBestChoice = false;
                do{
                    try{
                        bestChoice = input.next();
                        if(bestChoice.length() != 1)
                            throw new Exception("Enter only 1 character");
                        bestChoice = bestChoice.toUpperCase();

                        if(bestChoice.charAt(0) != 'Y' && bestChoice.charAt(0) != 'N'){
                            input.nextLine();
                            throw new Exception("Enter Y or N");
                        }
                        validBestChoice = true;
                    }
                    catch(InputMismatchException ex2){
                        System.out.println("Enter a valid character");
                    }
                    catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }while(!validBestChoice);
                
                // execute if user selects y
                if(bestChoice.charAt(0) != 'N'){
                    
                    // save best starting seat
                    char bestStart = bestAvailable(auditorium, adult, child, senior);

                    if(bestStart == '*')
                        System.out.println("No best available seats");
                    else{
                        char temp = (char)(bestStart + seatsWanted -1);
                        System.out.println("Seats " + bestStart + "-" + temp + " reserved");
                        auditorium.displayf();
                    }
                }
                    
            }
                
        }while(true);
        
        // display formatted report
        // save to file
        displayReport(auditorium);
        auditorium.saveToFile(inputFile);
        
         } // end auditorium try block
         catch(FileNotFoundException e){
            System.out.println("Error: Specified File was not found\nTry running the program again with the correct file");
            exit(0);
        }
                 
    }
    
    public static boolean checkAvailability(Auditorium auditorium, char seat, int row, int seatsWanted){
        final int ASCII_ADJUST = 64;                // variable to help adjust int to corresponding ASCII char
        int startingSeat = seat - ASCII_ADJUST;     // starting seat to check availability from
        boolean available = true;                   // return value
        
        // false if seatsWanted exceeds the number of seats in a row
        if(auditorium.getNumSeats() < (startingSeat + seatsWanted)-1)
            available = false;
        else{
            
            // loop through and check to see if there are seatsWanted consecutive seats available
            for(int count = startingSeat; count < (seatsWanted + startingSeat); count++, seat++){
                TheatreSeat tempSeat = auditorium.getSeat(row, seat);
                if(tempSeat.getReserved())
                    available = false;
            }
        }
        
        return available;
                
    }
    
    public static void reserveSeats(Auditorium auditorium, char seat, int row, int adult, int child, int senior){
       
        TheatreSeat temp = auditorium.getSeat(row, seat);   
        
        // reserve adult seats
        for(int i = 0; i < adult; i++){
            temp.setTicketType('A');
            temp.setReserved(true);
            temp = temp.right;
        }
        
        // reserve child seats
        for(int i = 0; i < child; i++){
            temp.setTicketType('C');
            temp.setReserved(true);
            temp = temp.right;
        }
        
        // reserve senior
        for(int i = 0; i < senior; i++){
            temp.setTicketType('S');
            temp.setReserved(true);
            temp = temp.right;
        }
        
        
    }
    
    // calc distance between two points
    public static double calcDistance(double sectX, double sectY, double audiX, double audiY){
        // distance formula
        return Math.sqrt(Math.pow(audiX - sectX, 2) + Math.pow(audiY - sectY, 2));
    }
    
    public static char bestAvailable(Auditorium auditorium, int adult, int child, int senior){
        final double LARGE_DISTANCE = 1000;
        double distance = LARGE_DISTANCE;
        double audiX = (double)(auditorium.getNumSeats() + 1) / 2.0;
        double audiY = (double)(auditorium.getNumRows() + 1) / 2.0;
        int seatsWanted = adult + child + senior;
        char bestStartingSeat = ' ';
        int bestRow = 0;
        boolean reserved = false;
        double rowDistance = 100;
        

        // loop through to check every seat and row
        for(int rowIndex = 1; rowIndex <= auditorium.getNumRows(); rowIndex++){
            for(char seatIndex = 'A'; seatIndex < (char)((65 + auditorium.getNumSeats()) - seatsWanted + 1); seatIndex++){
                if(checkAvailability(auditorium, seatIndex, rowIndex, seatsWanted)){
                    
                    double sectX = (double)(seatIndex-64 + (double)(seatsWanted-1)/2.0);    // section midpoint (x direction)
                    double sectY = rowIndex;                                                // section row (y direction)
                    double newDistance = calcDistance(audiX, audiY, sectX, sectY);          // distance from selection mp to audi mp
                    
                    // absolute value of newDistance
                    if(newDistance < 0)
                        distance *= -1;
                    
                    if(newDistance < distance){
                        distance = newDistance;
                        bestStartingSeat = seatIndex;
                        bestRow = rowIndex;
                        
                    }
                    
                    //-------------------------------------------------------------------
                    // tiebreaker code
                    if(newDistance == distance){

                        double newRowDistance = sectY - audiY;
                        if(newRowDistance < 0)
                            newRowDistance *= -1;
                        
                        double oldRowDistance = bestRow - audiY;            
                        if(newRowDistance < oldRowDistance){
                            bestStartingSeat = seatIndex;
                            bestRow = rowIndex;
                        }
                        else if(newRowDistance == oldRowDistance){
                            if(rowIndex < bestRow)
                                bestRow = rowIndex;
                        }                        
                    }
                    //-------------------------------------------------------------------------
                }
                
            }
        }
        
        // set flag
        if(distance == LARGE_DISTANCE)
            reserved = false;
        else{
            // reserveSeats(Auditorium auditorium, char seat, int row, int adult, int child, int senior)
            reserveSeats(auditorium, bestStartingSeat, bestRow, adult, child, senior);
            reserved = true;
        }
        
        if(reserved)
            return bestStartingSeat;
        else
            return '*';     // returning this val means there are no best available seats
                       
    }
    
    // same as bestAvailable, but returns the starting seat
    public static char bestAvailable2(Auditorium auditorium, int adult, int child, int senior){
        final double LARGE_DISTANCE = 1000;
        double distance = LARGE_DISTANCE;
        double audiX = (double)(auditorium.getNumSeats() + 1) / 2.0;
        double audiY = (double)(auditorium.getNumRows() + 1) / 2.0;
        int seatsWanted = adult + child + senior;
        char bestStartingSeat = ' ';
        int bestRow = 0;
        boolean reserved = false;
        double rowDistance = 100;
        
    
        for(int rowIndex = 1; rowIndex <= auditorium.getNumRows(); rowIndex++){
            for(char seatIndex = 'A'; seatIndex < (char)((65 + auditorium.getNumSeats()) - seatsWanted + 1); seatIndex++){
                if(checkAvailability(auditorium, seatIndex, rowIndex, seatsWanted)){
                    
                    // sectionMP = (double)(startingSeat + ((double)(seatsWanted - 1) / 2.0));
                    
                    double sectX = (double)(seatIndex-64 + (double)(seatsWanted-1)/2.0);
                    double sectY = rowIndex;
                    double newDistance = calcDistance(audiX, audiY, sectX, sectY);
                    
                    if(newDistance < 0)
                        distance *= -1;
                    
                    if(newDistance < distance){
                        distance = newDistance;
                        bestStartingSeat = seatIndex;
                        bestRow = rowIndex;
                        
                    }
                    
                    //-------------------------------------------------------------------
                    // tiebreaker code                
                        if(newDistance == distance){
                       
                            double newRowDistance = sectY - audiY;
                            if(newRowDistance < 0)
                                newRowDistance *= -1;

                            double oldRowDistance = bestRow - audiY;            
                            if(newRowDistance < oldRowDistance){
                                bestStartingSeat = seatIndex;
                                bestRow = rowIndex;
                            }
                        else if(newRowDistance == oldRowDistance){
                            if(rowIndex < bestRow)
                                bestRow = rowIndex;
                        }
                        
                    } // end if statement
                    //-------------------------------------------------------------------------
                }
                
            }
        }
        
        if(distance == LARGE_DISTANCE)
            reserved = false;
        else{
            // reserveSeats(auditorium, bestStartingSeat, bestRow, adult, child, senior);
            reserved = true;
        }

        if(reserved)
            return bestStartingSeat;
        else
            return '*';
                    
    }
    
    
    // display report to console
    public static void displayReport(Auditorium auditorium){
        final int ASCII_ADJUST = 64;
        int adultCount = 0, childCount = 0, seniorCount = 0, emptyCount = 0;
        
        // set current to head for traversal purposes
        TheatreSeat current = auditorium.getHead();
        char seatContents;
        
     for(int rowIndex = 0; rowIndex < auditorium.getNumRows(); rowIndex++){
         for(char seatIndex = 'A'; seatIndex <= (char)(auditorium.getNumSeats()+ ASCII_ADJUST); seatIndex++){
             if(rowIndex != 0 || seatIndex != 'A')
                 current = auditorium.getSeat(rowIndex+1, seatIndex);
             seatContents = current.getTicketType();
             
             // increment counter values for each instance of ticketType
             switch(seatContents) 
                    {
                        case 'A':
                            adultCount++;
                            break;
                        case 'C':
                            childCount++;
                            break;
                        case 'S':
                            seniorCount++;
                            break;
                        default:
                            emptyCount++;
                            break; 
                    } // end switch
             
         }
     }
     
     int totalSeats, totalSold;
     double totalSales;
     
        // calculate final statistics
        totalSeats = adultCount + childCount + seniorCount + emptyCount;
        totalSold = totalSeats - emptyCount;
        totalSales = (double)((10 * adultCount) + (5 * childCount) + (7.5 * seniorCount));
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        // report to console
        System.out.println("Total Seats:         " + totalSeats);
        System.out.println("Total Sold:          " + totalSold);
        System.out.println("Adult tickets sold:  " + adultCount);
        System.out.println("Child tickets sold:  " + childCount);
        System.out.println("Senior tickets sold: " + seniorCount);
        System.out.println("Total Sales:         $" + df.format(totalSales));
             
    }
    
} // end main.java