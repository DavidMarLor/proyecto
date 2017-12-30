package es.uco.kdis.datapro.dataset.column;

/**
 * This class, the abstraction of a numerical column, is
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
 * <li> (JRR|ARQ, 0.3, March 2012) 	Performance, refactoring and designing issues. Improvements. Removed set/getXInterval methods.
 * 									New methods: standardDevitation(), toNominal(), toInteger().
 * <li> (MOB, 0.2, June 2011) 		New methods: getMin(), getMax(), discretizeByAmplitude(), discretizeByEqualWidth(), discretizeByEqualFrequency()
 * <li> (JTL, 0.1,  February 2011) 	Initial version
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.3)
 @author Aurora Ramirez Quesada (ARQ, 0.3)
 @author Mario Orozco Borrego (MOB, 0.2)
 @author Jose de la Torre Lopez (MOB, 0.2)
 @version 0.3
 *
 **/
public class NumericalColumn extends ColumnAbstraction {

	/**
	 * <code>dMinInterval</code> is the minimum 
	 * value in the interval.
     @since 0.1
	 **/
	protected Double dMinInterval = Double.MIN_VALUE;

	/**
	 * <code>dMaxInterval</code> is the maximum
	 * value in the column.
     @since 0.1
	 **/
	protected Double dMaxInterval = Double.MAX_VALUE;

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public NumericalColumn() {
		super("", ColumnType.Numerical);
		oImpl = new NumericalColumnImpl();
	}

	/**
	 * Parameterized Constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public NumericalColumn(String sName) {
		super(sName, ColumnType.Numerical);
		oImpl = new NumericalColumnImpl();
	}

	/**
	 * Set the minimum value for the valid interval of value.
	 * @param dMinInterval The minimum interval to be set.
	 @throws IllegalAccessException if the value cannot be set.
	 @since 0.1
	 **/
	public void setdMinInterval(Double dMinInterval) throws IllegalAccessException {
		if (dMinInterval > this.getMinValue()) 
			throw new IllegalAccessException ("Value could not be set. A lower value already exists in the column.");
		this.dMinInterval = dMinInterval;
	}

	/**
	 * Get the minimum value in the interval.
	 * @return The minimum value of the interval.
	 @since 0.1
	 **/
	public Double getdMinInterval() {
		return this.dMinInterval;
	}

	/**
	 * Set the maximum value for the valid interval of value.
	 @param dMaxInterval The maximum interval to be set.
	 @throws IllegalAccessException if the value cannot be set.
	 @since 0.1
	 **/
	public void setdMaxInterval(Double dMaxInterval) throws IllegalAccessException {
		if (dMaxInterval < this.getMaxValue())
			throw new IllegalAccessException ("Value could not be set. A greater value already exists in the column.");
		this.dMaxInterval = dMaxInterval;
	}

	/**
	 * Get the maximum value in the interval.
	 @return the minimum value of the interval.
	 @since 0.1
	 **/
	public Double getdMaxInterval() {
		return this.dMaxInterval;
	}

	/**
	 * Get the maximum value in the column.
	 * @return The maximum value of a column.
	 @since 0.2
	 **/
	public double getMaxValue() {
		return ((NumericalColumnImpl) this.oImpl).getMaxValue();
	}

	/**
	 * Get the minimum value in the column.
	 * @return The minimum value of a column.
	 @since 0.2
	 **/
	public double getMinValue() {
		return ((NumericalColumnImpl) this.oImpl).getMinValue();
	}

	/**
	 * Get the mean of the values of the column.
	 * @return The mean of the values of the column.
	 @since 0.1
	 **/
	public double mean() {
		return ((NumericalColumnImpl) this.oImpl).mean();
	}

	/**
	 * Get the standard deviation of the values of the column.
	 * @return The standard deviation of the values of the column.
	 @since 0.3
	 **/
	public double standardDeviation(){
		return ((NumericalColumnImpl) this.oImpl).standardDeviation();
	}

	/**
	 * Normalize all the values of the column.
	 @since 0.1
	 **/
	public void normalize(){
		((NumericalColumnImpl)this.oImpl).normalize();
	}

	/**
	 * Standardize all the values of the column
	 * @param dMean mean
	 * @param dVariance variance
	 @since 0.1
	 **/
	public void standardize(double dMean, double dVariance){
		((NumericalColumnImpl)this.oImpl).standardize(dMean, dVariance);
	}

	// CONVERSION METHODS 

	/**
	 * Convert into a nominal column.
	 * @return NominalColumn with the corresponding string of numerical values.
	 * @since 0.3
	 **/
	public NominalColumn toNominal(){
		return ((NumericalColumnImpl)this.oImpl).toNominal(this.getName());
	}

	/**
	 * Convert into an integer column, according to a mode of conversion.
	 * @param bRoundedValue The conversion mode: if it is false, the values will be truncated, 
	 * if it is true, values will be rounded.
	 * @return IntegerColumn with the corresponding integer values.
	 @since 0.3
	 **/
	public IntegerColumn toInteger(boolean bRoundedValue){
		return ((NumericalColumnImpl)this.oImpl).toInteger(this.getName(), bRoundedValue);
	}
}
