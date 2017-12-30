package es.uco.kdis.datapro.datatypes;

/**
 * This class implements a template for any kind of Range. 
 * Intervals can have min and max values, and be open or closed.
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
 * @param <C> 
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 **/
public abstract class Range<C> {

	/**
	 * <code>oMinValue</code> is the 
	 * minimum value in the interval.
     @since 0.1
	 **/
	protected C oMinValue;

	/**
	 * <code>oMaxValue</code> is the 
	 * maximum value in the interval.
     @since 0.1
	 **/
	protected C oMaxValue;

	/**
	 * <code>bOpenMin</code> is true if the 
	 * min interval is open.
     @since 0.1
	 **/
	protected boolean bOpenMin;

	/**
	 * <code>bOpenMax</code> is true if the 
	 * max interval is closed.
     @since 0.1
	 **/
	protected boolean bOpenMax;

	/**
	 * Get if the min interval is open.
	 * @return True if the min interval is open.
     @since 0.1
	 **/
	public boolean isOpenMin() {
		return this.bOpenMin;
	}

	/**
	 * Set the min interval to open or close.
	 * @param bOpenMin True if open.
     @since 0.1
	 **/
	public void setOpenMin(boolean bOpenMin) {
		this.bOpenMin = bOpenMin;
	}

	/**
	 * Get if the max interval is open.
	 * @return True if the max interval is open.
     @since 0.1
	 **/
	public boolean isOpenMax() {
		return this.bOpenMax;
	}

	/**
	 * Set the max interval to open or close
	 * @param bOpenMax True if open
     @since 0.1
	 **/
	public void setOpenMax(boolean bOpenMax) {
		this.bOpenMax = bOpenMax;
	}

	/**
	 * Get the min value.
	 * @return The min value in the interval.
     @since 0.1
	 **/
	public C getMinValue() {
		return this.oMinValue;
	}

	/**
	 * Set the min value.
	 * @param oMin The new min value.
     @since 0.1
	 **/
	public void setMinValue(C oMin) {
		this.oMinValue = oMin;
	}

	/**
	 * Get the max value.
	 * @return The max value in the interval.
     @since 0.1
	 **/
	public C getMaxValue() {
		return this.oMaxValue;
	}

	/**
	 * Set the max value.
	 * @param oMax The new max value.
     @since 0.1
	 **/
	public void setMaxValue(C oMax) {
		this.oMaxValue = oMax;        
	}
}
