package es.uco.kdis.datapro.dataset.column;

/**
 * This class, the abstraction of a nominal column (a representation of a free-style string), is
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
 * <li>	(AMP|ARQ, 0.3, May 2014)	New method: getMaxValueLength
 * <li> (JRR|JML, 0.2,  March 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) 		Initial version. 
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.3)
 @author Antonio Jose Muñoz Perez (AMP, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 *
 **/
public class NominalColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public NominalColumn() {
		super("", ColumnType.Nominal);
		this.oImpl = new NominalColumnImpl();
	}

	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public NominalColumn(String sName) {
		super(sName, ColumnType.Nominal);
		this.oImpl = new NominalColumnImpl();
	}

	/**
	 * Get the number of values of the category
	 * with the maximum number of values.
	 * @return The maximum number of values for any category of the column.
	 @since 0.3
	 */
	public int getMaxValueLength() {
		return ((NominalColumnImpl)this.oImpl).getMaxValueLength();
	}
	
	// CONVERSION METHODS

	/**
	 * Convert into a categorical column.
	 * Each string is a category (with no repetition).
	 * @return  CategoricalColumn associated to the nominal column.
	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(){
		return ((NominalColumnImpl)this.oImpl).toCategorical(this.getName());
	}

	/**
	 * Convert into a numerical column.
	 * It creates the numerical representation of the string given, note if any string is not a numeric
	 * representable value, then an emptyValue will be added.
	 * @return NumericalColumn with the string numerical representation.
	 * Notes: the numeric column returned has no bounds.
	 @since 0.1
	 **/
	public NumericalColumn toNumerical(){
		return ((NominalColumnImpl)this.oImpl).toNumerical(this.getName());
	}
}
