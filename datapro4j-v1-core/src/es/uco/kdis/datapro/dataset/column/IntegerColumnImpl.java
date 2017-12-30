package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.NullValue;

/**
 * This class represents the implementation of an integer column, a subclass of 
 * a generic numerical column. It defines the actual implementation of the 
 * methods invoked from its abstraction. This class and its methods should not be directly invoked.
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
 * <li> (ARQ, 0.3, April 2012) New methods: mean(), standardDeviation().</li>
 * <li> (JRR, 0.3, March 2012) Invalid values are replaced by specific classes. Other specific changes:
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
 @author Jose Raul Romero (JRR, 0.2, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.3)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 *
 **/
public final class IntegerColumnImpl extends NumericalColumnImpl {

	/**
	 * Default constructor
	 @since 0.1
	 **/
	public IntegerColumnImpl() {
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
    		if (oValue instanceof Integer || oValue instanceof InvalidValue) {
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
    		if (oValue instanceof Integer || oValue instanceof InvalidValue) {
    			this.rgoValues.add(iIndex, oValue);
    			return 1;
    		}
    	} catch (Exception e) {
    		return 0;
    	}
    	return 0;
	}

	/**
	 *  Set a value at the specified position.
	 * @param oValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 **/
    @Override
	public int setValue(Object oValue, int iIndex) {
    	try {
    		if (oValue == null) {
    			this.rgoValues.set(iIndex, NullValue.getNullValue());
    			return 1;
    		}
    		if (oValue instanceof Integer || oValue instanceof InvalidValue) {
    			this.rgoValues.set(iIndex, oValue);
    			return 1;
    		}
    	} catch (Exception e) {
    		return 0;
    	}
    	return 0;
	}

    /**
     * Get the mean of the values of the column.
	 * @return The mean of the values of the column.
	 @since 0.3
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
				dSum += ((Integer)oValue).doubleValue();
				iNumElems++;
			}
		}
		return dSum / iNumElems;
	}
    
	/**
	 * Get the standard deviation of the values of the column.
	 * @return The standard deviation of the values of the column.
	 @since 0.3
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
				dSum2 += (((Integer)oValue).doubleValue() * ((Integer)oValue).doubleValue());
				dMean += ((Integer)oValue).doubleValue();
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
    @Override
	public double getMaxValue() {
    	int iMaxValue = Integer.MIN_VALUE;
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && !(oValue instanceof InvalidValue)) {
				if ((Integer)oValue > iMaxValue)
					iMaxValue = (Integer) oValue;
			}
		}
		return iMaxValue;
	}
    
	/**
	 * Get the minimum value of the column.
	 * @return The minimum value of the column.
	 @since 0.1
	 **/
    @Override
	public double getMinValue() {
    	int iMinValue = Integer.MAX_VALUE;
    	int iColSize = this.rgoValues.size();
    	Object oValue;
    	for (int i = 0; i< iColSize; i++) {
    		oValue = this.rgoValues.get(i);
    		if (oValue != null && !(oValue instanceof InvalidValue)) {
    			if ((Integer)oValue < iMinValue)
    				iMinValue = (Integer) oValue;                        
    		}
    	}
    	return iMinValue;
	}

    // CONVERSION METHODS
    
    /**
	 * Convert into a categorical column, where each value 
	 * corresponds to a single category (with no repetition).
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
	 * Convert into a numerical column, where each value is converted to floating number.
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
				oResCol.addValue(new Double((Integer)oValue).doubleValue());
		}
		return oResCol;
	}
}
