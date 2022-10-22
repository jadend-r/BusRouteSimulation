package game;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Passenger {
	private String name = "Passenger ";
	private Station destination;
	private Station originStation;
	private Random rand = new Random();
	
	/**
	 * Create new passenger with a station of origin
	 * @param originStation Station that is enqueuing this passenger
	 */
	public Passenger(Station originStation) {
		this.originStation = originStation;
		name += UUID.randomUUID();
		
		ChooseDestination(); //Passenger picks a random destination Station
	}
	
	/**
	 * Passenger chooses random Station from static Station list to be their destination
	 */
	private void ChooseDestination() {
		Station[] stations = Station.getStationList();
		
		int chosenStationIdx = rand.nextInt(stations.length);
		destination = stations[chosenStationIdx];
	}
	
	public String getName() {
		return name;
	}
	
	public Station getDestination() {
		return destination;
	}
}
