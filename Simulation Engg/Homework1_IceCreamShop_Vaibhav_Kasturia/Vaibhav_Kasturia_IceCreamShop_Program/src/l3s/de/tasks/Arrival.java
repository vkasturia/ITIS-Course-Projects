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
 * Class to indicate arrival of customer and add to queue. 
 * 
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import l3s.de.objects.Customer;
import l3s.de.objects.IceCreamSeller;

public class Arrival extends Task {

    int createdCustomerID;
    double arrival = 0;

    public static int[] customersArrived = new int[9];
    public static final int hourConvertToSec = 3600;

    public Arrival(double time) {
        createdCustomerID = 0;
        this.time = time;
    }

    @Override
    public void run(IceCreamSeller seller) {
        //If time crosses the shop closing time, close shop and do nothing
        if (time >= seller.end_time) {
            return;
        }

        //Keep incrementing id by 1 every time new customer arrives
        createdCustomerID += 1;
        Customer customer = new Customer(time, createdCustomerID);

        seller.customer_list.add(customer);
        System.out.println("Customer No. " + createdCustomerID + " arrives at time " + timeConverter((int) time));

        if (time >= (11 * hourConvertToSec) && time < (12 * hourConvertToSec)) {
            customersArrived[0] += 1;
        } else if (time >= (12 * hourConvertToSec) && time < (13 * hourConvertToSec)) {
            customersArrived[1] += 1;
        } else if (time >= (13 * hourConvertToSec) && time < (14 * hourConvertToSec)) {
            customersArrived[2] += 1;
        } else if (time >= (14 * hourConvertToSec) && time < (15 * hourConvertToSec)) {
            customersArrived[3] += 1;
        } else if (time >= (15 * hourConvertToSec) && time < (16 * hourConvertToSec)) {
            customersArrived[4] += 1;
        } else if (time >= (16 * hourConvertToSec) && time < (17 * hourConvertToSec)) {
            customersArrived[5] += 1;
        } else if (time >= (17 * hourConvertToSec) && time < (18 * hourConvertToSec)) {
            customersArrived[6] += 1;
        } else if (time >= (18 * hourConvertToSec) && time < (19 * hourConvertToSec)) {
            customersArrived[7] += 1;
        } else if (time >= (19 * hourConvertToSec) && time < (20 * hourConvertToSec)) {
            customersArrived[8] += 1;
        }
        
        //Add Customer to Customer Queue
        seller.customer_queue_list.elementAt(0).add(customer);

        //Start as soon as there is one customer in queue
        if (seller.customer_queue_list.elementAt(0).size() == 1) {
            Serve serve = new Serve(time);
            seller.insertCustomer(serve);
        }

        double next_customer_time = customerArrivalTime(time);
        time += next_customer_time;
        seller.insertCustomer(this);

    }

    public static String timeConverter(int time) {

        int millis = time * 1000;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);

        String convertedTime = df.format(new Date(millis));
        return convertedTime;
    }
    
    public static int[] returnArrivedCustomers() {
        return customersArrived;
    }

}
