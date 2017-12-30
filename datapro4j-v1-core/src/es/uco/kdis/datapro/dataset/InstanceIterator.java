package es.uco.kdis.datapro.dataset;

import es.uco.kdis.datapro.dataset.column.ColumnAbstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * InstanceIterator is the class that implements the interface IIterator. This 
 * class represents an iterator to access each row/instance in a dataset. Every
 * InstanceIterator is linked to a dataset.
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
 * <li> (JTL, 0.1, May 2011) Initial version. Intrusion strategies.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.2
 *
 **/
public class InstanceIterator implements IIterator {

	/**
	 * <code>sRow</code> stores the row/instance of the iterator
	 @since 0.1
	 **/
	private int iRow = 0;

	/**
	 * <code>oData</code> stores the dataset associated.
	 @since 0.1
	 **/
	private Dataset oData;

	/**
	 * Instance-iterator constructor. Set the dataset associated with
	 * the iterator.
	 * @param oDataset The <code>Dataset</code> to be associated with.
	 @since 0.1
	 @see Dataset
	 **/
	public InstanceIterator(Dataset oDataset) {
		this.oData = oDataset;
	}

	/**
	 * Obtain the first row/instance of a dataset associated.
	 * @return The list with the first row/instance
	 @since 0.1
	 @see Dataset
	 @see iRow
	 **/
	@Override
	public List<Object> first() {
		//Initialize to the first row/instance of the iterator
		this.iRow = 0;
		//Now, currentInstance can be called
		return this.currentInstance();
	}

	/**
	 * Increment <code>iRow</code> if there are more instances in 
	 * the dataset
	 @since 0.1
	 @see iRow
	 **/
	@Override
	public void next() {
		if (!isDone())
			this.iRow++;
	}

	/**
	 @return TRUE if the dataset has no more instances. FALSE otherwise.
	 @since 0.1
	 @see iRow
	 @see oData
	 **/
	@Override
	public boolean isDone() {
		return (this.iRow >= this.oData.getColumn(0).getSize());
	}

	/**
	 * Obtain the current row/instance of the dataset. This row/instance 
	 * is pointed by <code>iRow</code>.
	 @since 0.1
	 @see iRow
	 @see oData
	 **/
	@Override
	public List<Object> currentInstance() {
		ArrayList<Object> rgoRow = new ArrayList<Object>();

		for (ColumnAbstraction column : oData.getColumns())
			rgoRow.add(column.getElement(this.iRow));

		return rgoRow;
	}
}