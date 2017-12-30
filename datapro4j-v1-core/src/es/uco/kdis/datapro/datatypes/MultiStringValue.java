package es.uco.kdis.datapro.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a multivalue of string values. 
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
public class MultiStringValue extends MultiValue<String> {

	/**
	 * Default constructor.
     @since 0.1
	 **/
	public MultiStringValue () {
		this.rgoMultivalues = new ArrayList<String>();
	}

	/**
	 * Add a value at the end of the multivalue.
	 * @param sValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(String sValue) {
		try {
			this.rgoMultivalues.add(sValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Add a value at the specified index.
	 * @param sValue The value to be added.
	 * @param iIndex The position index where the object has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(String sValue, int iIndex) {
		try {
			this.rgoMultivalues.add(iIndex, sValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Append a list of values at the end of the multivalue.
	 * @param rgsValues The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addAllValues(List<String> rgsValues) {		
		int iCounter = 0;
		int iSize = rgsValues.size();
		for(int i = 0; i < iSize; i++)
			iCounter += this.addValue(rgsValues.get(i));
		return iCounter;
	}

	/**
	 * Append a list of values between two positions.
	 * @param rgsValues The list of values to be added.
	 * @param iFromIndex The initial position index.
	 * @param iToIndex The final position index.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValues(List<String> rgsValues, int iFromIndex, int iToIndex) {
		int iCounter = 0;
		int iPos = 0;
		for(int i = iFromIndex; i <= iToIndex; i++){
			iCounter += this.addValue(rgsValues.get(iPos), i);
			iPos += 1;
		}
		return iCounter;
	}

	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be added.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values successfully set.
	 @since 0.1
	 **/
	@Override
	public int setValue(String sValue, int iIndex) {
		try {
			this.rgoMultivalues.set(iIndex, sValue);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// CONVERSION METHODS

	/**
	 * Convert into a multivalue of double values.
	 * If any string is not a numeric representable value, an empty
	 * multivalue will be returned.
	 * @return MultiDoubleValue with the corresponding floating values, or and empty value if
	 * the conversion was not possible.
	 @since 0.1
	 **/
	public MultiDoubleValue toMultiDouble() {
		MultiDoubleValue oResult = new MultiDoubleValue();
		int iSize = this.rgoMultivalues.size();
		String sValue;
		for (int i = 0; i < iSize; i++){
			sValue = this.rgoMultivalues.get(i);
			try{
				oResult.addValue(Double.parseDouble(sValue));
			}
			catch (NumberFormatException e){
				oResult.removeAllValues();
				return oResult;
			}
		}
		return oResult;
	}
}
