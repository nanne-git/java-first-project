package api;

import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    CustomerService customerService=CustomerService.getInstance();
    ReservationService reservationService=ReservationService.getInstance();
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void createACustomer(String firstName,String lastName,String email){
        customerService.addCustomer(firstName,lastName,email);
    }
    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return reservationService.getCustomersReservations(customerService.getCustomer(customerEmail));
    }
    public Collection<IRoom> findARoom(Date checkIn,Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }
    public Collection<IRoom> recommendedRooms(Date checkInDate,Date checkOutDate,int days){
        return reservationService.recommendedRooms(checkInDate,checkOutDate,days);
    }
}
