package es.uco.kdis.datapro.dataset.column;

import java.text.SimpleDateFormat;

/**
 * This class represents the abstraction of a long column (a specialization of a numerical column).
 * and defines the methods to manipulate the column. It is the class that should be used 
 * by the programmer, since it hides the actual implementation of the column. 
 * Even when the implementation changes, the abstraction must remain unaltered. 
 *
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
 * <li> (IMA|RPB, 0.1, December 2013)	Initial version. 
 * </ul>
 * <p>
 * 
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 *
 **/
public class LongColumn extends NumericalColumn {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public LongColumn() {
		this.sName = "";
		this.ctColumnType = ColumnType.Long;
		this.oImpl = new LongColumnImpl();
	}

	/**
	 * Parameterized Constructor.
	 * @param sName The Name of the column.
	 @since 0.1
	 **/
	public LongColumn(String sName) {
		this.sName = sName;
		this.ctColumnType = ColumnType.Long;
		this.oImpl = new LongColumnImpl();
	}

	/**
	 * Get the minimum value of the interval.
	 * @return The minimum value of the interval.
     @since 0.1
	 **/
	public Long getlMinInterval() {
		return new Long(this.dMinInterval.longValue());
	}
	
	/**
	 * Set the minimum value for the valid interval of values.
	 * @param lMinInterval The minimum value of the interval to be set.
	 * @throws IllegalAccessException  if the value cannot be set.
	 @since 0.1
	 **/	 
	public void setlMinInterval(Long lMinInterval) throws IllegalAccessException {
		if (lMinInterval > this.getMinValue()) 
			throw new IllegalAccessException ("Value could not be set. A lower value already exists in the column.");
		this.dMinInterval = lMinInterval.doubleValue();
	}

	/**
	 * Get the maximum value of the interval.
	 * @return The maximum value of the interval.
     @since 0.1
	 **/
	public Long getlMaxInterval() {
		return new Long(this.dMaxInterval.longValue());
	}
	
	/**
	 * Set the maximum value for the valid interval of values.
	 * @param lMaxInterval The maximum value of the interval to be set.
	 * @throws IllegalAccessException if the value cannot be set.
     @since 0.1
	 **/
	public void setlMaxInterval(Long lMaxInterval) throws IllegalAccessException {
		if (lMaxInterval < this.getMaxValue())
			throw new IllegalAccessException ("Value could not be set. A greater value already exists in the column.");
		this.dMaxInterval = lMaxInterval.doubleValue();
	}

	/**
	 * Get the maximum value of the column.
	 * @return The maximum value of the column.
	 @since 0.1
	 **/
	@Override
	public double getMaxValue() {
		return ((LongColumnImpl) this.oImpl).getMaxValue();
	}

	/**
	 * Get the minimum value of the column.
	 * @return The minimum value of the column.
	 @since 0.1
	 **/
	@Override
	public double getMinValue() {
		return ((LongColumnImpl) this.oImpl).getMinValue();
	}

	/**
	 * Get the mean of the values in the column.
	 * @return The mean of the values in the column.
	 @since 0.1
	 **/
	@Override
	public double mean() {
		return ((LongColumnImpl) this.oImpl).mean();
	}

	/**
	 * Get the standard deviation of the values in the column.
	 * @return The standard deviation of the values in the column.
	 @since 0.1
	 **/
	@Override
	public double standardDeviation(){
		return ((LongColumnImpl) this.oImpl).standardDeviation();
	}

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column, where each value corresponds to 
	 * a single category (with no repetition).
	 * @return CategoricalColumn that corresponds to the integer column.
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(){
		return ((LongColumnImpl)this.oImpl).toCategorical(this.getName());
	}

	/**
	 * Convert into a numerical column, where each value is converted to a floating number.
	 * @return NumericalColumn with the corresponding floating values.
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(){
		return ((LongColumnImpl)this.oImpl).toNumerical(this.getName());
	}
	 
	/**
	 * Convert into an integer column, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode for values exceeding the integer boundary: 
	 * if it is false, the values will be set as MissingValue, 
	 * otherwise, values will be rounded to Integer.MIN_VALUE / Integer.MAX_VALUE.
	 * @return IntegerColumn with the corresponding integer values.
	 @since 0.1
	 **/
	public IntegerColumn toInteger(boolean bRoundedValue){
		return ((LongColumnImpl)this.oImpl).toInteger(this.getName(), bRoundedValue);
	}
	
	/** 
	 * Convert into a date column.
	 * @param oFormat The date specification format.
	 * @return DateColumn with the corresponding date values.
	 @since 0.1
	 */
	public DateColumn toDate(SimpleDateFormat oFormat){
		return ((LongColumnImpl)this.oImpl).toDate(this.getName(), oFormat);
	}
}
