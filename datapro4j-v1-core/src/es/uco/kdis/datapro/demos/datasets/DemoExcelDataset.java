package es.uco.kdis.datapro.demos.datasets;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.ExcelDataset;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.IOException;

public class DemoExcelDataset {

	public static void main(String[] args) throws IndexOutOfBoundsException, IOException, NotAddedValueException, IllegalFormatSpecificationException 
	{
		// Load an Excel dataset
		Dataset data = new ExcelDataset(args[0]);
		((ExcelDataset)data).setName("'My ExcelDataset'");
		((ExcelDataset)data).readDataset("%%n%%%%v", "ff%fic");
		System.out.println("Dataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		// Write a new dataset
		data.setNumberOfDecimals(1);
		((ExcelDataset)data).writeDataset("output.xlsx");
	}
}