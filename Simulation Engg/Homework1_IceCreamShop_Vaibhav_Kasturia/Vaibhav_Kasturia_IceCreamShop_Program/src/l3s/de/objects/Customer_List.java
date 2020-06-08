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
 * Class for a list of Customers object type
 * 
 */
import java.util.Vector;
import l3s.de.tasks.Task;

public class Customer_List {

    public Vector<Task> customer_list = new Vector<Task>();

    public void insert(Task task) {
        int x = 0;
        while (x < customer_list.size() && customer_list.elementAt(x).lessThan(task)) {
            x += 1;
        }
        customer_list.add(task);
    }

    public int getSize() {
        return customer_list.size();
    }

    public Task popFirstElement() {
        Task task = customer_list.firstElement();
        customer_list.removeElementAt(0);
        return task;
    }

}
