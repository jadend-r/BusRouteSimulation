package list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

public class AList<T> implements ListInterface<T>, Iterable<T> {
	private T[] list;
	private int numOfEntries;
	private int capacity;
	private static final int DEFAULT_CAPACITY = 10;
	private static final int MAX_CAPACITY = 10000;
	
	@SuppressWarnings("unchecked")
	public AList(int capacity) {
		if(capacity < DEFAULT_CAPACITY) {
			capacity = DEFAULT_CAPACITY;
		}
		checkCapacity();
		list = (T[]) new Object[capacity];
		this.capacity = capacity;
		this.numOfEntries = 0;
	}
	
	public AList() {
		this(DEFAULT_CAPACITY);
	}
	
	private void checkCapacity() {
		if(capacity > MAX_CAPACITY)
			throw new RejectedExecutionException("Capacity too big");
	}
	
	private void ensureCapacity() {
		if(isFull())
			capacity *= 2;
			list = Arrays.copyOf(list, capacity);
	}
	
	private boolean isFull() {
		return numOfEntries == capacity;
	}

	@Override
	public void add(T newEntry) {	
		ensureCapacity();
		list[numOfEntries++] = newEntry;
	}

	@Override
	public void add(int newPosition, T newEntry) {
		if(newPosition < 0 || newPosition > numOfEntries)
			throw new IndexOutOfBoundsException("Invalid position to insert");
		makeRoom(newPosition);
		list[newPosition] = newEntry;
		ensureCapacity();
	}

	private void makeRoom(int position) {
		for(int idx = numOfEntries; idx > position; idx--) {
			list[idx] = list[idx - 1];
		}
	}

	@Override
	public T remove(int givenPosition) {
		if(givenPosition < 0 || givenPosition > numOfEntries)
			throw new IndexOutOfBoundsException("Invalid position to remove");
		T outData = list[givenPosition];
		fillGap(givenPosition);
		return outData;
	}

	private void fillGap(int givenPosition) {
		for(int i = givenPosition; i < numOfEntries; i++) {
			list[i] = list[i + 1];
		}
		numOfEntries--;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(list, numOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public T[] toArray(T[] array) {
		T[] arr;
		if(array.length >= numOfEntries) {
			arr = array;
		} else {
			arr = Arrays.copyOf(array, numOfEntries);
		}
		for(int i = 0; i < arr.length; i++) {
			arr[i] = (T)list[i];
		}
		return arr;
	}

	@Override
	public boolean remove(T anEntry) {
		for(int i = 0; i < numOfEntries; i++) {
			if(anEntry.equals(list[i])) {
				fillGap(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public T replace(int givenPosition, T newEntry) {
		if(givenPosition < 0 || givenPosition > numOfEntries)
			throw new IndexOutOfBoundsException("Invalid position to replace");
		T outData = list[givenPosition];
		list[givenPosition] = newEntry;
		return outData;
	}

	@Override
	public boolean isEmpty() {
		return numOfEntries == 0;
	}

	@Override
	public boolean contains(T anEntry) {
		for(int i = 0; i < numOfEntries; i++) {
			if(list[i].equals(anEntry)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		for(int i = 0; i < numOfEntries; i++) {
			list[i] = null;
		}
		numOfEntries = 0;
	}

	@Override
	public T getEntry(int givenPosition) {
		if(givenPosition < 0 || givenPosition > numOfEntries)
			throw new IndexOutOfBoundsException("Invalid position to get");
		return list[givenPosition];
	}

	@Override
	public int getLength() {
		return numOfEntries;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ListIteratorForAList();
	}
	
	public Iterator<T> getIterator(){
		return iterator();
	}
	
	
	private class ListIteratorForAList implements ListIterator<T>{
		private int cursor;
		private boolean isRemoveOrSetLegal = false;
		private static final int NEXT = 1;
		private static final int PREV = 0;
		private int move = NEXT;
		
		public ListIteratorForAList() {
			cursor = 0;
		}
		
		@Override
		public boolean hasNext() {
			return cursor < numOfEntries;
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			T outData = list[cursor++];
			isRemoveOrSetLegal = true;
			move = NEXT;
			return outData;
		}

		@Override
		public boolean hasPrevious() {
			return cursor > 0;
		}

		@Override
		public T previous() {
			if(!hasPrevious())
				throw new NoSuchElementException();
			T outData = list[--cursor];
			move = PREV;
			return outData;
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor-1;
		}

		@Override
		public void remove() {
			if(!isRemoveOrSetLegal)
				throw new IllegalStateException();
			if(move == NEXT) 
				AList.this.remove(--cursor);
			else
				AList.this.remove(cursor);
			
			isRemoveOrSetLegal = false;
		}

		@Override
		public void set(T e) {
			if(!isRemoveOrSetLegal)
				throw new IllegalStateException();
			list[cursor-move] = e;
			
		}

		@Override
		public void add(T e) {
			if(move==NEXT) {
				AList.this.add(cursor++, e);
			} else {
				AList.this.add(cursor, e);
			}
			
		}
		
	}



	
	
	
	
	
	
	
	
	
	
	
	
	

}
