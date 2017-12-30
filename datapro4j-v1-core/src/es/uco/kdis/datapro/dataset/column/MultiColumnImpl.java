package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.MultiValue;
import es.uco.kdis.datapro.datatypes.NullValue;

/**
 * This class represents the implementation of a multivalue column. It defines the actual 
 * implementation of the methods invoked from its abstraction. This class and its methods 
 * should not be directly invoked.
 * <p>
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
 * <li>	(ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements.</li>
 * <li> (IMA|RPB, 0.1, February 2014) 	Initial version.</li>
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 *
 **/
public abstract class MultiColumnImpl extends ColumnImpl {

	/**
	 * <code>rgoValues</code> is the set of the column values.
	 @since 0.1
	 **/
	protected ArrayList<Object> rgoValues = new ArrayList<Object>();

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiColumnImpl(){	
	}

	/**
	 * Remove the value at the specified index.
	 * @param iIndex The position index to be removed.
	 @since 0.1
	 **/
	@Override
	public void removeValue(int iIndex) {
		this.rgoValues.remove(iIndex);
	}

	/**
	 * Append a list of values at the end of the column.
	 * @param rgoCol The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addAllValues(List<Object> rgoCol) {
		int iCounter = 0;
		int iSize = rgoCol.size();
		for(int i = 0; i < iSize; i++)
			iCounter += this.addValue(rgoCol.get(i));
		return iCounter;
	}

	/**
	 * Get the values in the column.
	 * @return All the values in the column as a list of objects.
	 @since 0.1
	 **/
	@Override
	public List<Object> getValues() {
		return this.rgoValues;
	}

	/**
	 * Get the element stored at specified position.
	 * @param iPos The specified position.
	 * @return The element at <code>iPos</code>.
	 @since 0.1
	 **/
	@Override
	public Object getElement(int iPos) {
		return this.rgoValues.get(iPos);
	}

	/**
	 * Get the number of elements in the column. 
	 * @return The number of elements comprised by the column.
	 @since 0.1
	 **/
	@Override
	public int getSize() {
		return this.rgoValues.size();
	}
	
	/**
	 * Count the number of missing values in the column.
	 * @return The total missing values in the column.
	 @since 0.2
	 **/
	public int countMissingValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof MissingValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of empty values in the column.
	 * @return The total empty values in the column.
	 @since 0.2
	 **/
	public int countEmptyValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof EmptyValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of null values in the column.
	 * @return The total null values in the column.
	 @since 0.2
	 **/
	public int countNullValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof NullValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values in the column.
	 @since 0.2
	 **/
	public int countInvalidValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof InvalidValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Add a value at the end of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.2
	 **/
	public abstract int addElementToMultivalue(Object oValue, int iIndex);

	/**
	 * Add a value at the specified position of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iInternalIndex The position index of the value to be added.
	 * @return The number of values added.
	 @since 0.2
	 **/
	public abstract int addElementToMultivalue(Object oValue, int iIndex, int iInternalIndex);

	/**
	 * Get the element stored at the specified position of a multivalue sequence.
	 * @param iIndex The index of the multivalue queried.
	 * @param iInternalIndex The index (inside the multivalue) of the value to be retrieved.
	 * @return The element queried.
	 @since 0.1
	 **/
	public Object getElementOfMultivalue(int iIndex, int iInternalIndex) {
		return ((MultiValue<?>)this.rgoValues.get(iIndex)).getValue(iInternalIndex);
	}

	/**
	 * Set a value at the specified position of a multivalue.
	 * @param oValue The new value.
	 * @param iIndex The position index of the multivalue to be modified (element in the column). 
	 * @param iInternalIndex The position index of the value to be modified.
	 * @return The number of values modified.
	 @since 0.2
	 **/
	public abstract int setElementOfMultivalue(Object oValue, int iIndex, int iInternalIndex);

	/**
	 * Get the size of a multivalue sequence.
	 * @param iIndex The index of the multivalue.
	 * @return The number of elements in the multivalue.
	 @since 0.1
	 **/
	public int getMultivalueSize(int iIndex) {
		try{
			if(this.rgoValues.get(iIndex) instanceof MultiValue<?>){
				return ((MultiValue<?>)this.rgoValues.get(iIndex)).getSize();
			}
			else // Invalid value
				return -1;
		}catch(Exception e){
			return -1;
		}
	}

	/**
	 * Remove the element stored at the specified position of the multivalue.
	 * @param iIndex The index of the multivalue.
	 * @param iInternalIndex The index (inside the multivalue) of the value to be removed.
	 * @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public void removeElementOfMultivalue(int iIndex, int iInternalIndex) {
		((MultiValue<?>)this.rgoValues.get(iIndex)).removeValue(iInternalIndex);
	}

	/**
	 * Remove the elements stored between two positions in the specified multivalue.
	 * @param iIndex The index of the multivalue.
	 * @param iFromIndex The index (inside the multivalue) of the first value to be removed.
	 * @param iToIndex The index (inside the multivalue) of the last value to be removed.
	 * @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public void removeElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		((MultiValue<?>)this.rgoValues.get(iIndex)).removeValues(iFromIndex,iToIndex);
	}

	
}