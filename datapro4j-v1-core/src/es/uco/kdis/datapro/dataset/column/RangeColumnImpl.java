package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.*;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the implementation of a range column.
 * <p>A range is a pair [MinValue, MaxValue].
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
 * <li> (JRR, 0.3, March 2012) 			Moved to Range<>; setXValues and getXValues (for invalid values) remain only for serialization
 * 										purposes. Singleton objects of InvalidValue, MissingValue and NullValue are used instead. 
 * 										The methods setXValues() including the parameter bForceChange were removed.
 * <li> (JRR|JML, 0.2, September 2011) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, May 2011) 			Initial version.
 * </ul>
 * <p>
 *
 * @author Jose Raul Romero (JRR, 0.2, 0.3)
 * @author Jose Maria Luna (JML, 0.2)
 * @author Mario Orozco Borrego  (MOB, 0.1)
 * @version 0.3
 **/
public class RangeColumnImpl extends ColumnImpl {

	/** 
	 * <code>rgoValues</code> are the column values.
	 @since 0.1 
	 **/
	private ArrayList<Object> rgoValues = new ArrayList<Object>();

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public RangeColumnImpl() {
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
			if (oValue instanceof Range || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Range || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Range || oValue instanceof InvalidValue) {
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
	public int addAllValues(List<Object> rgoValues) {
		int iCounter = 0;
		int iSize = rgoValues.size();
		for(int i = 0; i < iSize; i++){
			iCounter += this.addValue(rgoValues.get(i));
		}
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
	 */
	@Override
	public int getSize() {
		return this.rgoValues.size();
	}

	/**
	 * Count the number of missing values in the column.
	 * @return The total missing values in the column.
	 @since 0.1
	 */
	@Override
	public int countMissingValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue instanceof MissingValue)
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
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue instanceof EmptyValue)
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
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue instanceof NullValue)
				iCounter++;
		}
		return iCounter;
	}

	/**
	 * Count the invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values (missing, null and empty) in the column.
	 @since 0.1
	 **/
	@Override
	public int countInvalidValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue instanceof InvalidValue)
				iCounter++;
		}
		return iCounter;
	}

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column where each 
	 * range is a category (with no repetition).
	 * @param sName The name of the column.
	 * @return CategoricalColumn associated to the range column.
	 @throws NotAddedValueException  
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(String sName) throws NotAddedValueException {

		CategoricalColumn cReturnColumn = new CategoricalColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;

		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);

			if(oValue == null)
				cReturnColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				cReturnColumn.addValue(oValue);
			else if (oValue instanceof Range)
				cReturnColumn.addValue(oValue.toString(), true);
			else
				throw new NotAddedValueException("Invalid value. It should be an instance of Range");
		}

		return cReturnColumn;
	}

	/**
	 * Convert into a numerical column according to a transformation mode.
	 * @param sName The name of the column.
	 * @param iMode An integer between 0 and 3 which indicate a mode of transformation, possible modes are:
	 * 		<ul>
	 * 			<li> 0: Choose the minimum value of the range. </li>
	 * 			<li> 1: Choose the maximum value of the range. </li>
	 * 			<li> 2: Each value will be the arithmetical mean of min and max. </li>
	 * 			<li> 3: A random value in the range will be chosen. </li>
	 * 		</ul>
	 * @return NumericalColumn correspondence with the range column according to the mode.
	 @throws NotAddedValueException  
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(String sName, int iMode) throws NotAddedValueException{
		NumericalColumn cReturnColumn = null;

		if (iMode >= 0 && iMode <= 3){
			cReturnColumn = new NumericalColumn(sName);
			Random oRnd = new Random(System.currentTimeMillis());

			int iColSize = this.rgoValues.size();

			for (int i = 0; i < iColSize; i++){

				Object oValue = this.rgoValues.get(i);

				if(oValue == null)
					cReturnColumn.addValue(NullValue.getNullValue());
				else if (oValue instanceof InvalidValue) 
					cReturnColumn.addValue(oValue);
				else if (oValue instanceof Range){
					// TODO ARQ: How to control parameterized values without warnings? Partial solution: unknown parameter type and double conversion
					Double oMinValue = (Double)((Range<?>) oValue).getMinValue();
					Double oMaxValue = (Double)((Range<?>) oValue).getMaxValue();
					Double oAddValue = null;

					switch (iMode){
					case 0: // Minimum
						oAddValue = oMinValue;
						break;

					case 1: // Maximum
						oAddValue = oMaxValue;
						break;

					case 2: // Mean
						oAddValue = (oMinValue + oMaxValue) / 2;
						break;

					case 3: // Random
						oAddValue = oRnd.nextDouble()*(oMaxValue-oMinValue)+oMinValue;
						break;
					}

					cReturnColumn.addValue(oAddValue);
				}
				else
					throw new NotAddedValueException("Invalid value. It should be an instance of Range");
			}
		}
		else
			throw new NotAddedValueException("Invalid mode. It should be an integer between 0 and 3 which indicate a mode of transformation.");

		return cReturnColumn;
	}

	/**
	 * Convert into a numerical column according to a Gauss distribution.
	 * @param sName The name of the column.
	 * @param dMean The arithmetic mean.
	 * @param dStdDev The standard deviation.
	 * @return NumericalColumn that corresponds to the range column according to the Gauss distribution.
	 @throws NotAddedValueException
	 @since 0.1
	 **/
	public NumericalColumn toNumericalByGaussian(String sName, double dMean, double dVariance) throws NotAddedValueException{
		NumericalColumn cReturnColumn = new NumericalColumn(sName);

		int iColSize = this.rgoValues.size();
		Random rnd = new Random(System.currentTimeMillis());
		double dFraction;
		Object oValue, oMinValue, oMaxValue, oAddValue;

		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);

			if(oValue == null)
				cReturnColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				cReturnColumn.addValue(oValue);
			else if (oValue instanceof Range){
				oMinValue = ((Range<?>) oValue).getMinValue();
				oMaxValue = ((Range<?>) oValue).getMaxValue();

				dFraction = rnd.nextGaussian()*dVariance + dMean;
				oAddValue = dFraction*((Double)oMaxValue) - ((Double)oMinValue)*2;
				cReturnColumn.addValue(oAddValue);
			}
			else
				throw new NotAddedValueException("Invalid value. It should be an instance of Range");
		}
		return cReturnColumn;
	}
}