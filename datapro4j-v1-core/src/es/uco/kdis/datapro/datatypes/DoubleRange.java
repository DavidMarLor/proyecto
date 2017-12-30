package es.uco.kdis.datapro.datatypes;

/**
 * This class implements an interval of double values. Intervals can have min and max values, and be open or closed.
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
 * <li> (JRR, 0.1, March 2011) Initial version. 
 * </ul>
 * <p>
 *
 * @author Jose Raul Romero (JRR, 0.1)
 * @version 0.1
 **/
public class DoubleRange extends Range<Double> {

	// Extends Range for Doubles without addition functionality

	/**
	 * Default constructor. No parameters. Min and max values are 
	 * set to the negative and positive infinite value, respectively.
     @since 0.1
	 **/
	public DoubleRange () {
		this.oMinValue = Double.NEGATIVE_INFINITY;
		this.oMaxValue = Double.POSITIVE_INFINITY;
	}

	/**
	 * Constructor using parameters. Min and max values are set to the parameters.
	 * @param dMin Min value
	 * @param dMax Max value
     @since 0.1
	 **/
	public DoubleRange (double dMin, double dMax) {
		this.oMinValue = dMin;
		this.oMaxValue = dMax;
	}

	/**
	 * Checks whether the parameter value belongs to the interval.
	 * @param dValue The value to be checked.
	 * @return True if the parameter value belongs to this interval.
     @since 0.1
	 **/
	public boolean hasValue (double dValue) {
		if (this.getMinValue() != Double.NEGATIVE_INFINITY) {
			if (this.isOpenMin() &&
					dValue <= this.getMinValue().doubleValue())
				return false;
			if (!this.isOpenMin() &&
					dValue < this.getMinValue().doubleValue())
				return false;
		}
		if (this.getMaxValue() != Double.POSITIVE_INFINITY) {
			if (this.isOpenMax() &&
					dValue >= this.getMaxValue().doubleValue())
				return false;
			if (!this.isOpenMax() &&
					dValue > this.getMaxValue().doubleValue())
				return false;
		}
		return true;
	}

	/**
	 * toString method
	 @since 0.1
	 **/
	@Override
	public String toString () {
		String sMinInter = "[";
		String sMaxInter = "]";
		/* Check whether the min interval is open or not */
		if(this.isOpenMin())
			sMinInter = "(";
		/* Check whether the max interval is open or not */
		if(this.isOpenMax())
			sMaxInter = ")";
		return (sMinInter + this.oMinValue + "," + this.oMaxValue + sMaxInter);
	}
}
