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
 * Class of Generic IceCreamSeller
 * 
 */
import java.util.Queue;
import java.util.Vector;
import l3s.de.tasks.Task;

public class IceCreamSeller {

    public double time;
    public static double end_time;
    public Vector<Customer> customer_list = new Vector<>();
    public Vector<Queue<Customer>> customer_queue_list = new Vector<>();
    public static Customer_List customers;
    public String brand = "Langnese";

    public String returnBrand() {
        return brand;
    }

    double currentTime() {
        return time;
    }

    public void insertCustomer(Task e) {
        customers.insert(e);
    }

    public void serveCustomers() {
        Task task;
        while (customers.getSize() > 0) {
            task = customers.popFirstElement();
            time = task.time;
            task.run(this);
        }

    }
}
