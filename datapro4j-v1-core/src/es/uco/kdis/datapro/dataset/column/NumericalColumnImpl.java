package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of a numerical column.
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
 * <li> (JRR, 0.4, March 2012) 			setXValues and getXValues (for invalid values) remain only for serialization purposes.
 * 										Singleton objects of InvalidValue, MissingValue and NullValue are used instead. The methods setXValues()
 * 										including the parameter bForceChange were removed.
 * <li> (ARQ, 0.3, March 2012) 			New method: standardDeviation().
 * <li> (JRR|JML, 0.3, September 2011) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.2, July 2011) 			Conversion methods created.
 * <li> (JTL, 0.1, March 2011) 			Initial version. 
 * </ul>
 * <p>
 *
 * @author Jose Raul Romero (JRR, 0.3, 0.4)
 * @author Jose Maria Luna (JML, 0.3)
 * @author Aurora Ramirez Quesada (ARQ, 0.3)
 * @author Mario Orozco Borrego  (MOB, 0.2)
 * @author Jose de la Torre Lopez  (JTL, 0.1)
 * @version 0.4
 *
 ***/
public class NumericalColumnImpl extends ColumnImpl {

	/** 
	 * <code>rgoValues</code> are the column values.
	 @since 0.1
	 **/
	protected ArrayList<Object> rgoValues = new ArrayList<Object>();

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public NumericalColumnImpl() {
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
			if (oValue instanceof Double || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Double || oValue instanceof InvalidValue) {
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
			if (oValue instanceof Double || oValue instanceof InvalidValue) {
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
	public int getSize() {
		return this.rgoValues.size();
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
	public List<Object> getValues() {
		return this.rgoValues;
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
	 * @return The total null values in the column.
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
			if (oValue != null && oValue instanceof InvalidValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Get the mean of the values in the column.
	 * @return The mean of the values of the column.
	 @since 0.1
	 **/
	public double mean() {
		Double dSum = new Double(0.0);
		int iNumElems = 0;
		int iSize = this.rgoValues.size();
		Object oValue;

		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);

			if (oValue != null && !(oValue instanceof InvalidValue)){
				dSum += (Double)oValue;
				iNumElems++;
			}
		}
		return dSum / iNumElems;
	}

	/**
	 * Get the standard deviation of the values in the column.
	 * @return The standard deviation of the values of the column.
     @since 0.3
	 **/
	public double standardDeviation(){
		Double dSum2 = 0.0;
		Double dMean = 0.0;
		int iNumElems = 0;
		int iSize = this.rgoValues.size();
		Object oValue;

		for(int i=0; i<iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue)){
				dSum2 += ((Double)oValue * (Double)oValue);
				dMean += (Double)oValue;
				iNumElems++;
			}
		}
		dMean = dMean/iNumElems;
		return Math.sqrt((dSum2 / iNumElems) - (dMean*dMean));
	}

	/**
	 * Get the maximum value of the column.
	 * @return The maximum value of the column.
	 @since 0.1
	 **/
	public double getMaxValue() {

		Double dMaxValue = Double.NEGATIVE_INFINITY;
		Object oValue;
		int iSize = this.rgoValues.size();
		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && (oValue instanceof Double) && ((Double)oValue > dMaxValue))
				dMaxValue = (Double) oValue;
		}
		return dMaxValue.doubleValue();
	}

	/**
	 * Get the minimum value of the column.
	 * @return The minimum value of a column.
	 @since 0.1
	 **/
	public double getMinValue() {

		Double dMinValue = Double.POSITIVE_INFINITY;
		Object oValue;
		int iSize = this.rgoValues.size();
		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && (oValue instanceof Double) && ((Double)oValue < dMinValue))
				dMinValue = (Double) oValue;
		}
		return dMinValue.doubleValue();
	}

	/**
	 * Normalize all the values of the column.
	 @since 0.1
	 **/
	public void normalize(){
		int iSize = this.rgoValues.size();
		double dSum = 0;
		Object oValue;

		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue))
				dSum += Math.pow((Double)oValue,2);	
		}

		for (int i = 0; i < iSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue)){
				this.rgoValues.set(i, ((Double)oValue)/Math.sqrt(dSum));
			}
		}
	}

	/**
	 * Standardize all the values of the column
	 * @param mean The mean of the values of the column.
	 * @param variance The variance of the values of the column
	 @since 0.1
	 **/
	public void standardize(double dMean, double dVariance){
		int iColumnSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i < iColumnSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue)){
				this.rgoValues.set(i, (((Double)oValue) - dMean)/dVariance);
			}
		}
	}

	// CONVERSION METHODS

	/**
	 * Convert into a nominal column.
	 * @param sName The name of the column.
	 * @return NominalColumn with the string correspondence of values.
	 @since 0.2
	 **/
	public NominalColumn toNominal(String sName){
		NominalColumn oResultColumn = new NominalColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;

		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue)
				oResultColumn.addValue(oValue);
			else
				oResultColumn.addValue(oValue.toString());
		}

		return oResultColumn;
	}

	/**
	 * Convert into an integer column, according to a mode of conversion.
	 * @param sName The name of the column.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return IntegerColumn with the corresponding integer values.
	 @since 0.2
	 **/
	public IntegerColumn toInteger(String sName, boolean bRoundedValue){
		IntegerColumn oResultColumn = new IntegerColumn(sName);

		int iColSize = this.rgoValues.size();
		Integer iValue;
		Object oValue; 

		for(int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue)
				oResultColumn.addValue(oValue);
			else {
				// Round
				if (bRoundedValue)
					iValue = (int) Math.round(((Double)oValue).doubleValue());
				// Truncate
				else{
					BigDecimal bd = new BigDecimal((Double)oValue);
					bd = bd.setScale(40, BigDecimal.ROUND_HALF_UP);
					iValue  = bd.intValue();
				}
				oResultColumn.addValue(iValue);
			}
		}
		return oResultColumn;
	}  
}