package application.dataStructures;

public class ElementNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
     * Sets up this exception with an appropriate message.
     * @param collection the name of the collection
     */
    public ElementNotFoundException(String collection)
    {
        super("The " + collection + " does not contain the targeted element.");
    }
}
