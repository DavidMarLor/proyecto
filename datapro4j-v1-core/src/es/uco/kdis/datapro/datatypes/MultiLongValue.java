package es.uco.kdis.datapro.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a multivalue of long values. 
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
public class MultiLongValue extends MultiValue<Long> {

	/**
	 * Default constructor.
     @since 0.1
	 **/
	public MultiLongValue () {
		this.rgoMultivalues = new ArrayList<Long>();
	}
	
	/**
	 * Add a value at the end of the multivalue.
	 * @param lValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Long lValue) {
		try {
			this.rgoMultivalues.add(lValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Add a value at the specified index.
	 * @param lValue The value to be added.
	 * @param iIndex The position index where the object has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Long lValue, int iIndex) {
		try {
			this.rgoMultivalues.add(iIndex, lValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Append a list of values at the end of the multivalue.
	 * @param rglValues The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addAllValues(List<Long> rglValues) {		
		int iCounter = 0;
		int iSize = rglValues.size();
		for(int i = 0; i < iSize; i++)
			iCounter += this.addValue(rglValues.get(i));
		return iCounter;
	}
	
	/**
	 * Append a list of values between two positions.
	 * @param rglValues The list of values to be added.
	 * @param iFromIndex The initial position index.
	 * @param iToIndex The final position index.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValues(List<Long> rglValues, int iFromIndex, int iToIndex) {
		int iCounter = 0;
		int iPos = 0;
		for(int i = iFromIndex; i <= iToIndex; i++){
			iCounter += this.addValue(rglValues.get(iPos), i);
			iPos += 1;
		}
		return iCounter;
	}
	
	/**
	 * Set a value at the specified position.
	 * @param lValue The value to be added.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values successfully set.
	 @since 0.1
	 **/
	@Override
	public int setValue(Long lValue, int iIndex) {
		try {
			this.rgoMultivalues.set(iIndex, lValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	// CONVERSION METHODS
	
	/**
	 * Convert into a multivalue of double values.
	 * @return MultiDoubleValue with the corresponding floating values.
	 @since 0.1
	 **/
	public MultiDoubleValue toMultiDouble() {
		MultiDoubleValue oResult = new MultiDoubleValue();
		int iSize = this.rgoMultivalues.size();
		Long lValue;
		
		for (int i = 0; i < iSize; i++){
			lValue = this.rgoMultivalues.get(i);
			oResult.addValue(new Double(lValue.doubleValue()));
		}
		return oResult;
	}
	
	/**
	 * Convert into a multivalue of integer values.
	 * If the value is lower than Integer.MIN_VALUE or greater
	 * than Integer.MAX_VALUE, values will be rounded to Integer.MIN_VALUE / Integer.MAX_VALUE.
	 * @return MultiIntegerValue with the corresponding integer values.
	 @since 0.1
	 **/
	public MultiIntegerValue toMultiInteger() {		
		MultiIntegerValue oResult = new MultiIntegerValue();
		int iSize = this.rgoMultivalues.size();
		Long lValue;
		
		for(int i = 0; i < iSize; i++){
			lValue = this.rgoMultivalues.get(i);
			if (lValue >= Integer.MIN_VALUE && lValue <= Integer.MAX_VALUE) {
				oResult.addValue(lValue.intValue());
			}
			else {
				if (lValue < Integer.MIN_VALUE)
					oResult.addValue(Integer.MIN_VALUE);
				else
					oResult.addValue(Integer.MAX_VALUE);
			}
		}
		return oResult;
	}	
}
