package es.uco.kdis.datapro.exception;

/**
 * This class represent an exception. It is thrown when the format specification for a CSV Dataset is not valid.
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
 * <li> (JTL, 0.1) Initial version.
 * </ul>
 * <p>
 *
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.1
 *
 **/
public class IllegalFormatSpecificationException extends Exception {

	/**
	 * Serial version
	 @since 0.1
	 **/
	private static final long serialVersionUID = 1L;

	/**
	 * Exception constructor
	 * @param sMessage The exception message to be shown.
     @since 0.1
	 **/
	public IllegalFormatSpecificationException(String sMessage) {
		System.out.println(sMessage);
	}

}
