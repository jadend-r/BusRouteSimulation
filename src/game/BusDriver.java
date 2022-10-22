package game;

public class BusDriver {

	public static void main(String[] args) {
		Station LongWood = new Station("Longwood Medical Area");
		Station Mfa = new Station("Museum of Fine Arts");
		Station Northeastern = new Station("Northeastern");
		Station Boylston = new Station("Boylston");
		
		Route route1 = new Route(new Station[] {LongWood, Mfa, Northeastern, Boylston});
		
		try {
			Thread.sleep(2000); //Wait 2 seconds before starting busses en route
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Bus bus1 = new Bus(route1, false);
		Bus bus2 = new Bus(route1, true);
		
		/**
		 * Cannot safely advance both busses at the same time (in seperate threads) without mutual exclusion
		 * Must advance one at a time
		 */
		while(true) {
			bus1.advanceBus();
			bus2.advanceBus();
			try {
				Thread.sleep(Bus.TRAVEL_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
