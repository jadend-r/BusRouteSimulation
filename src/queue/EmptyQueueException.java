package queue;

class EmptyQueueException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5997101129288169132L;

	public EmptyQueueException() {
		super();
	}
	
	public EmptyQueueException(String info) {
		super(info);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
