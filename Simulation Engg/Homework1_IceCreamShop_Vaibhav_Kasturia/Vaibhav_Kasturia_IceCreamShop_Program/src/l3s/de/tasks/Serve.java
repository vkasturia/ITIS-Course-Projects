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
 * Class to take first customer from queue and serve him ice cream
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import l3s.de.objects.Customer;
import l3s.de.objects.IceCreamSeller;

public class Serve extends Task {

    public static int[] customersServed = new int[9];
    public static final int hourConvertToSec = 3600;

    public Serve(double time) {
        this.time = time;
    }

    public void run(IceCreamSeller seller) {
        Customer entity = seller.customer_queue_list.elementAt(0).peek();
        entity.time_serviced = time;

        System.out.println("Customer No. " + entity.customer_id + " is served ice cream at time " + timeConverter((int) time));

        if (time >= (11 * hourConvertToSec) && time < (12 * hourConvertToSec)) {
            customersServed[0] += 1;
        } else if (time >= (12 * hourConvertToSec) && time < (13 * hourConvertToSec)) {
            customersServed[1] += 1;
        } else if (time >= (13 * hourConvertToSec) && time < (14 * hourConvertToSec)) {
            customersServed[2] += 1;
        } else if (time >= (14 * hourConvertToSec) && time < (15 * hourConvertToSec)) {
            customersServed[3] += 1;
        } else if (time >= (15 * hourConvertToSec) && time < (16 * hourConvertToSec)) {
            customersServed[4] += 1;
        } else if (time >= (16 * hourConvertToSec) && time < (17 * hourConvertToSec)) {
            customersServed[5] += 1;
        } else if (time >= (17 * hourConvertToSec) && time < (18 * hourConvertToSec)) {
            customersServed[6] += 1;
        } else if (time >= (18 * hourConvertToSec) && time < (19 * hourConvertToSec)) {
            customersServed[7] += 1;
        } else if (time >= (19 * hourConvertToSec) && time < (20 * hourConvertToSec)) {
            customersServed[8] += 1;
        }

        double next_event_time = customerScoopServeTime(time);
        time += next_event_time;

        Depart customerDeparture = new Depart(time);
        seller.insertCustomer(customerDeparture);
    }

    public static String timeConverter(int time) {

        int millis = time * 1000;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);

        String convertedTime = df.format(new Date(millis));
        return convertedTime;
    }

    public static int[] returnServedCustomers() {
        return customersServed;
    }
}
