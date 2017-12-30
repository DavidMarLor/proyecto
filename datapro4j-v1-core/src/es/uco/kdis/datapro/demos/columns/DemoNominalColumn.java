package es.uco.kdis.datapro.demos.columns;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
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
import java.util.List;

public class DemoNominalColumn {

	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		// Load an Arff dataset
		Dataset data = new ArffDataset(args[0]);
		((ArffDataset) data).setMissingValue("?");
		((ArffDataset) data).setEmptyValue("-");
		((ArffDataset) data).setNullValue("<null>");
		((ArffDataset) data).readDataset("s%%%scd");
		System.out.println("\nDataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		//Get the nominal column
		System.out.println("\nColumn type: "+data.getColumn(0).getType());
		
		if(data.getColumn(0).getType() == ColumnType.Nominal)
		{
			//Get the column and its values
			NominalColumn oNominalColumn = (NominalColumn)data.getColumn(0);
			List<Object> oValues = oNominalColumn.getValues();
			
			for(Object oValue : oValues)
				System.out.println(oValue);
			
			System.out.println("\nNumber of invalid values: "+oNominalColumn.countInvalidValues());
			System.out.println("Number of empty values: "+oNominalColumn.countEmptyValues());
			System.out.println("Number of null values: "+oNominalColumn.countNullValues());
			System.out.println("Number of missing values: "+oNominalColumn.countMissingValues());
			
			CategoricalColumn oCategoricalColumn = oNominalColumn.toCategorical();
			oCategoricalColumn.setName("New categorical column");
			
			System.out.println("\n----------To categorical-----------------");
			List<Object> oCategoricalValues = oCategoricalColumn.getValues();
			List<Object> oCategoryList = oCategoricalColumn.getCategoryList();
			System.out.println("Column name: "+oCategoricalColumn.getName());
			System.out.println("List of categories: "+oCategoryList.toString());
			
			for(Object oValue : oCategoricalValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oCategoricalColumn.getCategoryName((Integer) oValue));
			
			//Get the column and its values
			oNominalColumn = (NominalColumn)data.getColumn(1);
			
			NumericalColumn oNumericalColumn = oNominalColumn.toNumerical();
			oNumericalColumn.setName("New numerical column");
			
			System.out.println("\n----------To numerical-----------------");
			System.out.println("Column name: "+oNumericalColumn.getName());
			List<Object> oNumericalValues = oNumericalColumn.getValues();

			for(Object oValue : oNumericalValues)
				if(!(oValue instanceof InvalidValue))
					System.out.println(oValue);
		}
	}
}
