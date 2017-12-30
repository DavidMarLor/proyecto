package es.uco.kdis.datapro.datatypes;

/**
 * This class represents an empty value. 
 * <p><b>NOTE:</b> Notice that both the columns and the dataset define their own invalid values. These values in the dataset are exclusively referred to 
 * those that are read and written from/to the dataset. However, missing values in columns refer exclusively to the scope of that column. This class 
 * specifically refer to the missing values, independently of how they are represented.
 * 
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
 * <li> (JRR, 0.1, March 2012) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 *
 **/
public class EmptyValue extends InvalidValue {
	
	/**
	 * <code>oSingleton</code> represents the empty value.
	 @since 0.1
	 **/
	private static EmptyValue oSingleton = null;

	/**
	 * Default contructor.
	 @since 0.1
	 **/
	// Constructor is private
	private EmptyValue() {
	}

	//****************
	// SINGLETON
	//****************
	
	/**
	 * Singleton constructor for empty value
	 * @return The generic empty value singleton instance
	 @since 0.1
	 **/
	public static EmptyValue getEmptyValue() {
		if (oSingleton == null) {
			oSingleton = new EmptyValue();
		}
		return oSingleton;
	}
}