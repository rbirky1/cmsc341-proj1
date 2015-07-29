package Proj1;

import java.io.*;
import java.util.*;

/**
 * @name TrafficSim
 * @author Rachael Birky
 * @Section 01
 * @date 02.21.2014
 *
 * @description A class that simulates the flow of a four way traffic light
 * 				intersection given an input file of traffic lane flow rates by
 * 				printing the state of the intersection at each simulated second
 * 				to the console.
 * 
 * 				North/South
 *				a.	Primary, meaning we want this to be green as much as possible.
 *					If East/West does not have traffic, then North/South will STAY green
 *				b.	Will always be green together
 *				c.	Minimally green for 30 seconds each time it turns green
 *				d.	There is no maximum value for how long it stays green
 *				e.	The simulation will ALWAYS start with North/South being green
 *				
 *				East/West
 *				a.	Maximally green for 30 seconds
 *				b.	Minimally green for 10 seconds
 *				c.	Will turn red early if no traffic on either East/West side
 *
 * @instancevariable	northbound, southbound, eastbound, westbound: queues representing
 * 						the four lanes of traffic
 * @instancevariable	results: a LinkedList of vehicles that have successfully exited the intersection
 * 
 * @instancevariable	time: an integer primitive representing the simulated time in seconds
 * 
 * @instancevariable	NStime: an integer primitive representing the amount of time, in simulated seconds,
 * 						that the north and south lanes have been activated
 * @instancevariable	EWtime: an integer primitive representing the amount of time, in simulated seconds,
 * 						that the east and west lanes have been activated
 * 
 * @instancevariable	EW_MAX: a constant integer primitive representing the maximum time in simulated 
 * 						seconds that the east and west lanes can be active
 * @instancevariable	EW_MIN: a constant integer primitive representing the maximum time in simulated 
 * 						seconds that the east and west lanes must be active
 * @instancevariable	NS_MIN: a constant integer primitive representing the maximum time in simulated 
 * 						seconds that the north and south lanes must be active
 * 
 * @instancevariable	MINUTE: a constant double primitive representing the value of a minute in
 * 						simulated seconds for this specific program
 * @instancevariable	SIM_TIME: a constant integer primitive representing the amount of time
 * 						the simulation runs in simulated seconds (2 minutes = 121 second including the
 * 						zeroth second 
 * 
 * @instancevariable	NSactive: a boolean value that stores whether or not the north and south
 * 						lanes are active
 * @instancevariable	EWactive: a boolean value that stores whether or not the east and west
 * 						lanes are active
 *  
 */
public class TrafficSim {
	
	Scanner infile;
	
	private Queue<Vehicle> northbound;
	private Queue<Vehicle> southbound;
	private Queue<Vehicle> eastbound;
	private Queue<Vehicle> westbound;
	private LinkedList<Vehicle> results;
	
	private int time;
	
	private int NStime;
	private int EWtime;
	
	private int EW_MAX = 30;
	private int EW_MIN = 10;
	private int NS_MIN = 30;
	
	private double MINUTE = 60.0;
	private int SIM_TIME = 121;
	
	private boolean NSactive = true;
	private boolean EWactive = false;

