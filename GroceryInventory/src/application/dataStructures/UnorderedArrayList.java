package application.dataStructures;

import java.util.Arrays;
import java.util.Iterator;

public class UnorderedArrayList<T> implements UnorderedListADT<T>, Iterable<T> {

	private final static int DEFAULT_CAPACITY = 100;
	private final static int NOT_FOUND = -1;
	protected int rear;
	protected T[] list;
	protected int modCount;

	/**
	 * Creates an empty list using the default capacity.
	 */
	public UnorderedArrayList() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty list using the specified capacity.
	 *
	 * @param initialCapacity the size of the array list
	 */
	@SuppressWarnings("unchecked")
	public UnorderedArrayList(int initialCapacity) {
		rear = 0;
		list = (T[]) (new Object[initialCapacity]);
		modCount = 0;
	}

	/**
	 * Removes and returns the specified element.
	 *
	 * @param element the element to be removed and returned from the list
	 * @return the removed elememt
	 * @throws ElementNotFoundException if the element is not in the list
	 */
	public T remove(T element) {
		T result;
		int index = find(element);
		if (index == NOT_FOUND)
			throw new ElementNotFoundException("ArrayList");
		result = list[index];
		rear--;
		// shift the appropriate elements
		for (int scan = index; scan < rear; scan++)
			list[scan] = list[scan + 1];
		list[rear] = null;
		modCount++;
		return result;
	}

	/**
	 * Returns the array index of the specified element, or the constant NOT_FOUND
	 * if it is not found.
	 *
	 * @param target the target element
	 * @return the index of the target element, or the NOT_FOUND constant
	 */
	private int find(T target) {
		int scan = 0;
		int result = NOT_FOUND;
		if (!isEmpty())
			while (result == NOT_FOUND && scan < rear)
				if (target.equals(list[scan]))
					result = scan;
				else
					scan++;
		return result;
	}

	/**
	 * Returns true if this list contains the specified element.
	 *
	 * @param target the target element
	 * @return true if the target is in the list, false otherwise
	 */
	public boolean contains(T target) {
		return (find(target) != NOT_FOUND);
	}

	/**
	 * Adds the specified element after the specified target element. Throws an
	 * ElementNotFoundException if the target is not found.
	 *
	 * @param element the element to be added after the target element
	 * @param target  the target that the element is to be added after
	 */
	public void addAfter(T element, T target) {
		if (size() == list.length)
			expandCapacity();
		int scan = 0;
		// find the insertion point
		while (scan < rear && !target.equals(list[scan]))
			scan++;
		if (scan == rear)
			throw new ElementNotFoundException("UnorderedList");
		scan++;
		// shift elements up one
		for (int shift = rear; shift > scan; shift--)
			list[shift] = list[shift - 1];
		// insert element
		list[scan] = element;
		rear++;
		modCount++;
	}

	public void addToFront(T element) {
		if (size() == list.length)
			expandCapacity();

		// shift elements to make room
		for (int scan = rear; scan > 0; scan--)
			list[scan] = list[scan - 1];

		list[0] = element;
		rear++;
	}

	public void addToRear(T element) {
		if (size() == list.length)
			expandCapacity();

		list[rear] = element;
		rear++;
	}

	private void expandCapacity() {
		list = Arrays.copyOf(list, list.length * 2);

	}

	@Override
	public T removeFirst() {
		if (rear == 0) {
            throw new EmptyCollectionException("list");
        }
        T result = list[0];
        rear--;
 
        for (int scan = 0; scan < rear; scan++) {
            list[scan] = list[scan + 1];
        }
        list[rear] = null;
 
        return result;
	}

	@Override
	public T removeLast() {
		if (rear == 0) {
            throw new EmptyCollectionException("list");
        }
        T result = list[rear - 1];
        rear--;
        list[rear] = null;
 
        return result;
	}

	@Override
	public T first() {
		return list[0];
	}

	@Override
	public T last() {
		return list[rear];
	}

	@Override
	public boolean isEmpty() {
		return rear==0;
	}

	@Override
	public int size() {
		return rear;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}
	
	@Override
	public String toString() {
		if (isEmpty()) {
            return "List: {}";
        }

        String text = "List: {";
        for (int i = 0; i < rear; i++) {
            text = text + list[i] +", ";
        }
        text = text + "}";
        return text;
	}
}
