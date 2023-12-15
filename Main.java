import java.util.Arrays;
import java.util.Scanner;

public class Main {
static char[][] theatreGrid;
static int ticketsSold;
static final int under60Price = 10;
static final int over60Price = 8;

    private static void initializeSeating(int rows, int seats){
        theatreGrid = new char[rows][seats];
        for(char[] row: theatreGrid){
            Arrays.fill(row,'S');
        }
    }
    private static void displaySeating(){
        System.out.println("Cinema:");
        for (int seatNum = 0; seatNum < theatreGrid[0].length; seatNum++){
            if (seatNum < 1){
                System.out.print("  ");
            }
            System.out.print(seatNum+1 + " ");
        }

        System.out.println();

        for (int row = 0; row < theatreGrid.length; row++) {
            for (int seat = 0; seat < theatreGrid[0].length; seat++) {
                if (seat == 0) {
                    System.out.print((row + 1) + " ");
                }
                System.out.print(theatreGrid[row][seat] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Boolean bookSeating(int reservedSeat, int reservedRow){
        if(theatreGrid[reservedSeat-1][reservedRow-1] == 'B'){
            return false;
        }
        else{
            theatreGrid[reservedSeat-1][reservedRow-1] = 'B';
            return true;
        }
    }

    private static int countSeatsSold(){
        int count = 0;
        for (char[] chars : theatreGrid) {
            for (int seats = 0; seats < theatreGrid[0].length; seats++) {
                if (chars[seats] == 'B') {
                    count++;
                }
            }
        }
        return count;
    }
    private static void printStats(){
        System.out.println("Number of purchased tickets: "+ countSeatsSold());
        float percentage = (float) countSeatsSold() / (theatreGrid.length * theatreGrid[0].length) * 100;
        String format = String.format("%.02f", percentage);
        System.out.println("Percentage: " + Float.valueOf(format) + "%");
        System.out.println("Current income: $"+ ticketsSold);
        System.out.println("Total income: $"+ calculateTotalIncome());
    }

    private static int calculateTotalIncome(){
        if(theatreGrid.length * theatreGrid[0].length > 60){
            return (theatreGrid.length* theatreGrid[0].length*under60Price);
        }
        else{
            return((((theatreGrid.length/2)* theatreGrid[0].length)*10)+(((theatreGrid.length-(theatreGrid.length/2))* theatreGrid[0].length)*8));
        }
    }

    private static void calculateTicketCost(int row){
        if(theatreGrid.length * theatreGrid[0].length > 60){
            if(row > theatreGrid.length/2){
                System.out.println("Ticket price: $" + over60Price);
                ticketsSold +=over60Price;
            }
            else{
                System.out.println("Ticket price: $" + under60Price);
                ticketsSold += under60Price;
            }
        }
        else{
            System.out.println("Ticket price: $" + under60Price);
            ticketsSold += under60Price;
        }
    }

    private static void setReservation(){
        Scanner myReader = new Scanner(System.in);
        System.out.println("Enter row number:");
        int booking_row = myReader.nextInt();
        System.out.println("Enter a seat number in that row:");
        int booking_seat = myReader.nextInt();
        if(booking_row > theatreGrid.length || booking_seat > theatreGrid[0].length){
            System.out.println("Wrong input!");
            setReservation();
        }
        else{
            if(!bookSeating(booking_row, booking_seat)){
                System.out.println("This ticket has already been purchased!");
                setReservation();
            }
            else{
                calculateTicketCost(booking_row);
            }
        }
    }

    public static void main(String[] args) {
        int choice;
        Scanner myReader = new Scanner(System.in);
        System.out.println("Enter number of rows:");
        int rows = myReader.nextInt();
        System.out.println("Enter number of seats in each row:");
        int seats = myReader.nextInt();
        initializeSeating(rows, seats);
        displaySeating();
        do {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            choice = myReader.nextInt();
            if(choice == 1){
                displaySeating();
            }
            if(choice == 2){
                setReservation();
            }
            if(choice == 3){
                printStats();
            }
        } while(choice != 0);
    }
}
