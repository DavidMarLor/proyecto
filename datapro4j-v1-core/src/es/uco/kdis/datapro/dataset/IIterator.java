package es.uco.kdis.datapro.dataset;

import java.util.List;

/**
 * IIterator is the interface class for a iterator that access to every row/instance in a dataset.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Jose Maria Luna, MSc 		[jmluna@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (JRR, 0.2, September 2011) Performance, refactoring and designing issues. Improvements.
 * <li> (JTL, 0.1, May 2011) Initial version. Intrusion strategies.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.2
 *
 **/
public interface IIterator {

	/**
	 * Obtains the first row/instance of a dataset associated.
	 * @return The list of objects with the first row/instance.
	 @since 0.1
	 **/
	public List<Object> first();

	/**
	 * Increments <code>iRow</code> if there are more instances in 
	 * the dataset.
	 @since 0.1
	 **/
	public void next();

	/**
    @return TRUE if the dataset has no more instances to read. FALSE otherwise.
	@since 0.1
	**/
	public boolean isDone();

	/**
	 * Obtains the current row/instance of the dataset. This row/instance 
	 * is pointed by <code>iRow</code>.
     * @return The current instance as a list of objects, one value per column.
	 @since 0.1
	 **/
	public List<Object> currentInstance();
}