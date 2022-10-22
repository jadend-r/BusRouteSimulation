package queue;

public interface QueueInterface<T> {
	public void enqueue(T newEntry);
	public T dequeue();
	public T getFront();
	public boolean isEmpty();
	public void clear();
	public int getSize();
}
