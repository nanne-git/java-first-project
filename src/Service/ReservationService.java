package Service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService implements ReservationServiceDefaultMethod{
    private static Map<String,IRoom> roomMap=new HashMap<String,IRoom>();
    //static Set<String> bookedRooms=new HashSet<String>();
    private static Map<Customer,Collection<Reservation>> reservationMap=new HashMap<Customer,Collection<Reservation>>();

    private static ReservationService reservationService=new ReservationService();
    private ReservationService(){}
    public static ReservationService getInstance(){
        return reservationService;
    }

    public static void addRoom(IRoom room){
        roomMap.put(room.getRoomNumber(),room);
    }
    public static IRoom getARoom(String roomId){
        return roomMap.get(roomId);
    }
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation=new Reservation(customer,room,checkInDate,checkOutDate);
        Collection<Reservation> reservationCollection=reservationMap.get(customer);
        if(reservationCollection==null){
            reservationCollection=new ArrayList<Reservation>();
        }
        reservationCollection.add(reservation);
        reservationMap.put(customer,reservationCollection);
        return reservation;
    }
    public static Collection<IRoom> findRooms(Date checkInDate,Date checkOutDate){
        Collection<IRoom> rooms=new ArrayList<IRoom>(roomMap.values());
        for(Collection<Reservation> reservations:reservationMap.values()){
            for(Reservation reservation:reservations){
                rooms.remove(reservation.getRoom());
            }
        }
        return rooms;
    }
    public static Collection<Reservation> getCustomersReservations(Customer customer){
        return reservationMap.get(customer);
    }
    public static void printAllReservation(){
        if(reservationMap.isEmpty()){
            System.out.println("No reservations");
        }
        else {
            for (Collection<Reservation> reservations : reservationMap.values()) {
                for (Reservation reservation : reservations) {
                    System.out.println(reservation.toString());
                }
            }
        }
    }
    public static Collection<IRoom> getAllRooms(){
        return roomMap.values();
    }
    public static Collection<IRoom> recommendedRooms(Date checkInDate,Date checkOutDate,int days){
        Collection<IRoom> rooms=new ArrayList<IRoom>(roomMap.values());
        for(Collection<Reservation> reservations:reservationMap.values()){
            for(Reservation reservation:reservations){
                Date reservationCheckOutDate=reservation.getCheckOutDate();
                reservationCheckOutDate.setDate(reservationCheckOutDate.getDate()+days);
                if(checkInDate.before(reservationCheckOutDate) && checkOutDate.after(reservation.getCheckInDate())){
                    rooms.remove(reservation.getRoom());
                }
                reservationCheckOutDate.setDate(reservationCheckOutDate.getDate()-days);
            }
        }
        return rooms;
    }
}
