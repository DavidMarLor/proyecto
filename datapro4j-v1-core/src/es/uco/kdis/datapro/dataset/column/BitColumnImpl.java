package es.uco.kdis.datapro.dataset.column;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * This class represents the implementation of a bit column. It defines
 * the actual implementation of the methods invoked from its abstraction.
 * This class and its methods should not be directly invoked.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (ARQ, 0.2, July 2014) New methods: conversion methods, countXValues
 * <li> (AMP, 0.1, July 2012) Initial version.</li>
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Antonio Jose Muñoz Perez (AMP, 0.1)
 @version 0.2
 *
 **/
public class BitColumnImpl extends ColumnImpl {

	/**
	 * <code>rgoValues</code> are the column values.
	 * @since   0.1
	 */
	private BitSet rgoValues = new BitSet();

	/**
	 * <code>iLenght</code> is the lenght of the column.
	 * @since   0.1
	 */
	private int iLenght = 0;

	/**
	 * Default constructor
	 @since 0.1
	 */
	public BitColumnImpl() {
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue Value to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
	@Override
	public int addValue(Object oValue) {
		if (oValue.toString().equals("1") || oValue.toString().equalsIgnoreCase("true"))
			this.rgoValues.set(this.getSize());
		else
			this.rgoValues.clear(this.getSize());

		this.iLenght++;
		return 1;
	}

	/**
	 * Add a value at the end of the column.
	 * @param cValue Value to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
	public int addValue(char cValue) {
		if (cValue == '1')
			this.rgoValues.set(this.getSize());
		else 
			this.rgoValues.clear(this.getSize());

		this.iLenght++;
		return 1;
	}

	/**
	 * Add a value at the end of the column.
	 * @param bValue Value to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
	public int addValue(boolean bValue) {
		if (bValue) 
			this.rgoValues.set(this.getSize());
		else
			this.rgoValues.clear(this.getSize());

		this.iLenght++;
		return 1;
	}

	/**
	 * Add a value at the specified index. The current value and 
	 * next values are shifted to the right.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the value should be added. 
	 * @return The number of values added
	 @since 0.1
	 */
	@Override
	public int addValue(Object oValue, int iIndex) {		
		int iPos = this.iLenght;
		int iAux;

		while((iAux = this.rgoValues.previousSetBit(iPos))>=iIndex){
			iPos=iAux;
			if(iAux-1>=0){
				this.rgoValues.set(iAux+1);
				this.rgoValues.clear(iAux);
			}
		}

		if (oValue.toString().equalsIgnoreCase("1") || oValue.toString().equalsIgnoreCase("true"))
			this.rgoValues.set(iIndex);
		else
			this.rgoValues.clear(iIndex);

		this.iLenght++;
		return 1;
	}

	/**
	 * Add a value at the specified index. The current value and 
	 * next values are shifted to the right.
	 * @param cValue The value to be added.
	 * @param iIndex The index where the value should be added. 
	 * @return The number of values added
	 @since 0.1
	 */
	public int addValue(char cValue, int iIndex) {		
		int iPos = this.iLenght;
		int iAux;

		while((iAux = this.rgoValues.previousSetBit(iPos))>=iIndex){
			iPos=iAux;
			if(iAux-1>=0){
				this.rgoValues.set(iAux+1);
				this.rgoValues.clear(iAux);
			}
		}

		if (cValue == '1')
			this.rgoValues.set(iIndex);
		else
			this.rgoValues.clear(iIndex);

		this.iLenght++;
		return 1;
	}

	/**
	 * Add a value at the specified index. The current value and 
	 * next values are shifted to the right.
	 * @param bValue The value to be added.
	 * @param iIndex The index where the value should be added. 
	 * @return The number of values added
	 @since 0.1
	 */
	public int addValue(boolean bValue, int iIndex) {		
		int iPos = this.iLenght;
		int iAux;

		while((iAux = this.rgoValues.previousSetBit(iPos))>=iIndex){
			iPos=iAux;
			if(iAux-1>=0){
				this.rgoValues.set(iAux+1);
				this.rgoValues.clear(iAux);
			}
		}

		if (bValue)
			this.rgoValues.set(iIndex);
		else
			this.rgoValues.clear(iIndex);

		this.iLenght++;
		return 1;
	}

	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 */
	@Override
	public int setValue(Object oValue, int iIndex) {
		if (oValue.toString().equalsIgnoreCase("1") || oValue.toString().equalsIgnoreCase("true")) {
			this.rgoValues.set(iIndex);
			return 1;
		}

		this.rgoValues.clear(iIndex);
		return 1;
	}

	/**
	 * Set a value at the specified position.
	 * @param cValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 */
	public int setValue(char cValue, int iIndex) {
		if (cValue == '1') {
			this.rgoValues.set(iIndex);
			return 1;
		}

		this.rgoValues.clear(iIndex);
		return 1;
	}

	/**
	 * Set a value at the specified position.
	 * @param bValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 */
	public int setValue(boolean bValue, int iIndex) {
		if (bValue) {
			this.rgoValues.set(iIndex);
			return 1;
		}

		this.rgoValues.clear(iIndex);
		return 1;
	}

	/**
	 * Remove the value at the specified index.
	 * @param iIndex The position index to be removed.
	 * @throws IndexOutOfBoundsException
     @since 0.1
	 */
	@Override
	public void removeValue(int iIndex) {		
		int iPos;

		this.rgoValues.clear(iIndex);

		while((iPos = this.rgoValues.nextSetBit(iIndex))!=-1){
			iIndex=iPos;
			if(iPos-1>=0){
				this.rgoValues.set(iPos-1);
				this.rgoValues.clear(iPos);
			}
		}

		this.iLenght--;		
	}

	/**
	 * Append a list of values at the end of the column.
	 * @param rgoCol The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
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
	 */
	@Override
	public List<Object> getValues() {
		ArrayList<Object> rgoList = new ArrayList<Object>();

		for(int i = 0; i < this.iLenght; i++)
			rgoList.add(this.rgoValues.get(i));

		return rgoList;
	}

	/**
	 * Get the element stored at specified position.
	 * @param iPos The specified position.
	 * @return The element at <code>iPos</code>.
	 @since 0.1
	 */
	@Override
	public Object getElement(int iPos) {
		return this.rgoValues.get(iPos);
	}

	/**
	 * Get the number of elements in the column. 
	 * @return The number of elements comprised by the column.
	 @since 0.1
	 */
	@Override
	public int getSize() {
		return this.iLenght;
	}

	/**
	 * Count the number of missing values in the column. Since
	 * missing values are set to false by default, this
	 * method always returns 0.
	 * @return The total amount of missing values in the column.
	 @since 0.2
	 **/
	@Override
	public int countMissingValues() {
		return 0;
	}

	/**
	 * Count the number of empty values in the column.Since
	 * empty values are set to false by default, this
	 * method always returns 0.
	 * @return The total amount of empty values in the column.
	 @since 0.2
	 **/
	@Override
	public int countEmptyValues() {
		return 0;
	}

	/**
	 * Count the number of null values in the column. Since
	 * null values are set to false by default, this
	 * method always returns 0.
	 * @return The total amount of null values in the column.
	 @since 0.2
	 **/
	@Override
	public int countNullValues() {
		return 0;
	}

	/**
	 * Count the number of invalid values (missing, null and 
	 * empty values) in the column. Since invalid values
	 * are set to false by default, this method always returns 0.
	 * @return The total number of invalid values in the column.
	 @since 0.2
	 **/
	@Override
	public int countInvalidValues() {
		return 0;
	}

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column.
	 * @return CategoricalColumn that defines two categories (true/false), 
	 * one for each bit value (1=true,0=false).
	 @since 0.2
	 **/
	public CategoricalColumn toCategorical(String sName){
		CategoricalColumn oResultColumn = new CategoricalColumn(sName);

		String sTrueCategory = "true";
		String sFalseCategory = "false";

		// Add the categories
		oResultColumn.addCategory(sTrueCategory);
		oResultColumn.addCategory(sFalseCategory);

		int iColSize = this.rgoValues.size();
		Object oValue;

		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);

			// set the category according to the boolean value
			if (((Boolean)oValue).booleanValue())
				oResultColumn.addValue(sTrueCategory);
			else
				oResultColumn.addValue(sFalseCategory);
		}
		return oResultColumn;
	}

	/**
	 * Convert into a binary column.
	 * @return BinaryColumn that defines two values, true or false, 
	 * one for each bit value (1=true,0=false).
	@since 0.2
	 **/
	public BinaryColumn toBinary(String sName){

		BinaryColumn oResultColumn = new BinaryColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;
		for(int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			// Simply stores the returned boolean value
			oResultColumn.addValue(oValue);
		}

		return oResultColumn;
	}

	/**
	 * Convert into an integer column.
	 * @return IntegerColumn where each bit is converted into an integer value.
	 @since 0.2
	 **/
	public IntegerColumn toInteger(String sName){
		IntegerColumn oResultColumn = new IntegerColumn(sName);

		int iColSize = this.rgoValues.size();
		Object oValue;

		for(int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			// create the corresponding integer value
			if(((Boolean)oValue).booleanValue()){
				oResultColumn.addValue(new Integer(1));
			}
			else
				oResultColumn.addValue(new Integer(0));
		}

		return oResultColumn;
	}

}