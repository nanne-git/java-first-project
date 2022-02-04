import model.Room;
import model.RoomType;

import java.util.Scanner;

public class HotelApplication {
    public static void main(String[] args){
        MainMenu mainMenu=new MainMenu();
        AdminMenu adminMenu=new AdminMenu();
        Boolean mainMenuFlag=true;
        while(mainMenuFlag) {
            System.out.println("""
                             Welcome to hotel reservation application
                    -----------------------------------------------------------
                    1.Find and reserve a room
                    2.See my reservations
                    3.Create an account
                    4.Admin
                    5.Exit
                    -----------------------------------------------------------""");
            Scanner sc=new Scanner(System.in);
            String mainMenuInput=sc.next();
            switch (mainMenuInput){
                case("1"):
                    try {
                        mainMenu.findAndReserveARoom();
                    }catch (Exception e) {
                        System.out.println("invalid inputs/arguments please try again");
                    }
                    break;
                case("2"):
                    System.out.println("please enter email address to view your reservation");
                    String customerEmail=sc.next();
                    if(mainMenu.checkUserAccount(customerEmail)) {
                        mainMenu.seeMyReservations(customerEmail);
                    }
                    else{
                        System.out.println("You dont have account with us please create an account and book available rooms");
                    }
                    break;
                case("3"):
                    System.out.println("Enter email");
                    String email = sc.next();
                    if(mainMenu.checkUserAccount(email)){
                        System.out.println("You already have an account with us , you can book rooms");
                    }
                    else {
                        System.out.println("Enter first Name");
                        String firstName = sc.next();
                        System.out.println("Enter last Name");
                        String lastName = sc.next();
                        try {
                            mainMenu.createAnAccount(firstName, lastName, email);
                            System.out.println("Account is created");
                        } catch (Exception e) {
                            System.out.println(email + e.getMessage());
                        }
                    }
                    break;
                case("4"):
                    Boolean adminMenuFlag=true;
                    while(adminMenuFlag){
                        System.out.println("""
                            -----------------------------------------------------------
                            1.See All customers
                            2.See All Rooms
                            3.See All Reservations
                            4.Add A Room
                            5.Back to main menu
                            -----------------------------------------------------------""");
                        String adminMenuInput=sc.next();
                        switch (adminMenuInput){
                            case("1"):
                                adminMenu.seeAllCustomers();
                                break;
                            case("2"):
                                adminMenu.seeAllRooms();
                                break;
                            case("3"):
                                adminMenu.seeAllReservations();
                                break;
                            case("4"):
                                try {
                                    System.out.println("Enter a room number");
                                    String roomNumber = sc.next();
                                    System.out.println("Enter price per night");
                                    String stringPrice = sc.next();
                                    Double price=Double.parseDouble(stringPrice);
                                    System.out.println("Enter room type\n1.for single bed\n2.for double bed");
                                    int roomType = sc.nextInt();
                                    RoomType roomTypeString = null;
                                    if (roomType == 1) {
                                        roomTypeString = RoomType.SINGLE;
                                    } else if (roomType == 2) {
                                        roomTypeString = RoomType.DOUBLE;
                                    }
                                    else{
                                        throw new IllegalArgumentException("invalid roomtype");
                                    }
                                    Room room = new Room(roomNumber, price, roomTypeString);
                                    adminMenu.addARoom(room);
                                    System.out.println("Room added successfully");
                                }
                                catch (Exception e){
                                    System.out.println(e.getMessage());
                                    System.out.println("Please try again with valid inputs");
                                }
                                break;
                            case("5"):
                                adminMenuFlag=false;
                                break;
                            default:
                                System.out.println("Not a valid input");
                                break;
                        }
                    }
                    break;
                case("5"):
                    mainMenuFlag=false;
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    break;
            }
        }
    }
}
