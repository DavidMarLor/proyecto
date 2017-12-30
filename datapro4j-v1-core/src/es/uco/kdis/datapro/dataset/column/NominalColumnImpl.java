package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of a nominal column (string).
 * The purpose of this class is to do all the implementation apart from the abstraction. 
 * Real operation codes are implemented by the column implementation.
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
 * <li>	(AMP|ARQ, 0.3, May 2014)	New method: getMaxValueLength.
 * <li> (JRR|JML, 0.2, March 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) 		Initial version. 
 * </ul>
 * <p>
 @author Aurora Ramirez Quesada (ARQ, 0.3)
 @author Antonio Jose Muñoz Perez (AMP, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 **/
public class NominalColumnImpl extends ColumnImpl {

	/** 
	 * <code>rgoValues</code> represents a column.
	 @since 0.1
	 **/
	private ArrayList<Object> rgoValues = new ArrayList<Object>();

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public NominalColumnImpl() {
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
			if (oValue instanceof String || oValue instanceof InvalidValue) {
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
			if (oValue instanceof String || oValue instanceof InvalidValue) {
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
			if (oValue instanceof String || oValue instanceof InvalidValue) {
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
	 @since 0.1
	 **/
	@Override
	public int countMissingValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();

		for(int i = 0; i < iColSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof MissingValue)
				iCounter++;
		}
		return iCounter;
	}

	/**
	 * Count the number of empty values in the column.
	 * @return The total empty values in the column.
	 @since 0.1
	 **/
	@Override
	public int countEmptyValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();

		for(int i = 0; i < iColSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof EmptyValue)
				iCounter++;
		}
		return iCounter;
	}

	/**
	 * Count the number of null values in the column.
	 * @return The total null values in the column.
	 @since 0.1
	 **/
	@Override
	public int countNullValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();

		for(int i = 0; i < iColSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof NullValue)
				iCounter++;
		}
		return iCounter;
	}

	/**
	 * Count the number of invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values (missing, null and empty) in
	 * the column
	 @since 0.1
	 **/
	@Override
	public int countInvalidValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();

		for(int i = 0; i < iColSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof InvalidValue)
				iCounter++;
		}
		return iCounter;
	}
	
	/**
	 * Get the maximum length of the values stored
	 * in the column.
	 * @return The length of the largest string value.
	 @since 0.3
	 */
	public int getMaxValueLength() {
		int iMaxValue = 1;
		Object oValue;
		int iSize = this.rgoValues.size();
		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && (oValue instanceof String) && (((String)oValue).length() > iMaxValue))
				iMaxValue = ((String)oValue).length();
		}
		return iMaxValue;
	}

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column. Each string is a category (with no repetition).
	 * @return  CategoricalColumn associated to the nominal column.
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(String sName){
		CategoricalColumn oResultColumn = new CategoricalColumn(sName);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);

			if(oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				oResultColumn.addValue(oValue);
			else
				oResultColumn.addValue(oValue, true);
		}

		return oResultColumn;
	}

	/**
	 * Convert into a numerical column.
	 * It creates the numerical representation of the string given, note if any string is not a numeric
	 * representable value, then an emptyValue will be added.
	 * @return NumericalColumn with the string numerical representation.
	 * Notes: the numeric column returned has no bounds.
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(String sName){
		NumericalColumn oResultColumn = new NumericalColumn(sName);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);

			if(oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				oResultColumn.addValue(oValue);
			else{
				try{
					oResultColumn.addValue(Double.parseDouble((String) oValue));
				}
				catch (NumberFormatException e){
					oResultColumn.addValue(EmptyValue.getEmptyValue());
				}
			}
		}

		return oResultColumn;
	}
}