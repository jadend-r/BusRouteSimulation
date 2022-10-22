package queue;

import utils.Node;

public class LinkedQueue<T> implements QueueInterface<T> {
	private Node<T> frontNode;
	private Node<T> backNode;
	private int numOfEntries = 0;
	
	public LinkedQueue() {
		frontNode = backNode = null;
	}
	
	@Override
	public void enqueue(T newEntry) {
		Node<T> toAdd = new Node<>(newEntry);
		if(isEmpty())
			backNode = frontNode = toAdd;
		backNode.setNext(toAdd);
		backNode = toAdd;
		numOfEntries++;
	}

	@Override
	public T dequeue() {
		if(isEmpty())
			throw new EmptyQueueException();
		T outData = frontNode.getData();
		frontNode = frontNode.getNext();
		if(frontNode == null) {
			backNode = null;
		}
		numOfEntries--;
		return outData;
	}

	@Override
	public T getFront() {
		if(isEmpty())
			throw new EmptyQueueException();
		return frontNode.getData();
	}

	@Override
	public boolean isEmpty() {
		return numOfEntries == 0;
	}

	@Override
	public void clear() {
		frontNode = backNode = null;
		numOfEntries = 0;
	}
	
	@Override
	public int getSize() {
		return numOfEntries;
	}

}
