package es.uco.kdis.datapro.dataset.column;

import java.util.List;

/**
 * This abstract class represents the implementation of the different kinds of columns.
 * The purpose of this class is to do all the implementation apart from the abstraction. 
 * All the operations with the column will be done using the column implementation.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}<p>
 * <li>Jose Maria Luna, MSc 		[jmluna@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (JRR, 0.3, March 2012) 		setXValues and getXValues (for invalid values) remain only for serialization
 * 									purposes. Singleton objects of InvalidValue, MissingValue and NullValue are used instead. 
 * 									The methods setXValues() including the parameter bForceChange were removed.
 * <li> (JRR, 0.2, September 2011) 	Performance, refactoring and designing issues. Improvements.
 * <li> (JTL, 0.1, May 2011) 		Initial version. Intrusion strategies.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2, 0.3)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.3
 *
 **/
public abstract class ColumnImpl {
	
	/**
	 * <code>oMissingValue</code> is an object that 
	 * represents a missing value in the column.
	 @since 0.1
	 **/
	protected Object oMissingValue = null;

	/**
	 * <code>oNullValue</code> is an object that 
	 * represents a null value in the column.
	 @since 0.1
	 **/
	protected Object oNullValue = null;

	/**
	 * <code>oEmptyValue</code> is an object that 
	 * represents an empty value in the column.
	 @since 0.1
	 **/
	protected Object oEmptyValue = null;

	/**
	 * Get the object that represents the missing value in the column.
	 * @return The object representing the missing value.
	 @since 0.1
	 **/
	public Object getMissingValue() {
		return this.oMissingValue;
	}

	/**
	 * Set the object that represents a missing value in the column.
	 * @param oMissingValue The object representing a missing value.
	 @since 0.1
	 **/
	public void setMissingValue(Object oMissingValue) {
		this.oMissingValue = oMissingValue;
	}
	
	/**
	 * Get the object that represents the null value in the column.
	 * @return The object representing the null value.
	 @since 0.1
	 **/
	public Object getNullValue() {
		return this.oNullValue;
	}

	/**
	 * Set the object that represents a null value in the column.
	 * @param oNullValue The object representing a null value in the column.
	 @since 0.1
	 **/
	public void setNullValue(Object oNullValue) {
		this.oNullValue = oNullValue;
	}
	
	/**
	 * Get the object that represents the empty value in the column.
	 * @return The object representing the empty value.
	 @since 0.1
	 **/
	public Object getEmptyValue() {
		return this.oEmptyValue;
	}

	/**
	 * Set the object that represents an empty value in the column.
	 * @param oEmptyValue The object representing an empty value in the column.
	 @since 0.1
	 **/
	public void setEmptyValue(Object oEmptyValue) {
		this.oEmptyValue = oEmptyValue;
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addValue(Object oValue);

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added
	 * @param bForce If true, it forces the value to be added.
	 * @return The number of elements appended to the column.
	 * Note: By default, bForce is omitted. Otherwise, the 
	 * subclass should explicitly rewrite this method.
	 @since 0.1
	 **/
	public int addValue(Object oValue, boolean bForce) {
		return addValue(oValue);
	}

	/**
	 * Add a value at the specified index.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the value has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addValue(Object oValue, int iIndex);

	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 **/
	public abstract int setValue(Object oValue, int iIndex);

	/**
	 * Remove the value at the specified index.
	 * @param iIndex The position index to be removed.
	 @since 0.1
	 **/
	public abstract void removeValue(int iIndex);
	
	/**
	 * Append a list of values at the end of the column.
	 * @param rgoCol The list of values to be added.
	 * @return The number of values added.
	 **/
	public abstract int addAllValues(List<Object> rgoCol);

	/**
	 * Get the values in the column.
	 * @return All the values in the column as a list of objects.
	 @since 0.1
	 **/
	public abstract List<Object> getValues();

	/**
	 * Get the element stored at specified position.
	 * @param iPos The specified position.
	 * @return The element at <code>iPos</code>.
	 @since 0.1
	 **/
	public abstract Object getElement(int iPos);

	/**
	 * Get the number of elements in the column. 
	 * @return The number of elements comprised by the column.
	 @since 0.1
	 **/
	public abstract int getSize();

	/**
	 * Count the number of missing values in the column.
	 * @return The total missing values in the column; -1 if cannot be calculated.
	 * Note: To be refined by the sub-class.
	 @since 0.1
	 **/
	public int countMissingValues() {
		return -1;
	}

	/**
	 * Count the number of empty values in the column.
	 * @return The total empty values in the column; -1 if cannot be calculated.
	 * Note: To be refined by the sub-class.
	 @since 0.1
	 **/
	public int countEmptyValues() {
		return -1;
	}

	/**
	 * Count the number of null values in the column.
	 * @return The total null values in the column; -1 if cannot be calculated.
	 * Note: To be refined by the sub-class.
	 @since 0.1
	 **/
	public int countNullValues() {
		return -1;
	}

	/**
	 * Count the number of invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values in the column; -1 if cannot be calculated.
	 * Note: To be refined by the sub-class.
	 @since 0.1
	 **/
	public int countInvalidValues() {
		return -1;
	}
}