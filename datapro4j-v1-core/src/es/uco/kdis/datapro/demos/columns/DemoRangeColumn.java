package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.column.RangeColumn;
import es.uco.kdis.datapro.datatypes.DoubleRange;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Demo program for
 * Range Column management.
 * */
public class DemoRangeColumn {
	public static void main(String[] args) throws NotAddedValueException{
		
		RangeColumn oRangeColumn = new RangeColumn();
		oRangeColumn.setName("myRangeColumn");
		
		DoubleRange oMyRange;
		ArrayList<Object> rgoMyRanges = new ArrayList<Object>();
		double dValue1, dValue2;
		Random oRnd = new Random(System.currentTimeMillis());
		
		// Add 10 values
		for(int i=0; i<10; i++){
			dValue1 = oRnd.nextDouble()*10;
			dValue2 = oRnd.nextDouble()*10;
			oMyRange = new DoubleRange();
			
			if(dValue1 > dValue2){
				oMyRange.setMaxValue(dValue1);
				oMyRange.setMinValue(dValue2);
				oMyRange.setOpenMax(true);
				oMyRange.setOpenMin(true);
			}
			else{
				oMyRange.setMaxValue(dValue2);
				oMyRange.setMinValue(dValue1);
				oMyRange.setOpenMax(false);
				oMyRange.setOpenMin(false);
			}			
			rgoMyRanges.add(oMyRange);
		}
		
		oRangeColumn.addAllValues(rgoMyRanges);
		
		// Add some invalid values
		oRangeColumn.addValue(MissingValue.getMissingValue(), 4);
		oRangeColumn.addValue(EmptyValue.getEmptyValue(), 2);
		oRangeColumn.addValue(NullValue.getNullValue(), 7);
		
		// Get information of the column
		System.out.println("\n----------Column Information---------------");
		System.out.println("Name: " + oRangeColumn.getName());
		System.out.println("Type: " + oRangeColumn.getType());
		System.out.println("Size: " + oRangeColumn.getSize());
		System.out.println("Number of invalid values: " + oRangeColumn.countInvalidValues());
		System.out.println("Number of empty values: " + oRangeColumn.countEmptyValues());
		System.out.println("Number of null values: " + oRangeColumn.countNullValues());
		System.out.println("Number of missing values: "+oRangeColumn.countMissingValues());
		
		// Show column values
		System.out.println("\nValues in the column: ");
		List<Object> oValues = oRangeColumn.getValues();
		int iSize = oValues.size();
		for(int i=0; i<iSize; i++)
			System.out.println(oValues.get(i));
		
		System.out.println("\n----------Conversion methods-----------------");
		
		CategoricalColumn oCategoricalColumn = oRangeColumn.toCategorical();
		System.out.println("\nValues after convert to categorical column:");
		oValues = oCategoricalColumn.getCategoryList();
		System.out.println("List of categories: " + oValues.toString());
		
		
		NumericalColumn oNumericalColumn = oRangeColumn.toNumerical(0);
		oValues = oNumericalColumn.getValues();
		System.out.println("\nValues after convert to numerical column (choose minimum value in the range):");
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oNumericalColumn = oRangeColumn.toNumerical(1);
		oValues = oNumericalColumn.getValues();
		System.out.println("\nValues after convert to numerical column (choose maximum value in the range):");
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oNumericalColumn = oRangeColumn.toNumerical(2);
		oValues = oNumericalColumn.getValues();
		System.out.println("\nValues after convert to numerical column (choose the mean value in the range):");
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oNumericalColumn = oRangeColumn.toNumerical(3);
		oValues = oNumericalColumn.getValues();
		System.out.println("\nValues after convert to numerical column (choose a random value in the range):");
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oNumericalColumn = oRangeColumn.toNumericalByGaussian(0, 1);
		oValues = oNumericalColumn.getValues();
		System.out.println("\nValues after convert to numerical column using a gaussian distribution:");
		for(Object oValue : oValues)
			System.out.println(oValue);

	}
}
