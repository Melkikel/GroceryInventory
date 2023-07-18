package application.dataStructures;

public class CircularArrayQueue<T> implements QueueADT<T> {

	private final static int DEFAULT_CAPACITY = 100;
	private int front, rear, count;
	private T[] queue;

	/**
	 * Creates an empty queue using the specified capacity.
	 * 
	 * @param initialCapacity the initial size of the circular array queue
	 */
	@SuppressWarnings("unchecked")
	public CircularArrayQueue(int initialCapacity) {
		front = rear = count = 0;
		queue = (T[]) (new Object[initialCapacity]);
	}

	/**
	 * Creates an empty queue using the default capacity.
	 */
	public CircularArrayQueue() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Adds the specified element to the rear of this queue, expanding the capacity
	 * of the queue array if necessary.
	 * 
	 * @param element the element to add to the rear of the queue
	 */
	public void enqueue(T element) {
		if (size() == queue.length)
			expandCapacity();
		queue[rear] = element;
		rear = (rear + 1) % queue.length;
		count++;
	}

	/**
	 * Creates a new array to store the contents of this queue with twice the
	 * capacity of the old one.
	 */
	private void expandCapacity() {
		@SuppressWarnings("unchecked")
		T[] larger = (T[]) (new Object[queue.length * 2]);
		for (int scan = 0; scan < count; scan++) {
			larger[scan] = queue[front];
			front = (front + 1) % queue.length;
		}
		front = 0;
		rear = count;
		queue = larger;
	}

	/**
	 * Removes the element at the front of this queue and returns a reference to it.
	 * 
	 * @return the element removed from the front of the queue
	 * @throws EmptyCollectionException if the queue is empty
	 */
	public T dequeue() throws EmptyCollectionException {
		if (isEmpty())
			throw new EmptyCollectionException("queue");
		T result = queue[front];
		queue[front] = null;
		front = (front + 1) % queue.length;
		count--;
		return result;
	}

	@Override
	public T first() {
		if (isEmpty())
			throw new EmptyCollectionException("queue");
		return queue[front];
	}

	@Override
	public boolean isEmpty() {
		return rear <= 0;
	}

	@Override
	public int size() {
		return count;
	}
}
