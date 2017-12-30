package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

/**
* This class, the abstraction of a multivalue column of string values, is the one
* that should be used by the programmer, since it hides the actual implementation 
* of the column. 
* Even when the implementation changes, the abstraction must remain unaltered. 
*
* CONTACT INFO:
* <ul> 
* <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
* <p>{@link http://www.jrromero.net}
* <li>Aurora Ramirez Quesada, MSc 	[aramirez@uco.es]
* <p><p>
* Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
* {@link http://www.uco.es/grupos/kdis}
* </ul>
* <p>
* HISTORY:
* <ul>
* <li> (ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements. </li>
* <li> (IMA|RPB, 0.1, February 2014)	Initial version. </li>
* </ul>
* <p>
*
@author Aurora Ramirez Quesada (ARQ, 0.2)
@author Rocio Pinillos Bermudez (RPB, 0.1)
@author Ignacio Monferrer Altava (IMA, 0.1)
@version 0.2
*
**/
public class MultiStringColumn extends MultiColumn {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiStringColumn () {
		super("", ColumnType.Multivalue);
		this.oImpl = new MultiStringColumnImpl();
	}
	
	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public MultiStringColumn (String sName) {
		super(sName, ColumnType.Multivalue);
		this.oImpl = new MultiStringColumnImpl();
	}
	
	/**
	 * Parameterized Constructor.
	 * @param sName The name of the column.
	 * @param ctColumnType The type of the column
	 @since 0.1
	 **/
	public MultiStringColumn(String sName, ColumnType ctColumnType) {
		super(sName, ctColumnType);
		this.oImpl = new MultiStringColumnImpl();
	}

	/**
	 * Add a list of values at the end of the specified multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.1
	 **/
	public int addAllElementsToMultivalue(List<String> rgoValues, int iIndex) {
		return ((MultiStringColumnImpl)this.oImpl).addAllElementsToMultiValue(rgoValues, iIndex);
	}
	
	/**
	 * Add a list of values between two specified positions in a multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The first position index of the multivalue.
	 * @param iToIndex The last position index of the multivalue.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public int addElementsToMultivalue(List<String> rgoValues, int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiStringColumnImpl)this.oImpl).addElementsToMultivalue(rgoValues, iIndex, iFromIndex, iToIndex);
	}
	
	/**
	 * Get the element stored between two positions in the multivalue sequence.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be retrieved.
	 * @param iToIndex The position index of the last value to be retrieved.
	 * @return A list containing the elements between the specified positions.
	 @since 0.2
	 **/
	public ArrayList<String> getElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiStringColumnImpl)this.oImpl).getElementValues(iIndex, iFromIndex, iToIndex);
	}
	
	// CONVERSION METHODS
	
	/**
	 * Convert into a column with floating multivalues.
	 * If a string in the multivalue is not a numeric representable value, 
	 * an empty value will be added as the element in the column.
	 * @return MultiDoubleColumn with the numerical representation of the string values.
	 @since 0.1
	 **/
	public MultiDoubleColumn toMultiDouble(){
		return ((MultiStringColumnImpl)this.oImpl).toMultiDouble(this.getName());
	}
}