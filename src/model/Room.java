package model;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;
    public Room(String roomNumber,Double price,RoomType roomType){
        this.roomNumber=roomNumber;
        this.price=price;
        this.roomType=roomType;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
    public Double getRoomPrice(){
        return price;
    }
    public RoomType getRoomType(){
        return roomType;
    }
    public boolean isFree(){
        if(this.price==0.0){
            return true;
        }
        else{
            return false;
        }
    }
    public String toString(){
        return "Room number : "+roomNumber+"\nRoom Type : "+roomType+"\nRoom price : "+price;
    }
}
