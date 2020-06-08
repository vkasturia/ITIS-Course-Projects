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
 * Class which defines Customer
 */
public class Customer {

    public int customer_id;
    public double time_arrival;
    public double time_serviced;
    public double time_departed;

    public Customer(double time, int id) {
        customer_id = id;
        time_arrival = time;
    }

}
