/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package l3s.de.objects;

/**
 *
 * @author Vaibhav Kasturia <kasturia at l3s.de>
 * 
 * Class for the ice cream seller Mike. Mike inherits from the IceCreamSeller class
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;

import l3s.de.tasks.Arrival;
import l3s.de.tasks.Serve;

public class Mike extends IceCreamSeller {

    public static final int hourConvertToSec = 3600;
    public final String hairColor = "Brown";
    
    public Mike() {
        time = 0;
        end_time = 20 * hourConvertToSec;
        Queue<Customer> queue = new LinkedList<Customer>();
        customer_queue_list.add(queue);
    }

    public static void main(String[] args) {
        Mike mike = new Mike();

        customers = new Customer_List();
        int shopOpeningTime = 11 * hourConvertToSec;
        System.out.println("Shop Opened at time = " + (timeConverter((int) shopOpeningTime)));
        mike.insertCustomer(new Arrival(shopOpeningTime));
        mike.serveCustomers();
        System.out.println("Stopped serving Customers. Shop Closed");
        
        int[] customersArrived = Arrival.returnArrivedCustomers();
        int[] customersServed = Serve.returnServedCustomers();
        
        System.out.println("\n ------------------------------------------------------------------------\n");
        System.out.println("STATISTICS:");
        System.out.println("Customers arrived per hour from 11.00 to 20.00 =" + Arrays.toString(customersArrived));
        System.out.println("Customers served per hour from 11.00 to 20.00 =" + Arrays.toString(customersServed));
    }

    public static String timeConverter(int time) {
        int millis = time * 1000;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);

        String convertedTime = df.format(new Date(millis));
        return convertedTime;
    }
    
    public String returnHairColor(){
        return hairColor;
    }
}