	/**
	 * @name TrafficSim
	 * @description Constructor method. This method records the data
	 * 				from the input file and runs the simulation accordingly
	 * 
	 * @param aFile: the input file to be read and parsed
	 */
	public TrafficSim(String aFile){
		
		//Initialize Queues
		northbound = new java.util.LinkedList<Vehicle>();
		southbound = new java.util.LinkedList<Vehicle>();
		eastbound = new java.util.LinkedList<Vehicle>();
		westbound = new java.util.LinkedList<Vehicle>();
		
		//Initialize Custom LinkedList
		results = new LinkedList<Vehicle>();
		
		//Read and record input file information
		readFromFile(aFile);
		
		//initialize times
		time = 0;
		NStime = 0;
		EWtime = 0;		
		
		//Add first two cars for each direction for time 0
		for (int i=0; i<2; i++){
			addVehicle('N', new Vehicle('c', time));
			addVehicle('S', new Vehicle('c', time));
			addVehicle('W', new Vehicle('c', time));
			addVehicle('E', new Vehicle('c', time));
		}
		
		northbound.element().setHeadTime(time);
		southbound.element().setHeadTime(time);
		eastbound.element().setHeadTime(time);
		westbound.element().setHeadTime(time);
		
		//Print initial board
		printBoard();

		time++;
		
		//Loop 120 times; increment time; print board
		while(time < SIM_TIME) {
				
			//Calculate Lanes to be emptied
			if (EWtime >= EW_MAX){
				System.out.println("Change Light; EW max reached.");
				activateNorthSouth();
			}
			else if ((hasTraffic(eastbound) || hasTraffic(westbound))){
				if (NStime >= NS_MIN){
					System.out.println("Light has to change. Met minimum length green, other side (EW) is waiting.");
					activateEastWest();
				}
				else
					activateNorthSouth();
			}
			else if ((hasTraffic(northbound) || hasTraffic(southbound))){
				if (EWtime >= EW_MIN){
					activateNorthSouth();
				}
				else
					activateEastWest();
			}
			else
				activateNorthSouth();

			
			//Calculate when to add cars and add appropriate number of cars
			addVehicles();
			
			//Print board and increment time
			printBoard();
			time++;
		}
		
		printResults();
	}
	
