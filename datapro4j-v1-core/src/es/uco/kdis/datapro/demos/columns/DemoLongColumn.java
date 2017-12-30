package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.column.DateColumn;
import es.uco.kdis.datapro.dataset.column.IntegerColumn;
import es.uco.kdis.datapro.dataset.column.LongColumn;
import es.uco.kdis.datapro.dataset.column.NominalColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.source.CsvDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Demo program for Long Column management (using a CsvDataset).
 * */
public class DemoLongColumn {
	public static void main(String[] args) 
			throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException, IllegalAccessException
	{
		Dataset oDataset = new CsvDataset(args[0]);
		((CsvDataset) oDataset).readDataset("%%n%v", "ll");

		System.out.println("\nThe dataset is now loaded.");

		//Show the dataset
		InstanceIterator oIterator = new InstanceIterator(oDataset);
		do{
			System.out.println(oIterator.currentInstance());
			oIterator.next();
		} while (!oIterator.isDone());

		int iSize = oDataset.getColumns().size();
		LongColumn oLongColumn;
		List<Object> oValues;
		for(int i=0; i<iSize; i++)
		{
			//Get each column and its values
			oLongColumn = (LongColumn)oDataset.getColumn(i);
			oValues = oLongColumn.getValues();

			System.out.println("\n----------Column Information---------------");
			System.out.println("Name: " + oLongColumn.getName());
			System.out.println("Size: " + oLongColumn.getSize());
			System.out.println("Number of invalid values: " + oLongColumn.countInvalidValues());
			System.out.println("Number of empty values: " + oLongColumn.countEmptyValues());
			System.out.println("Number of null values: " + oLongColumn.countNullValues());
			System.out.println("Number of missing values: "+oLongColumn.countMissingValues());
			System.out.println("Interval: [" + oLongColumn.getlMinInterval() + " , " +  oLongColumn.getlMaxInterval() + " ]");
			System.out.println("Minimum value: " + oLongColumn.getMinValue() + " Maximum value: " + oLongColumn.getMaxValue());

			System.out.println("Mean: " + oLongColumn.mean());
			System.out.println("Standard Deviation: " + oLongColumn.standardDeviation());
			System.out.println("Values: ");

			for(Object oValue : oValues)
				System.out.println(oValue);
		}

		// Values operations
		System.out.println("\n----------Set, add and remove values-----------------");
		oLongColumn = (LongColumn)oDataset.getColumn(0);

		// Set value
		System.out.println("Third element: " +  oLongColumn.getElement(2));
		// First, change the interval
		if(oLongColumn.getlMinInterval() > 1)
			oLongColumn.setlMinInterval(new Long(1));
		oLongColumn.setValue(new Long(1), 2);	// Change the null value
		System.out.println("Third element after setValue: " +  oLongColumn.getElement(2));

		// Remove and add
		System.out.println("Size before removeValue(): " +  oLongColumn.getSize());
		oLongColumn.removeValue(oLongColumn.getSize()-1);
		System.out.println("Size after removeValue(): " +  oLongColumn.getSize());
		System.out.println("Last element after removeValue(): " +  oLongColumn.getElement(oLongColumn.getSize()-1));
		// First, change the interval
		if(oLongColumn.getlMaxInterval() < 12)
			oLongColumn.setlMaxInterval((long) 12);
		oLongColumn.addValue(new Long(12),  oLongColumn.getSize());
		System.out.println("Last element after addValue(): " +  oLongColumn.getElement(oLongColumn.getSize()-1));
		System.out.println("Size after addValue(): " +  oLongColumn.getSize());


		System.out.println("\n----------Conversion methods-----------------");

		// To nominal
		NominalColumn oNominalColumn = ((LongColumn)oDataset.getColumn(0)).toNominal();
		System.out.println("\nValues after converting the first column into nominal:");
		oValues = oNominalColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);

		// To categorical
		CategoricalColumn oCategoricalColumn = ((LongColumn)oDataset.getColumn(0)).toCategorical();
		List<Object> oCategoryList = oCategoricalColumn.getCategoryList();
		System.out.println("\nList of categories: "+oCategoryList.toString());

		// To numerical
		NumericalColumn oNumericalColumn = ((LongColumn)oDataset.getColumn(1)).toNumerical();
		System.out.println("\nValues after converting the second column into numerical:");
		oValues = oNumericalColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		// To integer
		IntegerColumn oIntegerColumn = ((LongColumn)oDataset.getColumn(1)).toInteger(false);
		System.out.println("\nValues after converting the second column into integer (bRoundedValue=false):");
		oValues = oIntegerColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
		
		oIntegerColumn = ((LongColumn)oDataset.getColumn(1)).toInteger(true);
		System.out.println("\nValues after converting the second column into integer (bRoundedValue=true):");
		oValues = oIntegerColumn.getValues();
		for(Object oValue : oValues)
			System.out.println(oValue);
	
		// To date
		oLongColumn = new LongColumn();
		oLongColumn.addValue(System.currentTimeMillis());
		oLongColumn.addValue(NullValue.getNullValue());
		oLongColumn.addValue(MissingValue.getMissingValue());
		oLongColumn.addValue(EmptyValue.getEmptyValue());
		oLongColumn.addValue(new Long(-1));
		oLongColumn.addValue(new Long(0));
		iSize = oLongColumn.getSize();
		
		SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		DateColumn oDateColumn = oLongColumn.toDate(oFormat);
		System.out.println("\nConversion to date: ");
		for(int i=0; i<iSize; i++){
			System.out.println(oDateColumn.getElement(i));
		}
	}
}
