package es.uco.kdis.datapro.dataset.column;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;

/**
 * This class represents the implementation of a long column, a subclass of 
 * a generic numerical column. It defines the actual implementation of the 
 * methods invoked from its abstraction. This class and its methods should not be directly invoked.
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
 * <li>	(ARQ, 0.2, August 2015) Performance, refactoring and designing issues. Improvements.</li>
 * <li> (IMA|RPB, 0.1, December 2013) Initial version.</li>
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 *
 **/
public class LongColumnImpl extends NumericalColumnImpl {

	/**
	 * Default constructor
	 * @since 0.1
	 **/
	public LongColumnImpl() {
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
    		if (oValue instanceof Long || oValue instanceof InvalidValue) {
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
    		if (oValue instanceof Long || oValue instanceof InvalidValue) {
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
    		if (oValue instanceof Long || oValue instanceof InvalidValue) {
    			this.rgoValues.set(iIndex, oValue);
    			return 1;
    		}
    	} catch (Exception e) {
    		return 0;
    	}
    	return 0;
	}

	/**
	 * Get the maximum value of the column.
	 * @return The maximum value of the column.
	 @since 0.1
	 **/
    @Override
	public double getMaxValue() {
    	long lMaxValue = Long.MIN_VALUE;
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue)) {
				if ((Long)oValue > lMaxValue)
					lMaxValue = (Long) oValue;
			}
		}
		return lMaxValue;
	}
    
    /**
	 * Get the minimum value of the column.
	 * @return The minimum value of the column.
	 @since 0.1
	 **/
    @Override
	public double getMinValue() {
    	long lMinValue = Long.MAX_VALUE;
    	int iColSize = this.rgoValues.size();
    	Object oValue;
    	for (int i = 0; i< iColSize; i++) {
    		oValue = this.rgoValues.get(i);
    		if (oValue != null && !(oValue instanceof InvalidValue)) {
    			if ((Long)oValue < lMinValue)
    				lMinValue = (Long) oValue;                        
    		}
    	}
    	return lMinValue;
	}

    /**
	 * Get the mean of the values in the column.
	 * @return The mean of the values in the column.
	 @since 0.1
	 **/
    @Override
	public double mean() {
		Double dSum = new Double(0.0);
		int iNumElems = 0;
		int iTotal = this.rgoValues.size();
		Object oValue;
		
		for (int i = 0; i < iTotal; i++) {
			oValue = this.rgoValues.get(i);

			if (oValue != null && !(oValue instanceof InvalidValue)) {
				dSum += ((Long)oValue).doubleValue();
				iNumElems++;
			}
		}
		return dSum / iNumElems;
	}
    
    /**
	 * Get the standard deviation of the values in the column.
	 * @return The standard deviation of the values in the column.
	 @since 0.1
	 **/
	@Override
	public double standardDeviation(){
		Double dSum2 = 0.0;
		Double dMean = 0.0;
		int iNumElems = 0;
		int iSize = this.rgoValues.size();
		Object oValue;
		
		for(int i=0; i<iSize; i++){
			oValue = this.rgoValues.get(i);
			
			if (oValue != null && !(oValue instanceof InvalidValue)){
				dSum2 += (((Long)oValue).doubleValue() * ((Long)oValue).doubleValue());
				dMean += ((Long)oValue).doubleValue();
				iNumElems++;
			}
		}
		dMean = dMean/iNumElems;
		return Math.sqrt((dSum2 / iNumElems) - (dMean*dMean));
	}

	// CONVERSION METHODS
	
	/**
	 * Convert into a categorical column, where each value corresponds to 
	 * a single category (with no repetition).
	 * @param sName The name of the column.
	 * @return CategoricalColumn that corresponds to the integer column.
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(String sName){
		CategoricalColumn oResultColumn = new CategoricalColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null || oValue instanceof InvalidValue)
				oResultColumn.addValue(oValue);
			else
				oResultColumn.addValue(oValue.toString(), true);
		}
		return oResultColumn;
	}

	/**
	 * Convert into a numerical column, where each value is converted to a floating number.
	 * @param sName The name of the column.
	 * @return NumericalColumn with the corresponding floating values.
	 @since 0.1
	 **/ 
	public NumericalColumn toNumerical(String sName){
		NumericalColumn oResCol = new NumericalColumn(sName);
		int iColSize = this.rgoValues.size();
		Object oValue;
		
		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null || oValue instanceof InvalidValue)
				oResCol.addValue(oValue);
			else
				oResCol.addValue(new Double((Long)oValue).doubleValue());
		}
		return oResCol;
	}
	
	/**
	 * Convert into an integer column, according to a mode of conversion.
	 * @param sName The name of the column.
	 * @param bRoundedValue The conversion mode for values exceeding the integer boundary: 
	 * if it is false, the values will be set as MissingValue, 
	 * otherwise, values will be rounded to Integer.MIN_VALUE / Integer.MAX_VALUE.
	 * @return IntegerColumn with the corresponding integer values.
	 @since 0.1
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
			else if ((Long)oValue >= Integer.MIN_VALUE && (Long)oValue <= Integer.MAX_VALUE)
				oResultColumn.addValue(((Long)oValue).intValue());
			else {
				// Round
				if (bRoundedValue){
					if ((Long)oValue < Integer.MIN_VALUE)
						iValue = Integer.MIN_VALUE;
					else
						iValue = Integer.MAX_VALUE;
					
					oResultColumn.addValue(iValue);
				}
				// Set missing value
				else{
					oResultColumn.addValue(MissingValue.getMissingValue());
				}
			}
		}
		return oResultColumn;
	}  
	
	/** 
	 * Convert into a date column.
	 * @param sName The name of the column.
	 * @return DateColumn with the corresponding date values.
	 @since 0.1
	 */
	public DateColumn toDate(String sName, SimpleDateFormat oFormat){
		
		DateColumn oResultColumn = new DateColumn(sName);
		oResultColumn.addDateSpecification(oFormat);
		
		int iColSize = this.rgoValues.size();
		Object oValue;	
		
		for (int i = 0; i < iColSize; i++){
			oValue = this.rgoValues.get(i);
			if (oValue == null || oValue instanceof InvalidValue)
				oResultColumn.addValue(oValue);
			else
				oResultColumn.addValue(oResultColumn.getDateSpecification().format(new Date((Long)oValue)));
		}
		return oResultColumn;
	}
}
