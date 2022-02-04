import Service.CustomerService;
import api.HotelResource;
import com.sun.tools.javac.Main;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    /*
    1.Find and Reserve a room
    2.See my reservations
    3.Create an Account
    4.Admin
    5.Exit
    */
    HotelResource hotelResource=new HotelResource();
    Scanner sc=new Scanner(System.in);
    public void findAndReserveARoom() throws Exception{
        System.out.println("Enter check in date (mm/dd/yyyy)");
        String checkIn=sc.next();
        Date checkInDate=new Date(checkIn);
        System.out.println("Enter check out date (mm/dd/yyyy)");
        String checkOut=sc.next();
        Date checkOutDate=new Date(checkOut);
        if(checkOutDate.after(checkInDate) && !(checkInDate.before(new Date()))){
            Collection<IRoom> rooms=hotelResource.findARoom(checkInDate,checkOutDate);
            if(rooms.isEmpty()){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                String checkInString=simpleDateFormat.format(checkInDate);
                String checkOutString=simpleDateFormat.format(checkOutDate);
                Collection<IRoom> beforeAfterRoomsCollection=hotelResource.recommendedRooms(checkInDate,checkOutDate,0);
                if(!beforeAfterRoomsCollection.isEmpty()){
                    System.out.println("Available rooms between "+checkInString+" to "+checkOutString);
                    this.bookingARoom(beforeAfterRoomsCollection,checkInDate,checkOutDate);
                }
                else{
                    System.out.println("There are no rooms between "+checkInString+" to "+checkOutString);
                    checkInDate.setDate(checkInDate.getDate()+6);
                    checkOutDate.setDate(checkOutDate.getDate()+6);
                    String newCheckInString=simpleDateFormat.format(checkInDate);
                    String newCheckOutString=simpleDateFormat.format(checkOutDate);
                    Collection<IRoom> recommendedRoomsCollection=hotelResource.recommendedRooms(checkInDate,checkOutDate,0);
                    if(!recommendedRoomsCollection.isEmpty()){
                        System.out.println("recommended rooms between "+newCheckInString+" to "+newCheckOutString);
                        this.bookingARoom(recommendedRoomsCollection,checkInDate,checkOutDate);
                    }
                    else{
                        System.out.println("There are no recommended rooms between "+newCheckInString+" to "+newCheckOutString);
                    }
                }
            }
            else {
                System.out.println("--- available rooms ----");
                this.bookingARoom(rooms,checkInDate,checkOutDate);
            }
        }
        else{
            System.out.println("""
            ------------------------------------------------------------------------------
            | you can not book rooms in past or check out date should after check in date |
            ------------------------------------------------------------------------------
            """);
        }
    }
    public void seeMyReservations(String customerEmail){
        Collection<Reservation> reservations=hotelResource.getCustomersReservations(customerEmail);
        if(reservations==null){
            System.out.println("Currently you have not booked any room");
        }
        else{
            for(Reservation reservation:reservations) {
                System.out.println(reservation.toString());
            }
        }
    }
    public void createAnAccount(String firstName,String lastName,String email){
        hotelResource.createACustomer(firstName,lastName,email);
    }
    public boolean checkUserAccount(String email){
        if(hotelResource.getCustomer(email)==null){
            return false;
        }
        else{
            return true;
        }
    }
    public void bookingARoom(Collection<IRoom> rooms,Date checkInDate,Date checkOutDate){
        List<String> availableRooms=new ArrayList<String>();
        HashSet<IRoom> roomsSet = new HashSet<IRoom>(rooms);
        for (IRoom room : roomsSet) {
            System.out.println(room.toString());
            availableRooms.add(room.getRoomNumber());
        }
        System.out.println("would you like to book a room ?? Y or N");
        String bookingInput = sc.next();
        bookingInput=bookingInput.toLowerCase(Locale.ROOT);
        if (bookingInput.equals("y") || bookingInput.equals("yes")) {
            System.out.println("Do you have an account with us?? Y or N");
            String accountCheckInput = sc.next();
            accountCheckInput = accountCheckInput.toLowerCase(Locale.ROOT);
            if (accountCheckInput.equals("y") || accountCheckInput.equals("yes")) {
                System.out.println("Enter your email");
                String email = sc.next();
                if(this.checkUserAccount(email)) {
                    System.out.println("which room do you like to reserve");
                    String roomNumber = sc.next();
                    if(!availableRooms.contains(roomNumber)){
                        throw new IllegalArgumentException("please select only available rooms");
                    }
                    if(checkingAlreadyBookedRooms(email,checkInDate,checkOutDate)){
                        throw new IllegalArgumentException("There is another room already booked in those dates");
                    }
                    Reservation reservationDetails = hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
                    System.out.println(reservationDetails.toString());
                }
                else{
                    System.out.println("there is no account with that email address please create an account first");
                }
            } else if (accountCheckInput.equals("n") || accountCheckInput.equals("no")) {
                System.out.println("Create an account first");
            }
            else{
                System.out.println("Enter valid input");
            }
        }
    }
    public boolean checkingAlreadyBookedRooms(String customerEmail,Date checkInDate,Date checkOutDate){
        Collection<Reservation> customersReservationsList=hotelResource.getCustomersReservations(customerEmail);
        if(customersReservationsList==null){
            return false;
        }
        else {
            //System.out.println(customersReservationsList);
            for (Reservation customerReservation : customersReservationsList) {
                Date customerCheckInDate = customerReservation.getCheckInDate();
                Date customerCheckOutDate = customerReservation.getCheckOutDate();
                if (checkInDate.before(customerCheckOutDate) && checkOutDate.after(customerCheckInDate)) {
                    System.out.println("you already booked room number "+customerReservation.getRoom().getRoomNumber()+ " in those dates");
                    return true;
                }
            }
        }
        return false;
    }
}
