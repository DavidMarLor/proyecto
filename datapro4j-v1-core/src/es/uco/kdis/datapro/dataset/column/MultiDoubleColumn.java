package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

/**
* This class represents the abstraction of a multivalue column of double values. It is the one
* that should be used by the programmer, since it hides the actual implementation of the column. 
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
public class MultiDoubleColumn extends MultiColumn {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiDoubleColumn () {
		super("", ColumnType.Multivalue);
		this.oImpl = new MultiDoubleColumnImpl();
	}
	
	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public MultiDoubleColumn (String sName) {
		super(sName, ColumnType.Multivalue);
		this.oImpl = new MultiDoubleColumnImpl();
	}
	
	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 * @param ctColumnType The type of the column
	 @since 0.1
	 **/
	public MultiDoubleColumn(String sName, ColumnType ctColumnType) {
		super(sName, ctColumnType);
		this.oImpl = new MultiDoubleColumnImpl();
	}

	/**
	 * Add a list of values at the end of the specified multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.1
	 * */
	public int addAllElementsToMultivalue(List<Double> rgoValues, int iIndex) {
		return ((MultiDoubleColumnImpl)this.oImpl).addAllElementsToMultivalue(rgoValues, iIndex);
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
	public int addElementsToMultivalue(List<Double> rgoValues, int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiDoubleColumnImpl)this.oImpl).addElementsToMultivalue(rgoValues, iIndex, iFromIndex, iToIndex);
	}
		
	/**
	 * Get the element stored between two positions in the multivalue.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be retrieved.
	 * @param iToIndex The position index of the last value to be retrieved.
	 * @return A list containing the elements between the specified positions.
	 @since 0.1
	 **/
	public ArrayList<Double> getElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		return ((MultiDoubleColumnImpl)this.oImpl).getElementsOfMultivalue(iIndex, iFromIndex, iToIndex);
	}
	
	// CONVERSION METHODS
	
	/**
	 * Convert into a column with multivalues in string format.
	 * @return MultiStringValueColumn with the corresponding string of numerical values.
	 @since 0.1
	 **/
	public MultiStringColumn toMultiString(){
		return ((MultiDoubleColumnImpl)this.oImpl).toMultiString(this.getName());
	}

	/**
	 * Convert into a column with multivalues in integer format, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return MultiIntegerValueColumn with the corresponding integer values.
	 @since 0.1
	 **/
	public MultiIntegerColumn toMultiInteger(boolean bRoundedValue){
		return ((MultiDoubleColumnImpl)this.oImpl).toMultiInteger(this.getName(), bRoundedValue);
	}
	
	/**
	 * Convert into a column with multivalues in long format, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return MultiLongValueColumn with the corresponding long values.
	 @since 0.1
	 **/
	public MultiLongColumn toMultiLong(boolean bRoundedValue){
		return ((MultiDoubleColumnImpl)this.oImpl).toMultiLong(this.getName(), bRoundedValue);
	}
}