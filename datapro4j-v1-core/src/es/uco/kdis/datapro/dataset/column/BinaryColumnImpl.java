package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of a binary column. It defines
 * the actual implementation of the methods invoked from its abstraction.
 * This class and its methods should not be directly invoked.
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
 * <li>	(ARQ, 0.4, August 2015) The following method was modified to match with the specification: toCategorical
 * <li> (JRR, 0.3, March 2012) Invalid values are replaced by specific classes. Specific changes:
 *      <ul>
 *          <li>setXValue and getXValue still remain for serialization purposes only. Moved to ColumnImpl</li>
 *          <li>The following methods were adapted to use singleton invalid values instead: addValue, countXValues, toCategorical</li>
 *          <li>The following method was removed: setXValue
 *      </ul>
 * <li> (JRR|JML, 0.2, September 2011) Performance, refactoring and designing issues. Improvements.</li>
 * <li> (MOB, 0.1, June 2011) Initial version.</li>
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.4)
 @author Jose Maria Luna (JML, 0.2, 0.3)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 *
 **/
public class BinaryColumnImpl extends ColumnImpl {

	/**
	 * <code>rgoValues</code> are the column values.
	 @since 0.1
	 **/
	private ArrayList<Object> rgoValues = new ArrayList<Object>();

	/**
	 * Default constructor
	 @since 0.1
	 **/
	public BinaryColumnImpl() {
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
			if (oValue instanceof Boolean || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Boolean || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Boolean || oValue instanceof InvalidValue) {
				this.rgoValues.set(iIndex, oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Remove the value at the specified index.
	 * @param iIndex The position index to be removed.
	 * @throws IndexOutOfBoundsException
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
	 * @throws IndexOutOfBoundsException
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
	 * @return The total amount of missing values in the column.
	 @since 0.1
	 **/
	@Override
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
	 * @return The total amount of empty values in the column.
	 @since 0.1
	 **/
	@Override
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
	 * @return The total amount of null values in the column.
	 @since 0.1
	 **/
	@Override
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
	 * Count the number of invalid values (missing, null and 
	 * empty values) in the column.
	 * @return The total number of invalid values in the column.
	 @since 0.1
	 **/
	@Override
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

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column.
	 * @param sName The name of the column.
	 * @param sFalseCategory The category associated to the false binary value.
	 * @param sTrueCategory The category associated to the true binary value.
	 * @return CategoricalColumn with two categories (true,false), where values correspond to these categories.
	 * <p>Notes: 
	 * <ul>
	 * 		<li>If a value is a empty/missing value then a false value will be added.</li>
	 * 		<li>If a value is null, then a null value will be added.</li>
	 * </ul>
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(String sName, String sFalseCategory, String sTrueCategory){

		CategoricalColumn oResultColumn = new CategoricalColumn(sName);

		// Add the categories
		oResultColumn.addCategory(sFalseCategory);
		oResultColumn.addCategory(sTrueCategory);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);
			// TODO ARQ: the implementation now corresponds to the method specification (different treatment of missing/null/empty values)
			// Can oValue be equal to null?
			if(oValue == null || oValue instanceof NullValue)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof EmptyValue || oValue instanceof MissingValue) 
				oResultColumn.addValue(sFalseCategory);
			else{   /* It's not an invalid value */
				if (((Boolean)oValue).booleanValue())
					oResultColumn.addValue(sTrueCategory);
				else
					oResultColumn.addValue(sFalseCategory);
			}
		}
		return oResultColumn;
	}
}