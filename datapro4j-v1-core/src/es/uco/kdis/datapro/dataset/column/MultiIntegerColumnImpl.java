package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

import es.uco.kdis.datapro.datatypes.MultiDoubleValue;
import es.uco.kdis.datapro.datatypes.MultiIntegerValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.NullValue;

/**
 * This class represents the implementation of a multivalue column of integer values. It 
 * defines the actual implementation of the methods invoked from its abstraction. This 
 * class and its methods should not be directly invoked.
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
public class MultiIntegerColumnImpl extends MultiColumnImpl {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiIntegerColumnImpl(){	
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue) {
		try {
			if (oValue == null) {
				this.rgoValues.add(NullValue.getNullValue());
				return 1;
			}
			else if (oValue instanceof Integer){
				MultiIntegerValue oMultiValue = new MultiIntegerValue();
				oMultiValue.addValue((Integer)oValue);
				this.rgoValues.add(oMultiValue);
				return 1;
			}
			else if (oValue instanceof MultiIntegerValue || oValue instanceof InvalidValue) {
				this.rgoValues.add(oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Add a value at the specified index.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the value has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue, int iIndex) {
		try {
			if (oValue == null) {
				this.rgoValues.add(iIndex, NullValue.getNullValue());
				return 1;
			}
			else if (oValue instanceof Integer){
				MultiIntegerValue oMultiValue = new MultiIntegerValue();
				oMultiValue.addValue((Integer)oValue);
				this.rgoValues.add(iIndex, oMultiValue);
				return 1;
			}
			else if (oValue instanceof MultiIntegerValue || oValue instanceof InvalidValue) {
				this.rgoValues.add(iIndex, oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 **/
	@Override
	public int setValue(Object oValue, int iIndex) {
		try {
			if (oValue == null) {
				this.rgoValues.set(iIndex, NullValue.getNullValue());
				return 1;
			}
			else if(oValue instanceof Integer){
				MultiIntegerValue oMultiValue = new MultiIntegerValue();
				oMultiValue.addValue((Integer)oValue);
				this.rgoValues.set(iIndex, oMultiValue);
				return 1;
			}
			else if (oValue instanceof MultiIntegerValue || oValue instanceof InvalidValue) {
				this.rgoValues.set(iIndex, oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Add a value at the end of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addElementToMultivalue(Object oValue, int iIndex) {
		try {
			if (oValue instanceof Integer && this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).addValue((Integer) oValue);
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}	
	}

	/**
	 * Add a value at the specified position of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iInternalIndex The position index where the value has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addElementToMultivalue(Object oValue, int iIndex, int iInternalIndex) {
		try {
			if (oValue instanceof Integer && this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).addValue((Integer) oValue, iInternalIndex);
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}	
	}

	/**
	 * Add a list of elements at the end of the specified multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @return The number of values added.
	 @since 0.1
	 **/
	public int addAllElementsToMultivalue(List<Integer> rgoValues, int iIndex) {
		try {
			if (this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).addAllValues(rgoValues);
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Add a list of elements between two specified positions in the multivalue.
	 * @param rgoValues The list of values to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be added.
	 * @param iToIndex The position index of the last value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public int addElementsToMultivalue(List<Integer> rgoValues, int iIndex, int iFromIndex, int iToIndex) {
		try {
			if (this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).addValues(rgoValues, iFromIndex, iToIndex);
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Get the element stored between two positions in the multivalue sequence.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be retrieved.
	 * @param iToIndex The position index of the last value to be retrieved.
	 * @return A list containing the elements between the specified positions.
	 @since 0.2
	 **/
	public ArrayList<Integer> getElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		try{
			if(this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).getValues(iFromIndex,iToIndex);
			else
				return null;
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * Set a value at the specified position of a multivalue.
	 * @param oValue The new value.
	 * @param iIndex The position index of the multivalue to be modified (element in the column). 
	 * @param iInternalIndex The position index of the value to be modified.
	 * @return The number of values modified.
	 @since 0.1
	 **/
	public int setElementOfMultivalue(Object oValue, int iIndex, int iInternalIndex) {
		try {
			if (oValue instanceof Integer && this.rgoValues.get(iIndex) instanceof MultiIntegerValue)
				return ((MultiIntegerValue)this.rgoValues.get(iIndex)).setValue((Integer) oValue, iInternalIndex);
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	// CONVERSION METHODS

	/**
	 * Convert into a column with floating multivalues.
	 * @return MultiDoubleColumn with the corresponding floating values.
	 @since 0.1
	 **/
	public MultiDoubleColumn toMultiDouble(String sName){
		MultiDoubleColumn oResultColumn = new MultiDoubleColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;
		MultiDoubleValue oMultiValue;

		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null){
				oResultColumn.addValue(NullValue.getNullValue());
			}
			else if(oValue instanceof InvalidValue){
				oResultColumn.addValue(oValue);
			}
			else if (oValue instanceof MultiIntegerValue) {
				oMultiValue = ((MultiIntegerValue)oValue).toMultiDouble();
				oResultColumn.addValue(oMultiValue);
			}
		}
		return oResultColumn;
	}
}