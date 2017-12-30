package es.uco.kdis.datapro.datatypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a multivalue of double values. 
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}<p>
 * <li>Aurora Ramirez Quesada, MSc 	[aramirez@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li>	(ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements.</li>
 * <li> (IMA|RPB, 0.1, February 2014) 	Initial version.</li> 
 * </ul>
 * <p>
 * @param <C>
 * 
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 **/
public class MultiDoubleValue extends MultiValue<Double> {

	/**
	 * Default constructor.
     @since 0.1
	 **/
	public MultiDoubleValue () {
		this.rgoMultivalues = new ArrayList<Double>();
	}

	/**
	 * Add a value at the end of the multivalue.
	 * @param dValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Double dValue) {
		try{
			this.rgoMultivalues.add(dValue);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	/**
	 * Add a value at the specified index.
	 * @param dValue The value to be added.
	 * @param iIndex The position index where the object has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Double dValue, int iIndex) {
		try {
			this.rgoMultivalues.add(iIndex, dValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Append a list of values at the end of the multivalue.
	 * @param rgdValues The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addAllValues(List<Double> rgdValues) {		
		int iCounter = 0;
		int iSize = rgdValues.size();
		for(int i = 0; i < iSize; i++)
			iCounter += this.addValue(rgdValues.get(i));
		return iCounter;
	}

	/**
	 * Append a list of values between two positions.
	 * @param rgdValues The list of values to be added.
	 * @param iFromIndex The initial position index.
	 * @param iToIndex The final position index.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValues(List<Double> rgdValues, int iFromIndex, int iToIndex) {
		int iCounter = 0;
		int iPos = 0;
		for(int i = iFromIndex; i <= iToIndex; i++){
			iCounter += this.addValue(rgdValues.get(iPos), i);
			iPos += 1;
		}
		return iCounter;
	}

	/**
	 * Set a value at the specified position.
	 * @param dValue The value to be added.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values successfully set.
	 @since 0.1
	 **/
	@Override
	public int setValue(Double dValue, int iIndex) {
		try {
			this.rgoMultivalues.set(iIndex, dValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// CONVERSION METHODS

	/**
	 * Convert into a multivalue of string values.
	 * @return MultiStringValue with corresponding string values.
	 @since 0.1
	 **/
	public MultiStringValue toMultiString() {
		MultiStringValue oResult = new MultiStringValue();
		int iSize = this.rgoMultivalues.size();
		Double dValue;
		for (int i = 0; i < iSize; i++){
			dValue = this.rgoMultivalues.get(i);
			oResult.addValue(dValue.toString());
		}
		return oResult;
	}

	/**
	 * Convert into a multivalue of integer values, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return MultiIntegerValue with the corresponding integer values.
	 @since 0.1
	 **/
	public MultiIntegerValue toMultiInteger(boolean bRoundedValue) {
		MultiIntegerValue oResult = new MultiIntegerValue();
		int iSize = this.rgoMultivalues.size();
		Integer iValue;
		Double dValue; 

		for(int i = 0; i < iSize; i++){
			dValue = this.rgoMultivalues.get(i);
			// Round
			if (bRoundedValue)
				iValue = (int) Math.round(dValue);
			// Truncate
			else{
				BigDecimal bd = new BigDecimal(dValue);
				bd = bd.setScale(40, BigDecimal.ROUND_HALF_UP);
				iValue  = bd.intValue();
			}
			oResult.addValue(iValue);
		}
		return oResult;
	}  

	/**
	 * Convert into a multivalue of long values, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return MultiLongValue with the corresponding long values.
	 @since 0.1
	 **/
	public MultiLongValue toMultiLong(boolean bRoundedValue) {		
		MultiLongValue oResult = new MultiLongValue();
		int iSize = this.rgoMultivalues.size();
		Long lValue;
		Double dValue; 

		for(int i = 0; i < iSize; i++){
			dValue = this.rgoMultivalues.get(i);
			// Round
			if (bRoundedValue)
				lValue = (long) Math.round(dValue);
			// Truncate
			else{
				BigDecimal bd = new BigDecimal(dValue);
				bd = bd.setScale(40, BigDecimal.ROUND_HALF_UP);
				lValue  = bd.longValue();
			}
			oResult.addValue(lValue);
		}
		return oResult;
	}	
}
