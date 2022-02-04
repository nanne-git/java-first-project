import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminMenu {
    /*
    1.See All customers
    2.See All Rooms
    3.See All Reservations
    4.Add A Room
    5.Back To Main Menu
   */
    AdminResource adminResource=new AdminResource();
    public void seeAllCustomers(){
        Collection<Customer> customers=adminResource.getAllCustomers();
        if(customers.isEmpty()){
            System.out.println("There are no customers");
        }
        else {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }
    }
    public void seeAllRooms(){
        Collection<IRoom> rooms=adminResource.getAllRooms();
        if(rooms.isEmpty()){
            System.out.println("There are no rooms");
        }
        else {
            for (IRoom room : rooms) {
                System.out.println(room.toString());
            }
        }
    }
    public void seeAllReservations(){
        adminResource.displayAllReservations();
    }
    public void addARoom(IRoom room){
        List<IRoom> rooms=new ArrayList<IRoom>();
        rooms.add(room);
        adminResource.addRoom(rooms);
    }
}
