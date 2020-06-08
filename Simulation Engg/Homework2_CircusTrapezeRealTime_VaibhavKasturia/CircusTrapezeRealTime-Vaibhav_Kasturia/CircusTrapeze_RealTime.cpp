/* 
 * File:   CircusTrapeze_RealTime.cpp
 * 
 * Author: Vaibhav Kasturia <kasturia at l3s.de>
 *
 * Circus Trapeze 
 * 
 * Created on December 21, 2017
 */

#include <iostream>
#include <pthread.h>
#include <signal.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>
#include <math.h>
#include "Euler_Functions.h"

using namespace std;

struct simulation_struct_type{
    int sim_mode;
    double sim_time;
    double frame_time;
    double sim_end_time;
}simulation_struct;

struct parameter_struct_type{
    double g;
    double length;
    double length_of_man;
    double end_time;
    double delta_time;
    double start_angle;
}parameters;

struct state_struct_type{
    double theta;
    double velocity;
    double time;
}states;


void do_step(parameter_struct_type* par, state_struct_type* state){
    //Calculation done using Runge Kutta Method
    states.theta += calc_range1(parameters.delta_time, states.velocity);
    states.velocity += calc_range2(parameters.delta_time, states.theta);
    states.time += parameters.delta_time;
    
}


void timer_handler (int signum)
{
    simulation_struct.sim_time +=simulation_struct.frame_time;
    do_step(&parameters, &states);
    cout<<states.time*10<<"\t\t\t\t\t\t\t"<<states.theta*360/6.28<<"\t\t\t"<< states.velocity<<"\t\t\t"<<"\n";
}


void initialise_simulation(parameter_struct_type* par, state_struct_type* state){
    
    parameters.g = 9.8;
    parameters.length = 5;
    parameters.length_of_man= 2.4;
    parameters.end_time = 2.16 * 3.14 * sqrt(length / g);
    parameters.delta_time= 0.1;
    parameters.start_angle= 55;
    
    states.theta = parameters.start_angle * 6.28 / 360;
    states.velocity = 0;
    states.time = 0;
    
}

void *simulation_function(void *ptr);

int main ()
{
    simulation_struct.sim_mode = 0;
    simulation_struct.sim_time = 0.0;
    simulation_struct.frame_time = 0.010;
    simulation_struct.sim_end_time = 10;
    pthread_t simulation_thread;
    
    int status;
    
    status = pthread_create(&simulation_thread, NULL, simulation_function, NULL);
    simulation_struct.sim_mode =1;
    while (simulation_struct.sim_time<simulation_struct.sim_end_time);
}

void *simulation_function(void *ptr){
    
    struct sigaction sa;
    struct itimerval timer;
    
    // Install timer_handler as the signal handler for SIGVTALRM. 
    memset (&sa, 0, sizeof (sa));
    sa.sa_handler = &timer_handler;
    sigaction (SIGVTALRM, &sa, NULL);
    
    // Configure the timer to expire after the given frame time, when initialization is done
    // Value that is fed is in microseconds

    timer.it_value.tv_sec = 0;
    timer.it_value.tv_usec = simulation_struct.frame_time*10000;
    
    //After first time frame, we repeat this after each time frame
    timer.it_interval.tv_sec = 0;
    timer.it_interval.tv_usec = simulation_struct.frame_time * 1000;
    
    //Start a virtual timer. 
    //Whenever process executes, countdown is done
    initialise_simulation(&parameters, &states);
    
    setitimer (ITIMER_VIRTUAL, &timer, NULL);
    return NULL;
}