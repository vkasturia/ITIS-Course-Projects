/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trapezeclient;

/**
 *
 * @author Vaibhav Kasturia <kasturia at l3s.de>
 * 
 * Client class to provide the server with theta, velocity and time values
 */
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
          
class TrapezeClient{
    
    double theta ;
    double velocity =0;
    double time = 0;
    static int count =0;

    public TrapezeClient(double theta, double vel) {
     this.theta = theta;
     this.velocity = vel;
     this.time = 0;
    }
    
     void set_theta(double theta){
        this.theta = theta;
    }
     
    void set_velocity(double vel){
        velocity =vel;
    }
    
    void set_time (double time){
        this.time = time;
    }   
    
    
	public static void main(String[] args) {
		try{
                    TrapezeClient Client = new TrapezeClient(55, 0);
                    
                    String host = "localhost";
                    String IncomingMessage;
                    String OutgoingMessage;

                    //Server host and port
                    Socket s = new Socket(InetAddress.getByName(host),1411); 
			
                    DataInputStream dataInput = new DataInputStream(s.getInputStream());
                    DataOutputStream dataOutput = new DataOutputStream(s.getOutputStream());
                        
                    OutgoingMessage= String.valueOf(Client.theta)+ "\t" + String.valueOf(Client.velocity) + "\t" +String.valueOf(Client.time);
                    
                    PrintWriter writer = new PrintWriter("./ClientOutput.txt");
                    
                    writer.println("Angle(Theta)\tVelocity\tTime");
			while(count<1000){
				count++;
                                dataOutput.writeUTF(OutgoingMessage);
                               
                                IncomingMessage= (dataInput.readUTF());                                
                                String[] data = IncomingMessage.split("\t");
                                
                                Client.set_theta(Double.parseDouble(data[0]));
                                Client.set_velocity(Double.parseDouble(data[1]));
                                Client.set_time(Double.parseDouble(data[2]));
                                
                                
                                writer.println(IncomingMessage);
                                
				OutgoingMessage =IncomingMessage;
                                dataOutput.flush();
			}
                        writer.close();
		}catch(Exception e){
                   System.out.println("Exception occured at Client.");
                }
                
	}
}


