package game;

import java.util.Random;

import list.AList;
import queue.CircularArrayQueue;
import queue.LinkedQueue;
import queue.QueueInterface;

public class Station {
	private static AList<Station> stationList = new AList<>(); // Static collection of every station that is created
																// Needed so the passengers that enqueue at a station
																// are able to pick a
																// destination
	private String name;
	private static final int QUEUE_INTERVAL = 60; // Seconds between new passengers arriving at station
	private static final int QUEUE_INTERVAL_LIMIT = 3; // How many potential random Passengers can queue in one interval
	private QueueInterface<Passenger> passengerQueue;
	private Random rand = new Random();
	private PassengerQueueThread queueThread;

	/**
	 * Create a new station with a given name
	 * 
	 * @param name String name of station
	 */
	public Station(String name) {
		this.name = name;
		passengerQueue = new CircularArrayQueue<>();
		Station.stationList.add(this);
		startPassengerQueue(); // Start running separate thread for continuously queuing new Passengers
	}

	/**
	 * @return static list of all stations that have been created
	 */
	public static Station[] getStationList() {
		return stationList.toArray(new Station[stationList.getLength()]);
	}

	public String getName() {
		return name;
	}

	public boolean isEmpty() {
		return passengerQueue.isEmpty();
	}

	/**
	 * Attempts to board all Passengers at station into bus Stops when bus is full
	 * or Station queue is empty Passengers who failed to board will rejoin waiting
	 * queue
	 * 
	 * @param bus
	 */
	public void boardPassengers(Bus bus) {
		QueueInterface<Passenger> tempQueue = new LinkedQueue<>();
		while (!passengerQueue.isEmpty()) {
			if (bus.isFull()) {
				System.out.println("Bus is full");
				break;
			}
			Passenger toBoard = passengerQueue.dequeue();

			boolean boarded = false;
			
			boarded = bus.boardPassenger(toBoard); // Passenger will only board if bus is going in direction of
														// their destination

			if (!boarded) { // Bus wasn't going in right direction
					System.out.println(toBoard.getName() + " will not board the bus, they are trying to get to "
							+ toBoard.getDestination().getName());
			
				tempQueue.enqueue(toBoard); // Enqueue Passengers who failed to board in temporary queue
			}
		}
		while (!tempQueue.isEmpty())
			enqueuePassenger(tempQueue.dequeue()); // Requeue passengers who failed to board back into station
															// queue 
	}

	private void enqueuePassenger(Passenger passenger) {
		passengerQueue.enqueue(passenger);
	}

	private void startPassengerQueue() {
		queueThread = new PassengerQueueThread();
		queueThread.start();
	}

	/**
	 * @author Jaden Reid 
	 * 		   Separate thread class for enqueueing passengers 
	 * 		   This could possibly cause race condition if passenger QUEUE_INTERVAL and bus
	 *         arrival times are the same 
	 *         Bus arrival would cause station to dequeue while this thread tries to enqueue new Passengers 
	 *         Would need mutual exclusion to fix
	 */
	private class PassengerQueueThread extends Thread {
		public PassengerQueueThread() {
		}

		@Override
		public void run() {
			try {
				while (true) {
					int randomPassngNum = rand.nextInt(QUEUE_INTERVAL_LIMIT + 1);
					for (int passNum = 0; passNum < randomPassngNum; passNum++) {
						Passenger newPassenger = new Passenger(Station.this); // Create new Passenger at this station to
																				// enqueue
						Station.this.enqueuePassenger(newPassenger);
					}
					Thread.sleep(QUEUE_INTERVAL * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
