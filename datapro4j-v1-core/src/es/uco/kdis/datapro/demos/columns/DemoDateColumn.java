package es.uco.kdis.datapro.demos.columns;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.column.DateColumn;
import es.uco.kdis.datapro.dataset.source.ArffDataset;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

/**
 * Demo program for Date Column management (using an ArffDataset).
 * */
public class DemoDateColumn {

	public static void main(String[] args) 
			throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException, IllegalAccessException
	{
		Dataset oDataset = new ArffDataset(args[0]);
		((ArffDataset) oDataset).readDataset();

		System.out.println("\nThe dataset "  + oDataset.getName()+ " is now loaded.");

		//Show the dataset
		InstanceIterator oIterator = new InstanceIterator(oDataset);
		do{
			System.out.println(oIterator.currentInstance());
			oIterator.next();
		} while (!oIterator.isDone());

		// Show column information
		int iSize = oDataset.getColumns().size();
		DateColumn oDateColumn;
		List<Object> oValues;

		for(int i=0; i<iSize; i++) {
			//Get each column and its values
			oDateColumn = (DateColumn)oDataset.getColumn(i);
			oValues = oDateColumn.getValues();

			System.out.println("\n----------Column Information---------------");
			System.out.println("Name: " + oDateColumn.getName());
			System.out.println("Size: " + oDateColumn.getSize());
			System.out.println("Number of invalid values: " + oDateColumn.countInvalidValues());
			System.out.println("Number of empty values: " + oDateColumn.countEmptyValues());
			System.out.println("Number of null values: " + oDateColumn.countNullValues());
			System.out.println("Number of missing values: "+oDateColumn.countMissingValues());
			System.out.println("Values: ");

			for(Object oValue : oValues)
				System.out.println(oValue);
		}

		// Do some operations
		System.out.println("\n----------Set, add and remove values-----------------");
		oDateColumn = (DateColumn)oDataset.getColumn(0);

		// Extract and modify some values
		System.out.println("First element in column 0: " + oDateColumn.getElement(0));
		oDateColumn.addDateSpecification(new SimpleDateFormat("yy-MM-dd"));
		oDateColumn.setValue("99-04-23", 1);
		System.out.println("Second element after setValue: " + oDateColumn.getElement(1));

		// Remove and add
		System.out.println("Size before removeValue: " + oDateColumn.getSize());
		oDateColumn.removeValue(oDateColumn.getSize()-1);
		System.out.println("Size after removeValue: " + oDateColumn.getSize());
		System.out.println("Last element after removeValue: " + oDateColumn.getElement(oDateColumn.getSize()-1));
		oDateColumn.addValue(EmptyValue.getEmptyValue(), oDateColumn.getSize());
		System.out.println("Last element after addValue: " + oDateColumn.getElement(oDateColumn.getSize()-1));
		System.out.println("Size after addValue: " + oDateColumn.getSize());
	}
}
