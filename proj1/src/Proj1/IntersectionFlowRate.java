package Proj1;

/**
 * @name IntersectionFlowRate
 * @author Rachael Birky
 * @Section 01
 * @date 02.20.2014
 *
 * @description A static class that stores the flow rates of four traffic lanes
 * 				(northbound, southbound, eastbound, westbound)
 * 
 * @instancevariable:	eastFlowRateCars: the number of cars per minute added to
 * 						the eastbound queue
 * @instancevariable:	eastFlowRateTrucks: the number of trucks per minute added to
 * 						the eastbound queue
 * 
 * @instancevariable:	westFlowRateCars: the number of cars per minute added to
 * 						the westbound queue
 * @instancevariable:	westFlowRateCars: the number of cars per minute added to
 * 						the westbound queue
 * 
 * @instancevariable:	northFlowRateCars: the number of cars per minute added to
 * 						the northbound queue
 * @instancevariable:	northFlowRateCars: the number of cars per minute added to
 * 						the northbound queue
 * 
 * @instancevariable:	southFlowRateCars: the number of cars per minute added to
 * 						the southbound queue
 * @instancevariable:	southFlowRateCars: the number of cars per minute added to
 * 						the southbound queue

 */
public class IntersectionFlowRate {

	private static int eastFlowRateCars;
	private static int eastFlowRateTrucks;
	
	private static int westFlowRateCars;
	private static int westFlowRateTrucks;
	
	private static int northFlowRateCars;
	private static int northFlowRateTrucks;
	
	private static int southFlowRateCars;
	private static int southFlowRateTrucks;
	
	/**
	 * @name setFlowRate
	 * @description instantiates the given with traffic lane flow rate variables
	 * 				with the appropriate flow rates
	 * 
	 * @param direction: the traffic lane to be set
	 * @param carFlowRate: an integer primitive representing the number of cars per minute
	 * 				added to the given queue (traffic lane)
	 * @param truckFlowRate: an integer primitive representing the number of trucks per minute
	 * 				added to the given queue (traffic lane)
	 */
	public static void setFlowRate(char direction, int carFlowRate, int truckFlowRate){
		switch (direction){
		case 'N':
			northFlowRateCars = carFlowRate;
			northFlowRateTrucks = truckFlowRate;
			break;
		case 'S':
			southFlowRateCars = carFlowRate;
			southFlowRateTrucks = truckFlowRate;
			break;
		case 'E':
			eastFlowRateCars = carFlowRate;
			eastFlowRateTrucks = truckFlowRate;
			break;
		case 'W':
			westFlowRateCars = carFlowRate;
			westFlowRateTrucks = truckFlowRate;
			break;
		default:
			System.out.println("Incorrect Character.");
			break;
		}
	}
	
	/**
	 * @name getFlowRate
	 * @description instantiates the given with traffic lane flow rate variables
	 * 				with the appropriate flow rates
	 * 
	 * @param direction: the traffic lane flow rate to be accessed
	 * @param type: a character primitive representing the type of vehicle
	 * 				flow rate to be accessed (car or truck)
	 * 
	 * @return northFlowRateCars, northflowRateTrucks,
	 * 				southFlowRateCars, southFlowRateTrucks
	 * 				eastFlowRateCars, eastFlowRateTrucks
	 * 				westFlowRateCars, westFlowRateTrucks
	 * 			flow rates representing the number of cars or trucks
	 * 			added to the specified queue per minute
	 */
	public static int getFlowRate(char direction, char type){
		switch (direction){
		case 'N':
			if (type == 'c') return northFlowRateCars;
			else if (type == 't') return northFlowRateTrucks;
			else return 0;
		case 'S':
			if (type == 'c') return southFlowRateCars;
			else if (type == 't') return southFlowRateTrucks;
			else return 0;
		case 'E':
			if (type == 'c') return eastFlowRateCars;
			else if (type == 't') return eastFlowRateTrucks;
			else return 0;
		case 'W':
			if (type == 'c') return westFlowRateCars;
			else if (type == 't') return westFlowRateTrucks;
			else return 0;
		default:
			System.out.println("Incorrect Character.");
			return 0;
		}
	}
}
