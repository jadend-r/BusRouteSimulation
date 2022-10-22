package queue;

public class CircularArrayQueue<T> implements QueueInterface<T> {
	private int frontIndex;
	private int backIndex;
	private int capacity;
	private static final int DEFAULT_CAPACITY = 10;
	private int numOfEntries;
	
	private T[] queueArray;
	
	@SuppressWarnings("unchecked")
	public CircularArrayQueue(int capacity) {
		this.capacity = capacity;
		queueArray = (T[]) new Object[capacity];
		frontIndex = 0;
		backIndex = capacity - 1;
		numOfEntries = 0;
	}
	
	public CircularArrayQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	private void ensureCapacity() {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity * 2];
		for(int i = 0; i < capacity; i++) {
			temp[i] = queueArray[(frontIndex + i) % capacity];
		}
		frontIndex = 0;
		backIndex = capacity - 2;
		capacity *= 2;
		queueArray = temp;
	}
	
	private boolean isFull() {
		return (backIndex + 2) % capacity == frontIndex;
	}

	@Override
	public void enqueue(T newEntry) {
		backIndex = (backIndex + 1) % capacity;
		queueArray[backIndex] = newEntry;
		numOfEntries++;
		
		if(isFull())
			ensureCapacity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T dequeue() {
		if(isEmpty())
			throw new EmptyQueueException();
		T front = queueArray[frontIndex];
		queueArray[frontIndex] = null;
		frontIndex = (frontIndex + 1) % capacity;
		
		if(isEmpty()) {
			frontIndex = 0;
			if(capacity > DEFAULT_CAPACITY) {
				capacity = DEFAULT_CAPACITY;
				queueArray = (T[]) new Object[capacity];
			}
			backIndex = capacity - 1;
		}
		numOfEntries--;
		return front;
	}

	@Override
	public T getFront() {
		if(isEmpty())
			throw new EmptyQueueException();
		return queueArray[frontIndex];
	}

	@Override
	public boolean isEmpty() {
		return (backIndex + 1) % capacity == frontIndex;
	}

	@Override
	public void clear() {
		for(int i = 0; i < numOfEntries; i++) {
			queueArray[i] = null;
		}
		numOfEntries = 0;
	}

	@Override
	public int getSize() {
		return numOfEntries;
	}

}
