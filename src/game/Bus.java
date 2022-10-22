package game;

import bag.BagInterface;
import bag.LinkedBag;

public class Bus {
	private static int busNumber = 0;
	private int MAX_CAPACITY = 60;
	private int numOfPassengers;
	private int currentStationIndex;
	private BagInterface<Passenger> passengerBag;
	private Route busRoute;
	private String busName = "Bus ";
	private boolean startingFromTerminal;
	
	public static int TRAVEL_DELAY = 10000; // Miliseconds of how long bus takes to travel between stations
	
	/**
	 * Create a new Bus that follows a given route
	 * @param route Route that this Bus follows
	 * @param startingFromTerminal boolean true: Bus starts from end of Route, false: Bus starts from beginning of Route
	 */
	public Bus(Route route, boolean startingFromTerminal) {
		busRoute = route;
		passengerBag = new LinkedBag<>();
		numOfPassengers = 0;
		busName += busNumber++;
		this.startingFromTerminal = startingFromTerminal;
		
		if(startingFromTerminal) {
			currentStationIndex = route.getLength() - 1;
		} else {
			currentStationIndex = 0;
		}
		
		//Board passengers at bus's origin station
		startPassengerBoardingAtStation(route.getStationByNumber(currentStationIndex));
	}
	
	public boolean isFull() {
		return numOfPassengers == MAX_CAPACITY;
	}
	
	public boolean isEmpty() {
		return numOfPassengers == 0;
	}
	
	/**
	 * Attempts to deboard a given passenger from the bus
	 * @param passenger Passenger to deboard from the bus
	 */
	public void deboardPassenger(Passenger passenger) {
		boolean removed = passengerBag.remove(passenger);
		if(removed)
			numOfPassengers--;
	}
	
	/**
	 * Finds all Passengers currently on bus with a matching destination and deboards them
	 * @param station Station to deboard bus passengers at
	 * @throws UnsupportedOperationException if bus is empty
	 */
	public void deboardPassengers(Station station) {
		if(isEmpty())
			throw new UnsupportedOperationException("Cannot deboard an empty bus");
		//System.out.println("Passenger size: " + Integer.toString(passengerBag.getCurrentSize()));
		Passenger[] passengers = passengerBag.toArray(new Passenger[passengerBag.getCurrentSize()]);
		for(int i = 0; i < passengers.length; i++) {
			if(passengers[i].getDestination() == station){
				deboardPassenger(passengers[i]);
				System.out.println(passengers[i].getName() + " has deboarded " + busName + " at station: " + station.getName());
			}
		}
	}

	/**
	 * Attempts to board a given passenger if their destination is reachable in the direction the bus is currently going
	 * @param toBoard Passenger that is attempting to board
	 * @return true: successful board, false: passenger cannot board because their destination is in the opposite direction
	 * @throws IllegalArgumentException if the bus is full
	 * @throws IllegalArgumentException if the Passenger is already on the bus
	 */
	public boolean boardPassenger(Passenger toBoard) {
		if(isFull())
			throw new IllegalArgumentException("Cannot board a full bus: " + busName);
		if(passengerBag.contains(toBoard))
			throw new IllegalArgumentException("This passenger cannot be boarded as they are already on the bus");
		
		int destinationIndex = busRoute.getStationIndex(toBoard.getDestination());
		if(startingFromTerminal) {
			if(destinationIndex > currentStationIndex) //Our destination index has to be less than the current index because the bus is going 
				return false;                                //back down the route
		} else {
			if(destinationIndex < currentStationIndex) //Our destination index has to be more than the current index because the bus is going 
				return false;                                //Forwards
		}
		System.out.println(toBoard.getName() + " is now boarding bus: " + getName() + " from station: " + busRoute.getStationByNumber(currentStationIndex).getName() + " to station: " + toBoard.getDestination().getName());
		passengerBag.add(toBoard);
		numOfPassengers++;
		return true;
	}
	
	/**
	 * Invokes given Station to begin boarding its passengers onto this bus
	 * @param station Station to board passengers from onto bus
	 */
	public void startPassengerBoardingAtStation(Station station) {
		station.boardPassengers(this);
	}
	
	/**
	 * Advances bus to the next station en route
	 * If bus has traveled past terminal station on either end, bus turns around and goes back en route in opposite direction
	 * Deboards Passengers that have a matching Station destination 
	 * Starts boarding of Passengers at the arrived Station
	 */
	public void advanceBus() {
		System.out.println("\nAdvancing bus " + busName + " to next station");
		if(startingFromTerminal) {
			currentStationIndex--;
		} else {
			currentStationIndex++;
		}
		if(currentStationIndex == busRoute.getLength()){ //Bus has gone past terminal stop
			currentStationIndex = busRoute.getLength() - 1;
			startingFromTerminal = true;
		} else if (currentStationIndex == -1) {
			currentStationIndex = 0;
			startingFromTerminal = false;
		}
		Station arrivedStation = busRoute.getStationByNumber(currentStationIndex);
		
		System.out.println("Arrived at station: " + arrivedStation.getName() + " \nDestination: " + (startingFromTerminal ? busRoute.getStationByNumber(0).getName() : busRoute.getStationByNumber(busRoute.getLength() - 1).getName()) + "\n");
		
		//Deboard passengers that have a destination of our new station
		if(!isEmpty())
			deboardPassengers(arrivedStation);
		//Board passengers at new station
		if(!arrivedStation.isEmpty())
			startPassengerBoardingAtStation(arrivedStation);
		else
			System.out.println("\nThere are no passengers waiting to board at this station.");
	}
	
	public String getName() {
		return busName;
	}
}
