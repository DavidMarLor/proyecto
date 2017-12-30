package es.uco.kdis.datapro.algorithm.preprocessing.discretization;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.datatypes.DoubleRange;
import es.uco.kdis.datapro.datatypes.InvalidValue;

/**
 * Equal-width discretization of a given numerical/integer column of the dataset. 
 * A RangeColumn is returned.
 * 
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
 * <li> (JRR, 0.1, March 2012) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 *
 **/
public class EqualWidthDiscretization extends DatasetStrategy {

	/**
	 * <code>sResName</code> is the name 
	 * of the resulting column.
     @since 0.1
	 **/
	protected String sResName;

	/**
	 * <code>sColName</code> is the name 
	 * of the column to discretize.
     @since 0.1
	 **/
	protected String sColName;

	/**
	 * <code>iBins</code> is the number of bins.
     @since 0.1
	 **/
	protected int iBins;

	/**
	 * <code>oCol</code> is the column to discretize.
     @since 0.1
	 **/
	protected NumericalColumn oCol;

	/**
	 * <code>oRangeColumn</code> is the column as result.
     @since 0.1
	 **/
	protected RangeColumn oRangeColumn;

	/**
	 * Parameterized constructor
	 * @param oDataset The dataset to work with.
	 * @param iBins The number of bins.
	 * @param sColName The name of the column to be discretized.
	 * @param sResName The name of the resultant column.
     @since 0.1
	 **/
	public EqualWidthDiscretization (Dataset oDataset, int iBins, String sColName, String sResName) {
		super.setDataset(oDataset);
		this.iBins = iBins;
		this.sColName = sColName;
		this.sResName = sResName;
	}

	/**
	 * Initialization method.
     @since 0.1 
	 **/
	@Override
	public void initialize() {
		ColumnAbstraction oColAbs = this.getDataset().getColumnByName(this.sColName);
		if (oColAbs.getType() == ColumnType.Integer)
			this.oCol = ((IntegerColumn)oColAbs).toNumerical();
		else if (oColAbs.getType() == ColumnType.Numerical)
			this.oCol = (NumericalColumn)oColAbs;
		else 
			throw new UnsupportedOperationException("This strategy is only supported for Integer and Numerical columns");

		if (this.iBins < 1)
			throw new IndexOutOfBoundsException("Specified number of bins is out of range");
	}

	/**
	 * This methods creates a new RangeColumn taking both the intervals given
	 * as parameter and the values comprised by the original numerical column.
	 * @param aoRanges Array of intervals
	 * @param sName Name of the new column
	 * @return The range column created
     @since 0.1
	 **/
	protected RangeColumn calculateDRangeColumn (DoubleRange[] aoRanges, String sName) {
		int iColSize = this.oCol.getSize();
		Object oValue;
		RangeColumn oResultCol = new RangeColumn(sName);

		for (int i = 0; i < iColSize; i++) {
			oValue = this.oCol.getElement(i);
			if (oValue instanceof InvalidValue)
				oResultCol.addValue(oValue);
			else {
				for (int j = 0; j < aoRanges.length; j++) {
					if (aoRanges[j].hasValue(((Double)oValue).doubleValue())) {
						oResultCol.addValue(aoRanges[j]);
						break;
					}                          
				}
			}
		}

		return oResultCol;
	}    

	/**
	 * The execution method.
     @since 0.1
	 **/
	@Override
	public void execute() {          
		/* Get the minimum and maximum value of the column */
		double dMinValue = this.oCol.getMinValue();
		double dMaxValue = this.oCol.getMaxValue();
		DoubleRange[] aoCutPoints = new DoubleRange[this.iBins];

		// Only one interval is requested
		if (this.iBins == 1) {
			aoCutPoints[0] = new DoubleRange(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			// More than one interval is requested
		} else {
			// Calculating cut-points...
			double dBinWidth = (dMaxValue-dMinValue)/this.iBins;
			// Creating DoubleRanges for these cut-points
			aoCutPoints[0] = new DoubleRange(Double.NEGATIVE_INFINITY, dBinWidth+dMinValue);
			for (int i = 1; i < this.iBins-1; i++) {
				aoCutPoints[i] = new DoubleRange((i*dBinWidth+dMinValue), ((i+1)*dBinWidth+dMinValue));
				aoCutPoints[i].setOpenMin(true);
			}
			aoCutPoints[this.iBins-1] = new DoubleRange(((this.iBins-1)*dBinWidth+dMinValue), Double.POSITIVE_INFINITY);
			aoCutPoints[this.iBins-1].setOpenMin(true);
		}

		// Creating the RangeColumn
		this.oRangeColumn = this.calculateDRangeColumn(aoCutPoints, this.sResName);

		// Any other execution would return the same column
		this.setExecutable(false);
	}

	/**
	 * Post execution method.
     @since 0.1
	 **/
	@Override
	public void postexec() {
		// Do nothing
	}

	/**
	 * Return the result.
	 * @return The range column
     @since 0.1
	 **/
	@Override
	public Object getResult() {
		return this.oRangeColumn;
	}

}
