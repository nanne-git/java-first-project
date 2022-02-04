package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    public Customer(String firstName,String lastName,String email){
        this.firstName=firstName;
        this.lastName=lastName;
        if(!isEmail(email)){
            throw new IllegalArgumentException("is not a valid email address");
        }
        this.email=email;
    }
    public String toString(){
        return "First Name : "+firstName+"\nLast Name : "+lastName+"\nEmail : "+email;
    }
    public boolean isEmail(String email){
        String emailRegex="^(.+)@(.+).(.+)$";
        Pattern pattern=Pattern.compile(emailRegex);
        Matcher matcher=pattern.matcher(email);
        return matcher.find();
    }
    //for my convince
    public String getEmail(){
        return email;
    }
    //overriding equals and hashcode methods
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        else if(obj==null||obj.getClass()!=this.getClass()) {
            return false;
        }
        else{
            return false;
        }
    }
}
