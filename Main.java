import java.util.Arrays;
import java.util.Scanner;

public class Main {
static char[][] display;
static int totalSold;
static final int under60Price = 10;
static final int over60Price = 8;

    public static void initializeSeating(int rows, int seats){
        display = new char[rows][seats];
        for(char[] seat: display){
            Arrays.fill(seat,'S');
        }
    }
    public static void displaySeating(int rows, int seats){
        System.out.println("Cinema:");
        for (int seatCol=0; seatCol < seats; seatCol++){
            if (seatCol < 1){
                System.out.print("  ");
            }
            System.out.print(seatCol+1 + " ");
        }

        System.out.println();

        for (int seating = 0; seating < rows; seating++) {
            for (int row = 0; row < seats; row++) {
                if (row == 0){
                    System.out.print(seating+1 + " ");
                }
                System.out.print(display[seating][row] + " ");
            }
            System.out.println();
        }
    }

    public static Boolean bookSeating(int reservation_seat, int reservation_row){
        if(display[reservation_seat-1][reservation_row-1] == 'B'){
            return false;
        }
        else{
            display[reservation_seat-1][reservation_row-1] = 'B';
            return true;
        }
    }

    public static int soldSeating(){
        int count = 0;
        for (char[] chars : display) {
            for (int seats = 0; seats < display[0].length; seats++) {
                if (chars[seats] == 'B') {
                    count++;
                }
            }
        }
        return count;
    }
    public static void theatreStats(){
        System.out.println("Number of purchased tickets: "+soldSeating());
        float percentage = (float) soldSeating() / (display.length * display[0].length) * 100;
        String format = String.format("%.02f", percentage);
        System.out.println("Percentage: " + Float.valueOf(format) + "%");
        System.out.println("Current income: $"+totalSold);
        System.out.println("Total income: $"+totalIncome());
    }

    public static int totalIncome(){
        if(display.length * display[0].length > 60){
            return (display.length*display[0].length*under60Price);
        }
        else{
            return((((display.length/2)*display[0].length)*10)+(((display.length-(display.length/2))*display[0].length)*8));
        }
    }

    public static void ticketCost(int row){
        if(display.length * display[0].length > 60){
            if(row > display.length/2){
                System.out.println("Ticket price: $" + over60Price);
                totalSold +=over60Price;
            }
            else{
                System.out.println("Ticket price: $" + under60Price);
                totalSold += under60Price;
            }
        }
        else{
            System.out.println("Ticket price: $" + under60Price);
            totalSold += under60Price;
        }
    }

    public static void setReservation(){
        Scanner myReader = new Scanner(System.in);
        System.out.println("Enter row number:");
        int booking_row = myReader.nextInt();
        System.out.println("Enter a seat number in that row:");
        int booking_seat = myReader.nextInt();
        if(booking_row > display.length || booking_seat > display[0].length){
            System.out.println("Wrong input!");
            setReservation();
        }
        else{
            if(!bookSeating(booking_row, booking_seat)){
                System.out.println("This ticket has already been purchased!");
                setReservation();
            }
            else{
                ticketCost(booking_row);
            }
        }
    }

    public static void main(String[] args) {
        int choice;
        Scanner myReader = new Scanner(System.in);
        // set size of theatre
        System.out.println("Enter number of rows:");
        int rows = myReader.nextInt();
        System.out.println("Enter number of seats in each row:");
        int seats = myReader.nextInt();
        initializeSeating(rows, seats);
        displaySeating(rows, seats);
        do {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            choice = myReader.nextInt();
            if(choice == 1){
                displaySeating(rows, seats);
            }
            if(choice == 2){
                setReservation();
            }
            if(choice == 3){
                theatreStats();
            }
        } while(choice != 0);
    }
}