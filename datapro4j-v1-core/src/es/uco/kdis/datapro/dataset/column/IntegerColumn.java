package es.uco.kdis.datapro.dataset.column;

/**
 * This class, the abstraction of a integer column (a specialization of a numerical column), is 
 * the one that should be used by the programmer, since it hides the actual implementation of the column. 
 * Even when the implementation changes, the abstraction must remain unaltered. 
 *
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
 * <li> (JRR|JML|ARQ, 0.2,  April 2012) Performance, refactoring and designing issues. Improvements. 
 * 										Modified methods: setiXInterval().
 * 										New methods: mean(), standardDeviation().
 * <li> (MOB, 0.1. June 2011) 			Initial version. 
 * </ul>
 * <p>
 *
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.2
 *
 **/
public final class IntegerColumn extends NumericalColumn {

	/**
	 * Default constructor
	 @since 0.1
	 **/
	public IntegerColumn() {
		this.sName = "";
		this.ctColumnType = ColumnType.Integer;
		this.oImpl = new IntegerColumnImpl();
	}

	/**
	 * Parameterized Constructor.
	 * @param sName The Name of the column.
	 @since 0.1
	 **/
	public IntegerColumn(String sName) {
		this.sName = sName;
		this.ctColumnType = ColumnType.Integer;
		this.oImpl = new IntegerColumnImpl();
	}

	/**
	 * Get the minimum value of the interval.
	 * @return The minimum value of the interval.
      @since 0.1
	 **/
	public Integer getiMinInterval() {
		return new Integer(this.dMinInterval.intValue());
	}
	
	/**
	 * Set the minimum value for the valid interval of value.
	 * @param iMinInterval The minimum value of the interval to be set.
	 @throws IllegalAccessException  if the value cannot be set.
	 @since 0.1
	 **/
	public void setiMinInterval(Integer iMinInterval) throws IllegalAccessException {
		if (iMinInterval > this.getMinValue()) 
			throw new IllegalAccessException ("Value could not be set. A lower value already exists in the column.");
		this.dMinInterval = iMinInterval.doubleValue();
	}
	
	/**
	 * Get the maximum value of the interval.
	 * @return The maximum value of the interval.
      @since 0.1
	 **/
	public Integer getiMaxInterval() {
		return new Integer(this.dMaxInterval.intValue());
	}
	
	/**
	 * Set the maximum value for the valid interval of value.
	 * @param iMaxInterval The maximum in the interval to be set.
	 * @throws IllegalAccessException if the value cannot be set.
      @since 0.1
	 **/
	public void setiMaxInterval(Integer iMaxInterval) throws IllegalAccessException {
		if (iMaxInterval < this.getMaxValue())
			throw new IllegalAccessException ("Value could not be set. A greater value already exists in the column.");
		this.dMaxInterval = iMaxInterval.doubleValue();
	}

	/**
	 * Get the maximum value of the column.
	 * @return The maximum value of a column.
	 @since 0.1
	 **/
	@Override
	public double getMaxValue() {
		return ((IntegerColumnImpl) this.oImpl).getMaxValue();
	}

	/**
	 * Get the minimum value of the column.
	 * @return The minimum value of a column.
	 @since 0.1
	 **/
	@Override
	public double getMinValue() {
		return ((IntegerColumnImpl) this.oImpl).getMinValue();
	}
	
	/**
	 * Get the mean of the values of the column.
	 * @return The mean of the values of the column.
	 @since 0.2
	 **/
	@Override
	public double mean() {
		return ((IntegerColumnImpl) this.oImpl).mean();
	}

	/**
	 * Get the standard deviation of the values of the column.
	 * @return The standard deviation of the values of the column.
	 @since 0.2
	 **/
	@Override
	public double standardDeviation(){
		return ((IntegerColumnImpl) this.oImpl).standardDeviation();
	}
	
	// CONVERSION METHODS

	/**
	 * Convert into a categorical column where each value corresponds to 
	 * a single category (with no repetition).
	 * @return CategoricalColumn that corresponds to the integer column.
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(){
		return ((IntegerColumnImpl)this.oImpl).toCategorical(this.getName());
	}

	/**
	 * Convert the column into a numerical column, where each value is convert to floating number.
	 * @return NumericalColumn with the corresponding floating values.
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(){
		return ((IntegerColumnImpl)this.oImpl).toNumerical(this.getName());
	}   
}