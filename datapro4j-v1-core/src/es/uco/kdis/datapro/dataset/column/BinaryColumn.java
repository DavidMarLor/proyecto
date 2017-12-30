package es.uco.kdis.datapro.dataset.column;

/**
 * This class represents the abstraction of a binary column. It defines
 * the methods to manipulate a binary column. 
 * <p>
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
 * <li> (JRR|JML, 0.2, September 2011) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, July 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.2
 *
 **/
public class BinaryColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public BinaryColumn() {
		super("", ColumnType.Binary);
		this.oImpl = new BinaryColumnImpl();
	}

	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public BinaryColumn(String sName) {
		super(sName, ColumnType.Binary);
		this.oImpl = new BinaryColumnImpl();
	}

	// CONVERSION METHODS 

	/**
	 * Convert into a categorical column.
	 * 
	 * @param sFalseCategory The category equivalent to a false binary value.
	 * @param sTrueCategory The category equivalent to a true binary value.
	 * @return CategoricalColumn that defines two categories, one per each binary value (true,false).
	 * <p>Notes: 
	 * <ul>
	 * 		<li>If a value is a empty/missing value then a false value will be added.</li>
	 * 		<li>If a value is null, then a null value will be added.</li>
	 * </ul>
 	 @since 0.1
	 **/
	public CategoricalColumn toCategorical(String sFalseCategory, String sTrueCategory){
		return ((BinaryColumnImpl)this.oImpl).toCategorical(this.getName(), sFalseCategory, sTrueCategory);
	}
}
