package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represent the concept of a column of a Dataset.
 * Create the concept of a column in the Dataset stores all the important information
 * of the column (Metadata).
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
 * <li> (JRR, 0.4, March 2012) 		The following methods has been created at the superclass level,
 * 									and removed from the subclasses: countXXXValues, getAllValues 
 * <li> (JRR, 0.3, September 2011) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.2, May 2011) 		Included attribute 'force' to the addValue method.
 * <li> (JTL, 0.1, June 2012) 		Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.3, 0.4)
 @author Mario Orozco Borrego (MOB, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.4
 *
 **/
public abstract class ColumnAbstraction {

	/**
	 * <code>sName</code> represents the name of the column.
	 @since 0.1
	 **/
	protected String sName;

	/**
	 * <code>ctColumnType</code> represents the column type.
	 @see ColumnType
	 @since 0.1
	 **/
	protected ColumnType ctColumnType;

	/**
	 * <code>oImpl</code> maintains a reference to the column implementation.
	 @since 0.1
	 **/
	protected ColumnImpl oImpl;

	/**
	 * Parameterized Constructor.
	 * @param sName The Name of the column.
	 * @param ctColumnType The Type of the column.
	 @since 0.1
	 **/
	public ColumnAbstraction(String sName, ColumnType ctColumnType) {
		this.sName = sName;
		this.ctColumnType = ctColumnType;
	}

	/**
	 * Get the column name.
	 * @return Name of the column.
	@since 0.1
	 **/
	public String getName() {
		return this.sName;
	}

	/**
	 * Set the name of the column.
	 * @param sName New name of the column.
	 @since 0.1
	 **/
	public void setName(String sName) {
		this.sName = sName;
	}

	/**
	 * Get the type of the column.
	 @return Type of the column.
	 @since 0.1
	 **/
	public ColumnType getType() {
		return this.ctColumnType;
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added.
	 * @return The number of values added.
	@since 0.1
	 **/
	public int addValue(Object oValue) {
		return this.oImpl.addValue(oValue);
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The object to be added.
	 * @param bForce If true, it forces the value to be added.
	 * @return The number of values successfully added.
	 @since 0.1
	 **/
	public int addValue(Object oValue, boolean bForce) {
		return this.oImpl.addValue(oValue, bForce);
	}

	/**
	 * Add a value at the specified index.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the object has to be added.
	 * @return The number of values successfully added.
	@since 0.1
	 **/
	public int addValue(Object oValue, int iIndex) {
		return this.oImpl.addValue(oValue, iIndex);
	}

	/**
	 * Set a value at the specified index.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the value has to be set.
	 * @return The number of values successfully set.
	 @since 0.1
	 **/
	public int setValue(Object oValue, int iIndex) {
		return this.oImpl.setValue(oValue, iIndex);
	}

	/**
	 * Remove a value at the specified index.
	 * @param iIndex The index to be removed.
	 * @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public void removeValue(int iIndex) {
		this.oImpl.removeValue(iIndex);
	}

	/**
	 * Append a list of values at the end of the column.
	 * @param rgoCol The list of values to be added.
	 @since 0.1
	 **/
	public void addAllValues(List<Object> rgoCol) {
		this.oImpl.addAllValues((ArrayList<Object>) rgoCol);
	}

	/**
	 * Get the element stored at the specified position.
	 * @param iPos Position where the value is queried.
	 * @return The element at <code>iPos</code>.
	@since 0.1
	 **/
	public Object getElement(int iPos) {
		return this.oImpl.getElement(iPos);
	}

	/**
	 * Get the size of the column.
	 * @return Number of elements of the column.
	@since 0.1
	 **/
	public int getSize() {
		return this.oImpl.getSize();
	}

	/**
	 * Get the missing value object.
	 * @return The missing value object
	 @since 0.1
	 **/
	public Object getMissingValue() {
		return this.oImpl.getMissingValue();
	}

	/**
	 * Set the missing value object.
	 * @param oMissingValue The missing value to be set.
	@since 0.1
	 **/
	public void setMissingValue(Object oMissingValue) {
		this.oImpl.setMissingValue(oMissingValue);
	}

	/**
	 * Get the null value object.
	 * @return The null value object.
	@since 0.1
	 **/
	public Object getNullValue() {
		return this.oImpl.getNullValue();
	}

	/**
	 * Set the null value object.
	 * @param oNullValue The null value to be set.
	@since 0.1
	 **/
	public void setNullValue(Object oNullValue) {
		this.oImpl.setNullValue(oNullValue);
	}

	/**
	 * Get the null value object.
	 * @return The empty value object.
	 @since 0.1
	 **/
	public Object getEmptyValue() {
		return this.oImpl.getEmptyValue();
	}

	/**
	 * Set the empty value object.
	 * @param oEmptyValue The empty value to be set.
	@since 0.1
	 **/
	public void setEmptyValue(Object oEmptyValue) {
		this.oImpl.setEmptyValue(oEmptyValue);
	}

	/**
	 * This method queries the value of the column 
	 * and return these objects in a List.
	 * @return The list of values of the column.
	 @since 0.1
	 **/
	public List<Object> getValues() {
		return this.oImpl.getValues();
	}

	/**
	 * Count the number of missing values.
	 * @return The total number of missing values in the column.
	@since 0.1
	 **/
	public int countMissingValues() {
		return this.oImpl.countMissingValues();
	}

	/**
	 * Count the number of empty values.
	 * @return The total number of empty values in the column.
	 @since 0.1
	 **/
	public int countEmptyValues() {
		return this.oImpl.countEmptyValues();
	}

	/**
	 * Count the number of null values.
	 * @return The total number of values in the column.
	 @since 0.1
	 **/
	public int countNullValues() {
		return this.oImpl.countNullValues();
	}

	/**
	 * Count the number of invalid values (i.e., missing, null and empty).
	 * @return The total number of invalid values in the column.
	 @since 0.1
	 **/
	public int countInvalidValues() {
		return this.oImpl.countInvalidValues();
	}
}