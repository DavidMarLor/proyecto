package es.uco.kdis.datapro.exception;

/**
 * This class represents the exception thrown when the file type is not png, jpg, jpeg or pdf.
 * 
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Aurora Ramirez Quesada 		[aramirez@uco.es]
 * <p><p>
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul> 
 * <li> (IMA|RPB, 0.1, May 2015)	Initial version. 
 * </ul>
 * <p>
 *
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.1
 *
 **/
public class InvalidFileTypeException extends RuntimeException {
	
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
    public InvalidFileTypeException(String sMessage) {
		System.out.println(sMessage);
	}
}
