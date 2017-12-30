package es.uco.kdis.datapro.algorithm.preprocessing.discretization;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Equal-frequency discretization of a given numerical/integer column of the dataset. 
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
 * <li> (JRR, 0.1, April 2012) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.1
 *
 **/
public class EqualFrequencyDiscretization extends EqualWidthDiscretization {

	/** 
	 * Comparator that makes invalidValues less than any other thing, so
	 * when data is sorted, all invalid values fall at the end of the array.
	 @since 0.1
	 **/
	private Comparator<Object> oComparatorAscendent = new Comparator<Object>() {
		@Override
		public int compare(Object oArg0, Object oArg1) {	
			/**
			 * If arg0 > arg1, return 1
			 * If arg0 == arg1, return 0
			 * If arg0 < arg1, return -1
			 */
			if (oArg0 == null) {
				// Arg0 == null, Arg1 == null
				if (oArg1 == null)
					return 0;
				// Arg0 == null, Arg1::Invalid
				else if (oArg1 instanceof InvalidValue)
					return 0;
				// Arg0 == null, Arg1::Number
				else
					return -1;
			}

			if (oArg1 == null) {
				// Arg0:: InvalidValue, Arg1:: null
				if (oArg0 instanceof InvalidValue)
					return 0;
				// Arg0::Number, Arg1:: null
				else 
					return 1;
			}

			if (((Double) oArg0).doubleValue() > ((Double) oArg1).doubleValue())
				return 1;
			else if (((Double) oArg0).doubleValue() < ((Double) oArg1).doubleValue())
				return -1;
			else 
				return 0;
		}
	};    

	/**
	 * Parameterized constructor
	 * @param oDataset The dataset to work with.
	 * @param iBins The number of bins
	 * @param sColName The name of the column to be discretized.
	 * @param sResName The name of the resulting column.
     @since 0.1 
	 **/    
	public EqualFrequencyDiscretization(Dataset oDataset, int iBins, String sColName, String sResName) {
		super(oDataset, iBins, sColName, sResName);
	}

	/**
	 * The execution method.
     @since 0.1
	 **/
	@Override
	public void execute() {          
		Object oValue;
		/* A new collection is created comprising ordered data without invalid values */
		ArrayList<Object> aSortedColumn = new ArrayList<Object>();
		aSortedColumn.addAll(this.oCol.getValues());
		for (int i = aSortedColumn.size(); i >= 0; i--) {
			oValue = aSortedColumn.get(i);
			if (oValue == null || oValue instanceof InvalidValue)
				aSortedColumn.remove(i);
		}
		// The colection is ordered and counted
		Collections.sort(aSortedColumn, this.oComparatorAscendent);
	}
}
