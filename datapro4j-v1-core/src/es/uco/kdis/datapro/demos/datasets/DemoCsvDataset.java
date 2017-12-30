package es.uco.kdis.datapro.demos.datasets;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.CsvDataset;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.IOException;

public class DemoCsvDataset {

	public static void main(String[] args) throws IndexOutOfBoundsException, IOException, NotAddedValueException, IllegalFormatSpecificationException 
	{
		// Load an Csv dataset
		Dataset data = new CsvDataset(args[0]);
		((CsvDataset)data).setName("'My CsvDataset'");
		((CsvDataset)data).readDataset("%%n%v", "c%ff%cs");
		System.out.println("Dataset " + data.getName()+" is now loaded.");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		// Write a new dataset
		((CsvDataset)data).writeDataset("salida.csv");
	}
}