package es.uco.kdis.datapro.dataset.column;

/**
 * This class represents the abstraction of a bit column. It defines
 * the methods to manipulate a bit column. Thus, this column only allows
 * the storage of two types of values, true or false. Invalid values are
 * set as false by default.
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
 * <li> (ARQ, 0.2, July 2014) New methods: conversion methods
 * <li> (AMP, 0.1, July 2012) Initial version.
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Antonio Jose Muñoz Perez (AMP, 0.1)
 @version 0.2
 **/
public class BitColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 */
	public BitColumn() {
		super("", ColumnType.Bit);
		this.oImpl = new BitColumnImpl();
	}

	/**
	 * Parameterized constructor.
	 * @param sName Name of the column.
	 @since 0.1
	 */
	public BitColumn(String sName) {
		super(sName, ColumnType.Bit);
		this.oImpl = new BitColumnImpl();
	}

	/**
	 * Add a value at the end of the column.
	 * @param cValue Value to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
	public int addValue(char cValue) {		
		return ((BitColumnImpl)oImpl).addValue(cValue);
	}

	/**
	 * Add a value at the end of the column.
	 * @param bValue Value to be added.
	 * @return The number of values added.
	 @since 0.1
	 */
	public int addValue(boolean bValue) {	
		return ((BitColumnImpl)oImpl).addValue(bValue);
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
		return ((BitColumnImpl)oImpl).addValue(cValue,iIndex);
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
		return ((BitColumnImpl)oImpl).addValue(bValue,iIndex);
	}

	/**
	 * Set a value at the specified position.
	 * @param cValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 */
	public int setValue(char cValue, int iIndex) {
		return ((BitColumnImpl)oImpl).setValue(cValue,iIndex);
	}

	/**
	 * Set a value at the specified position.
	 * @param bValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 */
	public int setValue(boolean bValue, int iIndex) {
		return ((BitColumnImpl)oImpl).setValue(bValue,iIndex);
	}

	// CONVERSION METHODS 

	/**
	 * Convert into a categorical column.
	 * @return CategoricalColumn that defines two categories (true/false), 
	 * one for each bit value (1=true,0=false).
	 @since 0.2
	 **/
	public CategoricalColumn toCategorical(){
		return ((BitColumnImpl)this.oImpl).toCategorical(this.getName());
	}

	/**
	 * Convert into a binary column.
	 * @return BinaryColumn that defines two values, true or false, 
	 * one for each bit value (1=true,0=false).
	 @since 0.2
	 **/
	public BinaryColumn toBinary(){
		return ((BitColumnImpl)this.oImpl).toBinary(this.getName());
	}

	/**
	 * Convert into an integer column.
	 * @return  IntegerColumn where each bit is converted into an integer value.
	 @since 0.2
	 **/
	public IntegerColumn toInteger(){
		return ((BitColumnImpl)this.oImpl).toInteger(this.getName());
	}
}
