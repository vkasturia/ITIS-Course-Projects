/* 
 * File:   Euler_Functions.h
 * 
 * Author: Vaibhav Kasturia <kasturia at l3s.de>
 *
 * Created on December 21, 2017
 */

#include <stdio.h>
#include <math.h>

// Constants : Acceleration due to gravity and length of rope
double g = 9.8;
double length = 5;


// Function to calculate theta 
double calc_function1(double time, double velocity){
    return velocity*time;
}

// Function to calculate Velocity (in loop iteration) using Runge Kutta Method
double calc_function2(double time, double theta){
    return -g/length*sin(theta)*time;
}

//Function used in Calculating parameters for finding next theta for pendulum using Runge Kutta Method.
double calc_range1(double time, double velocity){
    double h = time/2;
    double k1= calc_function1(time, velocity);
    double k2 = calc_function1((time+(h/2)), (velocity+h/2*(k1)));
    double k3 = calc_function1((time+(h/2)), (velocity+h/2*(k2)));
    double k4 = calc_function1((time+(h)), (velocity+h*(k3)));
    
    double val = h*(k1 + 2*k2 + 2*k3 + k4)/6;
    return val;
    
}

//Function used in Calculating parameters for finding next velocity for pendulum
double calc_range2(double time, double theta){
    double h = time/2;
    double k1= calc_function2(time, theta);
    double k2 = calc_function2((time+(h/2)), (theta+h/2*(k1)));
    double k3 = calc_function2((time+(h/2)), (theta+h/2*(k2)));
    double k4 = calc_function2((time+(h)), (theta+h*(k3)));
    
    double val = (h/6*(k1 + 2*k2 + 2*k3 + k4));
    return val;
}

