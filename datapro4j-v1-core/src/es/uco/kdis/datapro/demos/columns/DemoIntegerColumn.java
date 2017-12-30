package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.column.IntegerColumn;
import es.uco.kdis.datapro.dataset.column.NominalColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.source.KeelDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;

import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Demo program for
 * Integer Column management.
 * */
public class DemoIntegerColumn {
	public static void main(String[] args) 
			throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException, IllegalAccessException
	{
		Dataset data = new KeelDataset(args[0]);
		((KeelDataset) data).readDataset();
		
		System.out.println("\nDataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do{
			System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		int iSize = data.getColumns().size();
		IntegerColumn oIntegerColumn;
		List<Object> oValues;
		for(int i=0; i<iSize; i++)
		{
			//Get each column and its values
			oIntegerColumn = (IntegerColumn)data.getColumn(i);
			oValues = oIntegerColumn.getValues();
			
			System.out.println("\n----------Column Information---------------");
			System.out.println("Name: " + oIntegerColumn.getName());
			System.out.println("Size: " + oIntegerColumn.getSize());
			System.out.println("Number of invalid values: " + oIntegerColumn.countInvalidValues());
			System.out.println("Number of empty values: " + oIntegerColumn.countEmptyValues());
			System.out.println("Number of null values: " + oIntegerColumn.countNullValues());
			System.out.println("Number of missing values: "+oIntegerColumn.countMissingValues());
			System.out.println("Interval: [" + oIntegerColumn.getdMinInterval() + " , " +  oIntegerColumn.getdMaxInterval() + " ]");
			System.out.println("Minimum value: " + oIntegerColumn.getMinValue() + " Maximum value: " + oIntegerColumn.getMaxValue());
			
			System.out.println("Mean: " + oIntegerColumn.mean());
			System.out.println("Standard Deviation: " + oIntegerColumn.standardDeviation());
			System.out.println("Values: ");
			
			for(Object oValue : oValues)
				System.out.println(oValue);
		}
		
		// Values operations
		System.out.println("\n----------Set, add and remove values-----------------");
		oIntegerColumn = (IntegerColumn)data.getColumn(0);
		
		// Set value
		System.out.println("Third element: " +  oIntegerColumn.getElement(2));
		// First, change the interval
		if(oIntegerColumn.getiMinInterval() > 1)
			oIntegerColumn.setiMinInterval(new Integer(1));
		oIntegerColumn.setValue(new Integer(1), 2);	// Change the null value
		System.out.println("Third element after setValue: " +  oIntegerColumn.getElement(2));
		
		// Remove and add
		System.out.println("Size before removeValue: " +  oIntegerColumn.getSize());
		oIntegerColumn.removeValue(oIntegerColumn.getSize()-1);
		System.out.println("Size after removeValue: " +  oIntegerColumn.getSize());
		System.out.println("Last element after removeValue: " +  oIntegerColumn.getElement(oIntegerColumn.getSize()-1));
		// First, change the interval
		if(oIntegerColumn.getiMaxInterval() < 12)
			oIntegerColumn.setiMaxInterval(12);
		oIntegerColumn.addValue(new Integer(12),  oIntegerColumn.getSize());
		System.out.println("Last element after addValue: " +  oIntegerColumn.getElement(oIntegerColumn.getSize()-1));
		System.out.println("Size after addValue: " +  oIntegerColumn.getSize());
		
		
		System.out.println("\n----------Conversion methods-----------------");
		
		// To nominal
		NominalColumn oNominalColumn = ((IntegerColumn)data.getColumn(0)).toNominal();
		System.out.println("\nValues after convert to nominal the first column:");
		oValues = oNominalColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		// To categorical
		CategoricalColumn oCategoricalColumn = ((IntegerColumn)data.getColumn(0)).toCategorical();
		List<Object> oCategoryList = oCategoricalColumn.getCategoryList();
		System.out.println("\nList of categories: "+oCategoryList.toString());
				
		// To numerical
		NumericalColumn oNumericalColumn = ((IntegerColumn)data.getColumn(1)).toNumerical();
		System.out.println("\nValues after convert to numerical the second column:");
		oValues = oNumericalColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
	}
}
