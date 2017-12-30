package es.uco.kdis.datapro.dataset.column;

import java.text.SimpleDateFormat;

/**
 * DateColumn implements the abstraction of a column comprised by
 * instances of type Date.
 * This type of column is required by ARFF datasets.
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
 * <li> (JML, 0.2) Date format specification.
 * <li> (JRR, 0.1) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 *
 **/

public class DateColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public DateColumn() {
		super("", ColumnType.Date);
		this.oImpl = new DateColumnImpl();
	}

	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public DateColumn(String sName) {
		super(sName, ColumnType.Date);
		this.oImpl = new DateColumnImpl();
	}
	
	/**
	 * Add a date specification.
	 * @param sDate Specification of the date.
	 @since 0.2
	 **/
	public void addDateSpecification(SimpleDateFormat oDate) {
		((DateColumnImpl) this.oImpl).addDateSpecification(oDate);
	}
	
	/**
	 * Return the date specification.
	 * @return The date specification.
	 @since 0.2
	 **/
	public SimpleDateFormat getDateSpecification() {
		return ((DateColumnImpl) this.oImpl).getDateSpecificaiton();
	}
}