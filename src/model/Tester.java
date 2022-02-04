package model;

public class Tester {
    public static void main(String[] args){
        Customer customer=new Customer("first","second","f@email.com");
        System.out.println(customer.toString());
        Customer wrong=new Customer("first","last","email");
        System.out.println(wrong);
    }
}
