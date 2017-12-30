package es.uco.kdis.datapro.exception;

/**
 * This class represents the exception thrown when a category is not found.
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
 * <li> (JRR, 0.1) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 *
 **/
public class NoSuchCategoryException extends Exception {
	
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
	public NoSuchCategoryException(String sMessage) {
		System.out.println(sMessage);
	}
}
