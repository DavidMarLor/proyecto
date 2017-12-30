package es.uco.kdis.datapro.demos.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

public class DemoDiscretizeFrequency {

	/** Comparator that makes invalidValues less than any other thing, so
	 *  when data is sorted, all invalid values fall at the end of the array.
	 */
	private static Comparator<Object> oComparatorAscendent = new Comparator<Object>() {
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

	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		int numBins = 5;
		
//      List<Double> aSortedColumn = Arrays.asList(2.0, 4. , 4.0, 4.0, 4., 3., 4., 4., 5.0, 2.0, 3.0, 2.0, 6.);
//      List<Double> aSortedColumn = Arrays.asList(2.,5.,5.,5.,5.,5.,6.,7.,8.,8.,9.);
//      List<Double> aSortedColumn = Arrays.asList(4.0, 4. , 4.0, 4.0, 4., 4., 4., 4., 5.0, 2.0, 3.0, 2.0, 6.);
//		List<Double> aSortedColumn = Arrays.asList(1.,2.,3.,3.,4.,5.,8.0, 8. , 8.0, 8.0, 9., 10., 11., 12., 12.0, 12.0);
		List<Double> aSortedColumn = Arrays.asList(1.,2.,3.,4.,5.,5.,5.,5.,5.,5.,5.,5.,7.);
		
		Collections.sort(aSortedColumn, oComparatorAscendent);
		ArrayList<ArrayList<Double>> bins = new ArrayList<ArrayList<Double>>(numBins);
		for(int i = 0; i < numBins; i++)
			bins.add(new ArrayList<Double>());
		
		int size = aSortedColumn.size();
		int iSize = size;
		int numBinsOrig = numBins;
		int numElements = 0;
		int elementBins = (int) Math.rint((double)size/(double)numBins);
		int j = 0;
		double element = Double.NaN;
		for(int i = 0; i < iSize ; i++)
		{
			if(aSortedColumn.get(i).doubleValue() == element)
			{
				bins.get(j).add(aSortedColumn.get(i));
				numElements++;
				size--;
			}
			else if (numElements <= elementBins )
			{
				int count = 1;
				for(int k=i+1; k<iSize; k++)
					if(aSortedColumn.get(i).doubleValue() == aSortedColumn.get(k).doubleValue())
						count++;

				if((count+numElements) <= elementBins)
				{
					for(int k = 0; k<count; k++)
						bins.get(j).add(aSortedColumn.get(k+i));

					numElements += count;
					size -= count;
					i += count-1;
				}
				else
				{
					numBins -= 1;
					elementBins = (int) Math.rint((double)size/(double)numBins);
					j++;
					for(int k = 0; k<count; k++)
						bins.get(j).add(aSortedColumn.get(k+i));

					i += count-1;
					numElements = count;
					size -= count;
				}
			}
			else
			{
				numBins -= 1;
				elementBins = (int) Math.rint((double)size/(double)numBins);
				j++;
				bins.get(j).add(aSortedColumn.get(i));
				numElements = 1;
				size -= 1;
			}
			element = aSortedColumn.get(i);
		}
		
		for(int i = 0; i < numBinsOrig; i++)
		{
			if(bins.get(i).size()==0)
			{
				for(int k = 0; k < i; k++)
				{
					int zSize = bins.get(k).size();
					int z = zSize-1;
					double elementMove = bins.get(k).get(z);
					double elementComp = bins.get(k).get(z);
					for(; z > 0 && elementMove == elementComp; )
					{
						
						bins.get(k+1).add(0, elementMove);
						bins.get(k).remove(elementMove);
						z--;
						elementComp = bins.get(k).get(z);
					}
				}
			}
		}
		

		for( ArrayList<Double> oList : bins)
		{
			System.out.println("new bins");
			for(Double oValue : oList)
			{
				System.out.println(oValue);
			}
		}
	}
}