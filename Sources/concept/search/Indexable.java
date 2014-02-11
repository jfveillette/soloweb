package concept.search;

/**
 * Implemented by classes that know how to create an indexable record.
 */

public interface Indexable {

	/**
	 * @return A generic record that describes this object
	 */
	public IndexRecord indexRecord();
}