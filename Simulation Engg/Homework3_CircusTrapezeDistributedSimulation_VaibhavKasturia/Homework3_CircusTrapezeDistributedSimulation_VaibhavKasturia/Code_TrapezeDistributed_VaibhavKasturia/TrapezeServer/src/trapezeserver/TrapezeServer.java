/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trapezeserver;

/**
 *
 * @author Vaibhav Kasturia <kasturia at l3s.de>
 * 
 * Server class with gets input from Client and calculates and sends back to the client updated theta, velocity and time 
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import static java.lang.Math.sin;

class TrapezeServer{

//Constants    
static double g = 9.8;
static double length = 5;
static double delta_time= 0.1;
static double count =0;
static double temp_theta = 0;

// Function to calculate theta 
static double calc_function1(double time, double velocity){
    return velocity*time;
}

// Function for calculating velocity using Runge Kutta Method while iterating in a loop
static double calc_function2(double time, double theta){
    return -g/length*sin(theta)*time;
}

//Function for calculating parameters using Runge-Kutta Method for finding next theta for trapeze
static double calc_range1(double time, double velocity){
    double h = time/2;
    double k1= calc_function1(time, velocity);
    double k2 = calc_function1((time+(h/2)), (velocity+h/2*(k1)));
    double k3 = calc_function1((time+(h/2)), (velocity+h/2*(k2)));
    double k4 = calc_function1((time+(h)), (velocity+h*(k3)));
    
    double val = h * (k1 + 2 * k2 + 2 * k3 + k4)/6;
    return val;
    
}

//Function for calculating parameters for finding next velocity for trapeze
static double calc_range2(double time, double theta){
    double h = time / 2;
    double k1= calc_function2(time, theta);
    double k2 = calc_function2((time+(h / 2)), (theta+h / 2*(k1)));
    double k3 = calc_function2((time+(h / 2)), (theta+h / 2*(k2)));
    double k4 = calc_function2((time+(h)), (theta+h*(k3)));
    
    double val = (h / 6 * (k1 + 2 * k2 + 2 * k3 + k4));
    return val;
}

	public static void main(String[] args) {
		try{

			ServerSocket server_socket = new ServerSocket(1411);
			Socket socket = server_socket.accept();
                        
			DataInputStream dataInput = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());

			String inputMessage;
                        String outputMessage;

                        PrintWriter writer = new PrintWriter("./ServerOutput.txt");
                    
			while(count < 1000){
                            count += 1;
				inputMessage = dataInput.readUTF();
                                String[] data = inputMessage.split("\t");
                                
                                double theta = Double.parseDouble(data[0]) * 6.28 / 360;
                                double velocity = Double.parseDouble(data[1]);
                                double time = Double.parseDouble(data[2]);
                                
                                theta -= velocity * delta_time;
                                velocity += (g/length * sin(theta)) * delta_time;
                                time += delta_time;

                            	writer.println("Incoming message from Client is : "+inputMessage); 
                                
				outputMessage = String.valueOf(theta * 360 / 6.28)+ "\t" + String.valueOf(velocity) + "\t"+String.valueOf(time)+ "\t";
                                
                                writer.println("Outgoing message to Client is : " + outputMessage+"\n");
                                
				dataOutput.writeUTF(outputMessage);
				dataOutput.flush();
			}
			

		}catch(Exception e){
                   System.out.println("Exception occurred at Server.");
                }
	}
}