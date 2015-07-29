package Proj1;

/**
 * @name Vehicle
 * @author Rachael Birky
 * @Section 01
 * @date 02.20.2014
 *
 * @description A class representing a vehicle.
 * 
 * @instancevariable:	type: a car or truck represented
 * 						by the character primitives 'c' or 't', respectively.
 * @instancevariable:	timeEntered: an integer primitive representing the time
 * 						the vehicle entered the queue (traffic lane)
 * @instancevariable:	timeExited: an integer primitive representing the
 *			 			time the vehicle left the queue (traffic lane)
 * 
 * @instancevariable:	timeRequired: an integer primitive representing the amount
 * 						of time required for the vehicle to exit the queue (cross
 * 						the intersection). A value of 1 for a car, and 2 for a truck 
 * 
 * @instancevariable:	headTime: an integer primitive representing how
 * 						long the vehicle was first in the queue
 */
public class Vehicle {

	private char type;
	private int timeEntered;
	private int timeExited;
	private int timeRequired;
	private int headTime;
	
	/**
	 * @name Vehicle
	 * @description Constructor creates a new vehicle with
	 * 				a timeEntered of 0 and a type of empty 
	 * 
	 * @param none
	 * @return none
	 */
	public Vehicle(){
		this.type = '\u0000';
		this.timeEntered = 0;
	}
	
	/**
	 * @name Vehicle
	 * @description Constructor that creates a new vehicle
	 * 				with the given timeEntered and type. If
	 * 				the vehicle is a car, it's timeRequired is
	 * 				set to 1; if a truck, to 2
	 * 
	 * @param aType: a character primitive representing the
	 * 			vehicle type (c for car, t for truck)
	 * @param aTime: an integer primitive representing the time
	 * 			the vehicle entered the queue (traffic lane)
	 * @return none
	 */
	public Vehicle(char aType, int aTime){
		this.type = aType;
		this.timeEntered = aTime;
		
		if (this.type == 't') this.timeRequired = 2;
		else this.timeRequired = 1;
	}
	
	/**
	 * @name setType
	 * @description Setter method to change the Vehicle type
	 * 
	 * @param aType: a character primitive representing the
	 * 			vehicle type (c for car, t for truck)
	 * @return none
	 */
	public void setType(char aType){
		this.type = aType;
	}
	
	/**
	 * @name setTimeEntered
	 * @description Setter method to change the Vehicle's
	 * 				timeEntered
	 * 
	 * @param aTime: an integer primitive representing the time
	 * 				the vehicle entered the queue (traffic lane)
	 * @return none
	 */
	public void setTimeEntered(int aTime){
		this.timeEntered = aTime;
	}
	
	/**
	 * @name setTimeExited
	 * @description Setter method to set the Vehicle's
	 * 				time of departure from the queue (traffic lane)
	 * 
	 * @param aTime: an integer primitive representing the time
	 * 				the vehicle left the queue
	 * @return none
	 */
	public void setTimeExited(int aTime){
		this.timeExited = aTime;
	}
	
	/**
	 * @name setHeadTime
	 * @description Setter method to set the amount of
	 * 				time the vehicle was at the head of the queue
	 * 				(front of the traffic lane)
	 * 
	 * @param aTime: an integer primitive representing the amount
	 * 				of time a vehicle was first in the traffic lane
	 * @return none
	 */
	public void setHeadTime(int aTime){
		this.headTime = aTime;
	}
	
	/**
	 * @name getType
	 * @description Getter method to access the vehicle's type
	 * 
	 * @param none
	 * @return this.type: a character primitive representing the
	 * 			vehicle type (c for car, t for truck)
	 */
	public char getType(){
		return this.type;
	}
	
	/**
	 * @name getTimeEntered
	 * @description Getter method to access the vehicle's time entered
	 * 
	 * @param none
	 * @return this.timeEntered: an integer primitive representing the
	 * 				time the vehicle entered the queue (traffic lane)
	 */
	public int getTimeEntered(){
		return this.timeEntered;
	}
	
	/**
	 * @name getTimeExited
	 * @description Getter method to access the vehicle's timeExited
	 * 
	 * @param none
	 * @return this.timeExited: an integer primitive representing the
	 * 			time the vehicle left the queue (traffic lane)
	 */
	public int getTimeExited(){
		return this.timeExited;
	}
	
	/**
	 * @name getTimeRequired
	 * @description Getter method to access the time required
	 * 				for a vehicle to exit the queue (cross the intersection)
	 * 
	 * @param none
	 * @return this.timeRequired: an integer primitive representing the amount
	 * 			of time required for the vehicle to exit the queue (cross
	 * 			the intersection). A value of 1 for a car, and 2 for a truck 
	 */
	public int getTimeRequired(){
		return this.timeRequired;
	}
	
	/**
	 * @name getWaitTime
	 * @description Getter method to access the amount of time
	 * 				the vehicle waited in the queue
	 * 
	 * @param none
	 * @return timeExited - timeEntered: the amount of time
	 * 				the vehicle was in the queue
	 */
	public int getWaitTime(){
		return (timeExited - timeEntered);
	}
	
	/**
	 * @name getHeadTime
	 * @description Getter method to access the amount of time
	 * 				the vehicle was at the head of the queue
	 * 				(first in the traffic lane)
	 * 
	 * @param none
	 * @return this.headTime: an integer primitive representing how
	 * 				long the vehicle was first in the queue
	 */
	public int getHeadTime(){
		return this.headTime;
	}
}
