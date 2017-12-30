package es.uco.kdis.datapro.datatypes;

/**
 * This class represents a missing value. 
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
public class MissingValue extends InvalidValue {

	/**
	 * <code>oSingleton</code> represents the missing value.
	 @since 0.1
	 **/
	private static MissingValue oSingleton = null;

	/**
	 * Default contructor.
	 @since 0.1
	 **/
	// Constructor is private
	private MissingValue() {
	}

	//****************
	// SINGLETON
	//****************

	/**
	 * Singleton constructor for missing value
	 * @return The generic missing value singleton instance
     @since 0.1
	 **/
	public static MissingValue getMissingValue() {
		if (oSingleton == null) {
			oSingleton = new MissingValue();
		}
		return oSingleton;
	}

}
