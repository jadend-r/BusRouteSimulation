package game;

import list.AList;

public class Route {
	private AList<Station> routeStations;
	
	/**
	 * Create a new Route with given sequential Stations
	 * @param stationsForRoute Station array of Stations on this Route
	 */
	public Route(Station[] stationsForRoute) {
		this.routeStations = new AList<>();
		for(Station station : stationsForRoute) {
			addStation(station);
		}
	}
	
	public Route() {
		this(null);
	}
	
	/**
	 * Add a Station to this Route
	 * @param station Station to add to Route
	 * @throws IllegalArgumentException if Station is already in Route
	 */
	public void addStation(Station station) {
		if(routeStations.contains(station))
			throw new IllegalArgumentException("This route cannot have duplicate destinations");
		routeStations.add(station);
	}
	
	public Station[] getStations() {
		return routeStations.toArray(new Station[routeStations.getLength()]);
	}
	
	public int getLength() {
		return routeStations.getLength();
	}
	
	/**
	 * Gets Station in this Route by index
	 * @param num index of Station in Route to retrieve
	 * @return Station at index in Route
	 * @throws IllegalArgumentException if num is out of bounds of Route
	 */
	public Station getStationByNumber(int num) {
		if(num >= routeStations.getLength() || num < 0)
			throw new IllegalArgumentException("Cannot get station with specified index");
		return routeStations.getEntry(num);
	}
	
	/**
	 * Gets the index of a Station in this Route
	 * @param station Station in this Route to get index of
	 * @return index of Station in route or -1 if Station not in Route
	 */
	public int getStationIndex(Station station) {
		for(int i = 0; i < routeStations.getLength(); i++) {
			if(routeStations.getEntry(i) == station) {
				return i;
			}
		}
		return -1;
	}
}
