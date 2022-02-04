package Service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    static Map<String,Customer> customerMap=new HashMap<String,Customer>();
    private static CustomerService customerService=new CustomerService();
    private CustomerService(){
    }
    public static CustomerService getInstance(){
        return customerService;
    }
    public static void addCustomer(String firstName,String lastName,String email){
        Customer customer=new Customer(firstName,lastName,email);
        CustomerService.customerMap.put(customer.getEmail(),customer);
    }
    public static Customer getCustomer(String customerEmail){
        return CustomerService.customerMap.get(customerEmail);
    }
    public static Collection<Customer> getAllCustomers(){
        return CustomerService.customerMap.values();
    }
}
