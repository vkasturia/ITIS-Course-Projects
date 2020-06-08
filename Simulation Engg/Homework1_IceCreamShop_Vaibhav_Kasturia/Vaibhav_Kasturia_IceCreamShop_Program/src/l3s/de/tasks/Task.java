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
 * Class which assigns Arrival Time and Scoop Serving Time (based on Number of Scoop customer wants) to each customer
 * 
 */

import java.util.Random;
import l3s.de.objects.IceCreamSeller;

public abstract class Task {

    public double time;
    public static final int hourConvertToSec = 3600;
    public static final int minConvertToSec = 60;

    //Serving time for each scoop that Mike takes
    public static int scoopServeTime = 30;

    public abstract void run(IceCreamSeller seller);

    public boolean lessThan(Task task) {
        return this.time < task.time;
    }

    public Double customerArrivalTime(double time) {
        double arrivalTime = 0;

        //Between 11:00 and 13:00 customer arrives randomly every 1 to 3 min
        if (time >= (11 * hourConvertToSec) && time < (13 * hourConvertToSec)) {
            arrivalTime = assignTime((1 * minConvertToSec), (3 * minConvertToSec));
        } //Between 13:00 and 17:00 customer arrives randomly every 2 to 10 min
        else if (time >= (13 * hourConvertToSec) && time < (17 * hourConvertToSec)) {
            arrivalTime = assignTime((2 * minConvertToSec), (10 * minConvertToSec));
        } //Between 17:00 and 19:00 customer arrives randomly every 1 to 2 min
        else if (time >= (17 * hourConvertToSec) && time < (19 * hourConvertToSec)) {
            arrivalTime = assignTime((1 * minConvertToSec), (2 * minConvertToSec));
        } //Between 19:00 and 20:00 customer arrives randomly every 2 to 5 min
        else if (time >= (19 * hourConvertToSec) && time < (20 * hourConvertToSec)) {
            arrivalTime = assignTime((2 * minConvertToSec), (5 * minConvertToSec));
        }

        return arrivalTime;

    }

    public Double customerScoopServeTime(double time) {
        double customerServingTime = 0;

        int[] numScoopPreference_Eleven_Twelve = new int[]{1, 2, 3};
        int[] numScoopPreference_Twelve_Fifteen = new int[]{3, 4, 5};
        int[] numScoopPreference_Fifteen_Twenty = new int[]{1, 2, 3, 4};

        //Between 11:00 and 12:00 all customers want randomly 1 to 3 scoops
        if (time >= (11 * hourConvertToSec) && time < (12 * hourConvertToSec)) {
            customerServingTime = (assignNumScoops(numScoopPreference_Eleven_Twelve) * scoopServeTime);
        }

        //Between 12:00 and 15:00 all customers want randomly 3 to 5 scoops
        else if (time >= (12 * hourConvertToSec) && time < (15 * hourConvertToSec)) {
            customerServingTime = (assignNumScoops(numScoopPreference_Twelve_Fifteen) * scoopServeTime);
        }

        //Between 15:00 and 20:00 all customers want randomly 1 to 4 scoops
        else if (time >= (15 * hourConvertToSec) && time < (20 * hourConvertToSec)) {
            customerServingTime = (assignNumScoops(numScoopPreference_Fifteen_Twenty) * scoopServeTime);
        }

        return customerServingTime;

    }

    public Double assignTime(int startTime, int endTime) {
        Random random = new Random();
        return Double.valueOf(startTime + random.nextInt(endTime - startTime + 1));
    }

    public static Double assignNumScoops(int[] array) {
        int rnd = new Random().nextInt(array.length);
        double numScoops = (double) array[rnd];
        return numScoops;
    }
}
