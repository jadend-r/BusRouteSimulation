package bag;

public interface BagInterface <T> {
	public int getCurrentSize();
	
	public boolean isEmpty();
	
	public boolean add(T newEntry);
	
	public boolean remove(T anEntry);
	
	public T remove();
	
	public void clear();
	
	public boolean contains(T anEntry);
	
	public int getFrequencyOf(T anEntry);
	
	public Object[] toArray();
	
	public T[] toArray(T[] a);
}
