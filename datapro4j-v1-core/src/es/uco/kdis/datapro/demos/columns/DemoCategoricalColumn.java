package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.column.BinaryColumn;
import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.column.ColumnType;
import es.uco.kdis.datapro.dataset.column.NominalColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.source.ArffDataset;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemoCategoricalColumn {

	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		// Load an Arff dataset
		Dataset data = new ArffDataset(args[0]);
		((ArffDataset) data).setMissingValue("?");
		((ArffDataset) data).setEmptyValue("-");
		((ArffDataset) data).setNullValue("<null>");
		((ArffDataset) data).readDataset("s%f%scd");
		System.out.println("\nDataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		//Get the nominal column
		System.out.println("\nColumn type: "+data.getColumn(3).getType());
		
		if(data.getColumn(3).getType() == ColumnType.Categorical)
		{
			//Get the column and its values
			CategoricalColumn oCategoricalColumn = (CategoricalColumn)data.getColumn(3);
			
			System.out.println("\nNumber of invalid values: "+oCategoricalColumn.countInvalidValues());
			System.out.println("Number of empty values: "+oCategoricalColumn.countEmptyValues());
			System.out.println("Number of null values: "+oCategoricalColumn.countNullValues());
			System.out.println("Number of missing values: "+oCategoricalColumn.countMissingValues());
			
			NominalColumn oNominalColumn = oCategoricalColumn.toNominal();
			oNominalColumn.setName("New nominal column");
			
			System.out.println("\n----------To nominal-----------------");
			List<Object> oNominalValues = oNominalColumn.getValues();
			System.out.println("Column name: "+oNominalColumn.getName());
			
			for(Object oValue : oNominalValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oValue);
			
			//Get the column and its values
			NumericalColumn oNumericalColumn = oCategoricalColumn.toNumerical();
			oNumericalColumn.setName("New numerical column");
			
			System.out.println("\n----------To numerical-----------------");
			System.out.println("Column name: "+oNumericalColumn.getName());
			List<Object> oNumericalValues = oNumericalColumn.getValues();

			for(Object oValue : oNumericalValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oValue);
			
			//Get the column and its values
			List<String> oTrueValues = new ArrayList<String>();
			oTrueValues.add("Iris-virginica");
			BinaryColumn oBinaryColumn = oCategoricalColumn.toBinary(oTrueValues);
			oBinaryColumn.setName("New binary column");
			
			System.out.println("\n----------To binary-----------------");
			System.out.println("Column name: "+oBinaryColumn.getName());
			List<Object> oBinaryValues = oBinaryColumn.getValues();

			for(Object oValue : oBinaryValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oValue);
			
			//Replace a category
			System.out.println("\n----------Replace category-----------------");
			oCategoricalColumn.replaceCategory("Iris-virginica", "Iris-versiNew", false);
			List<Object> oCategoricalValues = oCategoricalColumn.getValues();
			for(Object oValue : oCategoricalValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oCategoricalColumn.getCategoryName((Integer) oValue));
			
		}
	}
}
