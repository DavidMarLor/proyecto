package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.exception.NotAddedValueException;

/**
 * This class, the abstraction of a range column (i.e. a representation of a [min, max] interval), 
 * is the one that should be used by the programmer, since it hides the actual implementation 
 * of the column. Even when the implementation changes, the abstraction must remain unaltered. 
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
 * <li> (JRR|JML, 0.2, March 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, July 2011) 		Initial version. 
 * </ul>
 * <p>
 *
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.2
 *
 **/
public class RangeColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public RangeColumn() {
		super("", ColumnType.Range);
		this.oImpl = new RangeColumnImpl();
	}

	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public RangeColumn(String sName) {
		super(sName, ColumnType.Range);
		this.oImpl = new RangeColumnImpl();
	}

	// CONVERSION METHODS

	/**
	 * Convert into a categorical column where each range is a category (with no repetition).
	 * @return CategoricalColumn associated to the range column.
	 * @throws NotAddedValueException
     @since 0.1 
	 **/
	public CategoricalColumn toCategorical() throws NotAddedValueException {
		return ((RangeColumnImpl)this.oImpl).toCategorical(this.getName());
	}
	
	/**
	 * Convert into NumericalColumn according to a transformation mode
	 * @param iMode An integer between 0 and 3 which indicate a mode of transformation, possible modes are:
	 * 		<ul>
	 * 			<li> 0: Choose the minimum value of the range. </li>
	 * 			<li> 1: Choose the maximum value of the range. </li>
	 * 			<li> 2: Each value will be the arithmetical mean of min and max. </li>
	 * 			<li> 3: A random value in the range will be chosen. </li>
	 * 		</ul>
	 * @return NumericalColumn that corresponds to the range column.
	 * @throws NotAddedValueException  
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(int iMode) throws NotAddedValueException {
		return ((RangeColumnImpl)this.oImpl).toNumerical(this.getName(), iMode);
	}

	/**
	 * Convert into a numerical column according to a Gauss distribution.
	 * @param dMean The arithmetic mean.
	 * @param dStdDev The standard deviation.
	 * @return NumericalColumn that corresponds to the range column according to the Gauss distribution.
	 * @throws NotAddedValueException
	 @since 0.1
	 **/
	public NumericalColumn toNumericalByGaussian(double dMean, double dStdDev) throws NotAddedValueException {
		return ((RangeColumnImpl)this.oImpl).toNumericalByGaussian(this.getName(), dMean, dStdDev);
	}
}
