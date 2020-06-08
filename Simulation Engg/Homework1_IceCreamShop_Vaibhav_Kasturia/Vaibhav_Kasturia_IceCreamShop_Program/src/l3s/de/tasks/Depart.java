/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package l3s.de.tasks;

/**
 *
 * @author Vaibhav Kasturia <kasturia at l3s.de>
 * 
 * Class to remove Customer from queue after being served ice cream and indicate his departure
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import l3s.de.objects.Customer;
import l3s.de.objects.IceCreamSeller;

public class Depart extends Task {

    public Depart(double time) {
        this.time = time;
    }

    public void run(IceCreamSeller seller) {
        Customer customer = seller.customer_queue_list.elementAt(0).poll();
        customer.time_departed = time;

        System.out.println("Customer No. " + customer.customer_id + " is departed at time " + timeConverter((int) time));
        
        //Check whether arrival time of next person in queue is greater than ice cream serving time of current customer
        try {
            Customer next_customer = seller.customer_queue_list.elementAt(0).element();
            if (time < next_customer.time_arrival) {
                time = next_customer.time_arrival;
            }
        } catch (NoSuchElementException e) {
            //Do Nothing
        }
        
        if (seller.customer_queue_list.elementAt(0).size() > 0) {
            Serve serve = new Serve(time);
            seller.insertCustomer(serve);
        }
    }

    public static String timeConverter(int time) {

        int millis = time * 1000;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);

        String convertedTime = df.format(new Date(millis));
        return convertedTime;
    }
}