	/**
	 * @name readFromFile
	 * @description Reads the given file and parses it,
	 * 				storing the values direction, carFlowRate and
	 * 				truckFlowRate in that order
	 * @param aFile: the provided file to be read
	 * @reutrn none
	 */
	public void readFromFile(String aFile){
		try {
			infile = new Scanner(new FileReader(aFile));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		for (int i=0; i<4; i++){
			String line = infile.nextLine();
			
			StringTokenizer tokenizer = new StringTokenizer(line);
			
			char direction = tokenizer.nextToken().charAt(0);
			int carFlowRate = Integer.parseInt(tokenizer.nextToken());
			int truckFlowRate = Integer.parseInt(tokenizer.nextToken());
			
			IntersectionFlowRate.setFlowRate(direction, carFlowRate, truckFlowRate);
		}
	}
	
	/**
	 * @name addVehicle
	 * @description Adds the given Vehicle to the provided queue
	 * 
	 * @param direction: a character primitive representing the queue
	 * @param x: the vehicle object to be added
	 * @return none
	 */
	public void addVehicle(char direction, Vehicle x){
		
		switch (direction){
		case 'N':
			northbound.add(x);
			break;
		case 'S':
			southbound.add(x);
			break;
		case 'E':
			eastbound.add(x);
			break;
		case 'W':
			westbound.add(x);
			break;
		default:
			System.out.println("Incorrect Character.");
			break;
		}
	}

	/**
	 * @name addVehicles
	 * @description Calculates and adds the appropriate number and 
	 * 				type of vehicles, by accesses the current simulated time 
	 * 				in seconds and the flow rate of each lane
	 * @param none
	 * @return none
	 */
	public void addVehicles(){
		//NORTH
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('N', 'c')) == 0.0){
			addVehicle('N', new Vehicle('c', time));
			if (northbound.size()==1) northbound.element().setHeadTime(time);
		}
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('N', 't')) == 0.0){
			addVehicle('N', new Vehicle('t', time));
			if (northbound.size()==1) northbound.element().setHeadTime(time);
		}
		
		//SOUTH
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('S', 'c')) == 0.0){
			addVehicle('S', new Vehicle('c', time));
			if (southbound.size()==1) southbound.element().setHeadTime(time);
		}
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('S', 't')) == 0.0){
			addVehicle('S', new Vehicle('t', time));
			if (southbound.size()==1) southbound.element().setHeadTime(time);
		}
		
		//EAST
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('E', 'c')) == 0.0){
			addVehicle('E', new Vehicle('c', time));
			if (eastbound.size()==1) eastbound.element().setHeadTime(time);
		}
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('E', 't')) == 0.0){
			addVehicle('E', new Vehicle('t', time));
			if (eastbound.size()==1) eastbound.element().setHeadTime(time);
		}
		
		//WEST
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('W', 'c')) == 0.0){
			addVehicle('W', new Vehicle('c', time));
			if (westbound.size()==1) westbound.element().setHeadTime(time);
		}
		if (time%(MINUTE/IntersectionFlowRate.getFlowRate('W', 't')) == 0.0){
			addVehicle('W', new Vehicle('t', time));
			if (westbound.size()==1) westbound.element().setHeadTime(time);
		}
	}
	
	/**
	 * @name hasTraffic
	 * @description Checks whether there are items (traffic) in the given queue
	 * 
	 * @param aQueue: the queue (traffic lane) to be checked
	 * @return false if the queue is empty, true otherwise
	 */
	public boolean hasTraffic(Queue<Vehicle> aQueue){
		return !aQueue.isEmpty();
	}
	
	/**
	 * @name activateNorthSouth
	 * @description "Activates" the north and south traffic lanes
	 * 				by removing the head vehicles according to their
	 * 				required time.
	 * 				If these lanes were previously active, their time
	 * 				is increments; if not, their time is set to 1.
	 * 				EWactive is set to false and NStime is set to true,
	 * 				indicating which queues (lanes) are currently open
	 * @param none
	 * @return none
	 */
	public void activateNorthSouth(){
		EWactive = false;
		
		if (NSactive)
			NStime++;
		else{
			NSactive = true;
			NStime = 1;
		}
		
		//NORTH
		if (northbound.peek() != null){
			Vehicle current = northbound.peek();
			if (time - current.getHeadTime() >= current.getTimeRequired()){
					Vehicle temp = northbound.remove();
					temp.setTimeExited(time);
					if (northbound.peek() != null)
						northbound.element().setHeadTime(time);
					results.addNode(temp);
				}
		}
		
		//SOUTH
		if (southbound.peek() != null){
			Vehicle current = southbound.peek();
			if (time - current.getHeadTime() >= current.getTimeRequired()){
					Vehicle temp = southbound.remove();
					temp.setTimeExited(time);
					if (southbound.peek() != null)
							southbound.element().setHeadTime(time);
					results.addNode(temp);
				}
		}
		
		NSactive = true;

	}
	
	/**
	 * @name activateEastWest
	 * @description "Activates" the east and west traffic lanes
	 * 				by removing the head vehicles according to their
	 * 				required time.
	 * 				If these lanes were previously active, their time
	 * 				is increments; if not, their time is set to 1.
	 * 				NSactive is set to false and EWtime is set to true,
	 * 				indicating which queues (lanes) are currently open
	 * @param none
	 * @return none
	 */
	public void activateEastWest(){
		NSactive = false;
		
		if (EWactive)
			EWtime++;
		else{
			EWactive = true;
			EWtime = 1;
		}
		
		//EAST
		if (eastbound.peek() != null){
			Vehicle current = eastbound.peek();
			if (time - current.getHeadTime() >= current.getTimeRequired()){
					Vehicle temp = eastbound.remove();
					temp.setTimeExited(time);
					if (eastbound.peek() != null)
						eastbound.element().setHeadTime(time);
					results.addNode(temp);
				}
		}
		
		//WEST
		if (westbound.peek() != null){
			Vehicle current = westbound.peek();
			if (time - current.getHeadTime() >= current.getTimeRequired()){
					Vehicle temp = westbound.remove();
					temp.setTimeExited(time);
					if (westbound.peek() != null)
						westbound.element().setHeadTime(time);
					results.addNode(temp);
				}
		}
		
		EWactive = true;

	}

	/**
	 * @name printBoad
	 * @description Prints the current state of the "intersection".
	 * 				The first vehicle in each queue is represented by its
	 * 				type character, and each following vehicle is
	 * 				represented by an 'x'.
	 *				The queues are arranged according to an aerial view,
	 *				and each lane is represented by an acronym and current vehicle count 
	 * @param none
	 * @return none
	 */
	public void printBoard(){
		
		System.out.println();
		
		//First line; size of southbound
		System.out.println("\tSB\t"+southbound.size());

		//Copy Queues into Arrays
		Vehicle[] southboundArray =southbound.toArray(new Vehicle[6]);
		Vehicle[] eastboundArray = eastbound.toArray(new Vehicle[6]);
		Vehicle[] westboundArray = westbound.toArray(new Vehicle[6]);
		Vehicle[] northboundArray =northbound.toArray(new Vehicle[6]);
		
		//use switch statement...?
		//Reverse ordered southbound
		//East bound
		for (int i=5; i>=0; i--){
			if (i>1){
				if (southboundArray[i]!=null)
					System.out.print("\n\tx");
				else
					System.out.print("\n");
			}

			else if (i==1){
				if (southboundArray[i]!=null)
					System.out.print("\nEB\tx");
				else
					System.out.print("EB");
			}
			
			else if (i==0){
				if (southboundArray[i]!=null)
					System.out.print("\n "+eastbound.size()+"\t"+southbound.element().getType());
				else
					System.out.print("\n "+eastbound.size());
			}

		}
		
		System.out.println();
		System.out.print("  ");
		
		//Middle row of characters
		//Reverse ordered eastbound; space; ordered westbound
		for (int i=5; i>=0; i--){
			if (eastboundArray[i]!=null){
				if (i==0)
					System.out.print(eastboundArray[i].getType());
				else
					System.out.print("x");
			}
			else System.out.print(" ");
		}
		System.out.print(" ");
		
		for (int i=0; i<6; i++){
			if (westboundArray[i]!=null){
				if (i==0)
					System.out.print(westboundArray[i].getType());
				else
					System.out.print("x");
			}
			else System.out.print(" ");
		}

		//Print ordered northbound
		//Change WB count to right of northbound items
		for (int i=0; i<6; i++){
			if (i>1){
				if (northboundArray[i]!=null)
					System.out.print("\n\tx");
				else
					System.out.print("\n");
			}

			else if (i==0){
				if (northboundArray[i]!=null)
					System.out.print("\n\t"+northbound.element().getType()+"\tWB");
				else
					System.out.print("\n\t\tWB");
			}
			
			else if (i==1){
				if (northboundArray[i]!=null)
					System.out.print("\n\tx\t "+westbound.size());
				else
					System.out.print("\n\t\t "+westbound.size());
			}

		}
		//Last line; size of northbound
		System.out.println("\n\n\tNB\t"+northbound.size());
		
		System.out.println();
		System.out.println("at clock: " + time);
		System.out.println("------------------");
	}
	
	/**
	 * @name getNumCars
	 * @description Iterates through the vehicles that exited the
	 * 				intersection and counts and returns the number of cars.
	 * @param none
	 * @return numCars: the number of cars that exited the intersection
	 */
	public int getNumCars(){
		int numCars = 0;
		for(Vehicle c : results){		
			if(c.getType() == 'c')
				numCars++;
		}
		return numCars;
	}
	
	/**
	 * @name getNumTrucks
	 * @description Iterates through the vehicles that exited the
	 * 				intersection and counts and returns the number of trucks.
	 * @param none
	 * @return numTrucks: the number of trucks that exited the intersection
	 */
	public int getNumTrucks(){
		int numTrucks = 0;
		for(Vehicle t : results){		
			if(t.getType() == 't')
				numTrucks++;
		}
		return numTrucks;
	}
	
	/**
	 * @name getAverageWaitTime
	 * @description Iterates through the vehicles that exited the
	 * 				intersection and adds the times each waited in the queue.
	 * 				Divides this total time by the total number of vehicles
	 * 				to calculate and return the average time each vehicle waited.
	 * @param none
	 * @return totalWaitTime/results.length(): the average time
	 * 				each vehicle waited in its respective queue
	 */
	public float getAverageWaitTime(){
		float totalWaitTime = 0;
		for(Vehicle v : results){		
				totalWaitTime+=v.getWaitTime();
		}
		return (totalWaitTime/results.length());
	}
	
	/**
	 * @name getNumCars
	 * @description Iterates through the vehicles that exited the
	 * 				intersection and counts and returns the number of cars.
	 * @param none
	 * @return numCars: the number of cars that exited the intersection
	 */
	public void printResults(){
		System.out.println("The final results are: ");
		System.out.println("The number of vehicles that passed through the intersection is: "+results.length());
		System.out.println("The number of cars that passed through the intersection is: " +getNumCars());
		System.out.println("The number of trucks that passed through the intersection is: " +getNumTrucks());
		System.out.println("The average wait time for this intersection is: " +getAverageWaitTime());
		
	}
	
	
	public static void main(String[] args){
		TrafficSim x = new TrafficSim("input2.txt");
	}
}
