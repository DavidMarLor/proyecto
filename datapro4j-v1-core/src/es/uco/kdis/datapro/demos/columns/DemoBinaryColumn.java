package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.column.BinaryColumn;
import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.source.ArffDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;

import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Demo program for
 * Binary Column management.
 * */
public class DemoBinaryColumn {
	public static void main(String[] args) 
			throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		
		Dataset data = new ArffDataset(args[0]);
		((ArffDataset) data).setMissingValue("?");
		((ArffDataset) data).setEmptyValue("-");
		((ArffDataset) data).setNullValue("<null>");
		((ArffDataset) data).readDataset("bb");	// Force to load two binary columns
		
		System.out.println("\nDataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do{
			System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		int iSize = data.getColumns().size();
		BinaryColumn oBinaryColumn;
		List<Object> oValues;
		
		for(int i=0; i<iSize; i++)
		{
			//Get each column and its values
			oBinaryColumn = (BinaryColumn)data.getColumn(i);
			oValues = oBinaryColumn.getValues();
			
			System.out.println("\n----------Column Information---------------");
			System.out.println("Name: " + oBinaryColumn.getName());
			System.out.println("Size: " + oBinaryColumn.getSize());
			System.out.println("Number of invalid values: " + oBinaryColumn.countInvalidValues());
			System.out.println("Number of empty values: " + oBinaryColumn.countEmptyValues());
			System.out.println("Number of null values: " + oBinaryColumn.countNullValues());
			System.out.println("Number of missing values: "+oBinaryColumn.countMissingValues());
			System.out.println("Values: ");
			
			for(Object oValue : oValues)
				System.out.println(oValue);
		}
		
		// Values operations
		System.out.println("\n----------Set, add and remove values-----------------");
		oBinaryColumn = (BinaryColumn)data.getColumn(0);
		
		// Set value
		System.out.println("Third element: " + oBinaryColumn.getElement(2));
		oBinaryColumn.setValue(new Boolean(true), 2);	// Change the null value
		System.out.println("Third element after setValue: " + oBinaryColumn.getElement(2));
		
		// Remove and add
		System.out.println("Size before removeValue: " + oBinaryColumn.getSize());
		oBinaryColumn.removeValue(oBinaryColumn.getSize()-1);
		System.out.println("Size after removeValue: " + oBinaryColumn.getSize());
		System.out.println("Last element after removeValue: " + oBinaryColumn.getElement(oBinaryColumn.getSize()-1));
		oBinaryColumn.addValue(new Boolean(false), oBinaryColumn.getSize());
		System.out.println("Last element after addValue: " + oBinaryColumn.getElement(oBinaryColumn.getSize()-1));
		System.out.println("Size after addValue: " + oBinaryColumn.getSize());
		
		// To categorical
		System.out.println("\n----------To categorical-----------------");
		oBinaryColumn = (BinaryColumn)data.getColumn(1);
		CategoricalColumn oCategoricalColumn = oBinaryColumn.toCategorical("falseCategory", "trueCategory");

		List<Object> oCategoryList = oCategoricalColumn.getCategoryList();
		System.out.println("Column name: " + oCategoricalColumn.getName());
		System.out.println("List of categories: " + oCategoryList.toString());
	}
}
