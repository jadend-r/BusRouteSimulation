package bag;

import java.util.Arrays;

import utils.Node;

public class LinkedBag<T> implements BagInterface<T> {
	private Node<T> head;
	private int numOfEntries;
	
	@Override
	public int getCurrentSize() {
		return numOfEntries;
	}

	@Override
	public boolean isEmpty() {
		if(numOfEntries == 0 ^ head == null) {
			System.out.println("Chain error");
		}
		return numOfEntries == 0;
	}

	@Override
	public boolean add(Object newEntry) {
		@SuppressWarnings("unchecked")
		Node<T> newData = new Node<>((T)newEntry, head);
		head = newData;
		numOfEntries++;
		return true;
	}

	@Override
	public boolean remove(Object anEntry) {
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			if (curr.getData().equals(anEntry)){
				curr.setData(head.getData());
				head = head.getNext();
				numOfEntries--;
				return true;
			}
		}
		return false;
	}

	@Override
	public T remove() {
		T outData = head.getData();
		head = head.getNext();
		numOfEntries--;
		return outData;
	}

	@Override
	public void clear() {
		head = null;
		numOfEntries = 0;
	}

	@Override
	public boolean contains(Object anEntry) {
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			if (curr.getData().equals(anEntry)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int getFrequencyOf(Object anEntry) {
		int count = 0;
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			if (curr.getData().equals(anEntry)){
				count++;
			}
		}
		return count;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[numOfEntries];
		int index = 0;
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			result[index] = curr.getData();
			index++;
		}
		return result;
	}
	
	public LinkedBag<T> copy(){
		LinkedBag<T> cp = new LinkedBag<>();
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			cp.add(curr.getData());
		}
		return cp;
	}
	
	public LinkedBag<T> intersection(LinkedBag<T> other){
		LinkedBag<T> un = new LinkedBag<>();
		other = other.copy();
		for(Node<T> curr = head; curr != null; curr = curr.getNext()) {
			if(other.remove(curr.getData())){
				un.add(curr.getData());
			}
		}
		return un;
	}

	@Override
	public T[] toArray(T[] a) {
		T[] arr;
		if(a.length >= numOfEntries) {
			arr = a;
		} else {
			arr = Arrays.copyOf(a, numOfEntries);
		}
		int idx = 0;
		for(Node <T> curr = head; curr != null; curr = curr.getNext()) {
			arr[idx++] = (T)curr.getData();
		}
		return arr;
	}

}
