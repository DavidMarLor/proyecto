package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.column.IntegerColumn;
import es.uco.kdis.datapro.dataset.column.NominalColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.source.ArffDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;

import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Demo program for
 * Numerical Column management.
 * */
public class DemoNumericalColumn {
	public static void main(String[] args) 
			throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException, IllegalAccessException
	{
		Dataset data = new ArffDataset(args[0]);
		((ArffDataset) data).setMissingValue("?");
		((ArffDataset) data).setEmptyValue("-");
		((ArffDataset) data).setNullValue("<null>");
		((ArffDataset) data).readDataset("ff");	// Force to load two numerical columns

		System.out.println("\nDataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do{
			System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		int iSize = data.getColumns().size();
		NumericalColumn oNumericalColumn;
		List<Object> oValues;
		double [] dMean = new double[iSize];
		double [] dSD = new double[iSize];
		for(int i=0; i<iSize; i++)
		{
			//Get each column and its values
			oNumericalColumn = (NumericalColumn)data.getColumn(i);
			oValues = oNumericalColumn.getValues();
			
			System.out.println("\n----------Column Information---------------");
			System.out.println("Name: " + oNumericalColumn.getName());
			System.out.println("Size: " + oNumericalColumn.getSize());
			System.out.println("Number of invalid values: " + oNumericalColumn.countInvalidValues());
			System.out.println("Number of empty values: " + oNumericalColumn.countEmptyValues());
			System.out.println("Number of null values: " + oNumericalColumn.countNullValues());
			System.out.println("Number of missing values: "+oNumericalColumn.countMissingValues());
			System.out.println("Interval: [" + oNumericalColumn.getdMinInterval() + " , " +  oNumericalColumn.getdMaxInterval() + " ]");
			System.out.println("Minimum value: " + oNumericalColumn.getMinValue() + " Maximum value: " + oNumericalColumn.getMaxValue());
			
			dMean[i] = oNumericalColumn.mean();
			System.out.println("Mean: " + dMean[i]);
			dSD[i] = oNumericalColumn.standardDeviation();
			System.out.println("Standard Deviation: " + dSD[i]);
			System.out.println("Values: ");
			
			for(Object oValue : oValues)
				System.out.println(oValue);
		}
		
		// Values operations
		System.out.println("\n----------Set, add and remove values-----------------");
		oNumericalColumn = (NumericalColumn)data.getColumn(0);
		
		// Set value
		System.out.println("Third element: " +  oNumericalColumn.getElement(2));
		// First, change the interval
		if(oNumericalColumn.getdMinInterval() > 0.5)
			oNumericalColumn.setdMinInterval(0.5);
		oNumericalColumn.setValue(new Double(0.5), 2);	// Change the null value
		System.out.println("Third element after setValue: " +  oNumericalColumn.getElement(2));
		
		// Remove and add
		System.out.println("Size before removeValue: " +  oNumericalColumn.getSize());
		oNumericalColumn.removeValue( oNumericalColumn.getSize()-1);
		System.out.println("Size after removeValue: " +  oNumericalColumn.getSize());
		System.out.println("Last element after removeValue: " +  oNumericalColumn.getElement( oNumericalColumn.getSize()-1));
		// First, change the interval
		if(oNumericalColumn.getdMaxInterval() < 12.0)
			oNumericalColumn.setdMaxInterval(12.0);
		oNumericalColumn.addValue(new Double(12.0),  oNumericalColumn.getSize());
		System.out.println("Last element after addValue: " +  oNumericalColumn.getElement( oNumericalColumn.getSize()-1));
		System.out.println("Size after addValue: " +  oNumericalColumn.getSize());
		
		// To categorical
		System.out.println("\n----------Conversion methods-----------------");
		
		NominalColumn oNominalColumn = ((NumericalColumn)data.getColumn(0)).toNominal();
		System.out.println("\nValues after convert to nominal the first column:");
		oValues = oNominalColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
				
		IntegerColumn oIntegerColumn = ((NumericalColumn)data.getColumn(1)).toInteger(false);
		System.out.println("\nValues after convert to integer the second column (truncate mode):");
		oValues = oIntegerColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oIntegerColumn = ((NumericalColumn)data.getColumn(1)).toInteger(true);
		System.out.println("\nValues after convert to integer the second column (round mode):");
		oValues = oIntegerColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		System.out.println("\n----------Normalize and Standardize-----------------");
		oNumericalColumn.normalize();
		oValues = oNumericalColumn.getValues();
		System.out.println("Values after normalize first column:");
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oNumericalColumn = (NumericalColumn)data.getColumn(1);
		oNumericalColumn.standardize(dMean[1], (dSD[1]*dSD[1]));
		oValues = oNumericalColumn.getValues();

		System.out.println("\nValues after standardize second column:");
		for(Object oValue : oValues)
			System.out.println(oValue);
	}
}
