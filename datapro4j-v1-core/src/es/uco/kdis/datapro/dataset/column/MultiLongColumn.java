package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

/**
* This class, the abstraction of a multivalue column of long values, is the one
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
* <li> (ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements.</li> 
* <li> (IMA|RPB, 0.1, February 2014)	Initial version.</li>
* </ul>
* <p>
*
@author Aurora Ramirez Quesada (ARQ, 0.2)
@author Rocio Pinillos Bermudez (RPB, 0.1)
@author Ignacio Monferrer Altava (IMA, 0.1)
@version 0.2
*
**/
public class MultiLongColumn extends MultiColumn {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiLongColumn () {
		super("", ColumnType.Multivalue);
		this.oImpl = new MultiLongColumnImpl();
	}
	
	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public MultiLongColumn (String sName) {
		super(sName, ColumnType.Multivalue);
		this.oImpl = new MultiLongColumnImpl();
	}
	
	/**
	 * Parameterized Constructor.
	 * @param sName The name of the column.
	 * @param ctColumnType The type of the column
	 @since 0.1
	 **/
	public MultiLongColumn(String sName, ColumnType ctColumnType) {
		super(sName, ctColumnType);
		this.oImpl = new MultiLongColumnImpl();
	}
	
	/**
	 *  Add a list of values at the end of the specified multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.1
	 **/
	public int addAllElementsToMultivalue(List<Long> rgoValues, int iIndex) {
		return ((MultiLongColumnImpl)this.oImpl).addAllElementsToMultivalue(rgoValues, iIndex);
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
	public int addElementsToMultivalue(List<Long> rgoValues, int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiLongColumnImpl)this.oImpl).addElementsToMultivalue(rgoValues, iIndex, iFromIndex, iToIndex);
	}
		
	/**
	 * Get the element stored between two positions in the multivalue sequence.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be retrieved.
	 * @param iToIndex The position index of the last value to be retrieved.
	 * @return A list containing the elements between the specified positions.
	 @since 0.2
	 **/
	public ArrayList<Long> getElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiLongColumnImpl)this.oImpl).getElementsOfMultivalue(iIndex, iFromIndex, iToIndex);
	}
	
	// CONVERSION METHODS
	
	/**
	 * Convert into a column with floating multivalues.
	 * @return MultiDoubleColumn where each value is converted to floating number.
	 @since 0.1
	 **/
	public MultiDoubleColumn toMultiDouble(){
		return ((MultiLongColumnImpl)this.oImpl).toMultiDouble(this.getName());
	}
	 
	/**
	 * Convert into a column with integer multivalues.
	 * @return MultiIntegerColumn where each value is converted to an integer number.
	 @since 0.1
	 **/
	public MultiIntegerColumn toMultiInteger(){
		return ((MultiLongColumnImpl)this.oImpl).toMultiInteger(this.getName());
	}
}