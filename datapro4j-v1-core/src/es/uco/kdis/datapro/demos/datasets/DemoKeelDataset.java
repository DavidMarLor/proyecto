package es.uco.kdis.datapro.demos.datasets;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.KeelDataset;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DemoKeelDataset {

	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		// Load an Keel dataset
		Dataset data = new KeelDataset(args[0]);
		((KeelDataset) data).readDataset();
		System.out.println("Dataset " + data.getName()+" is now loaded.");
		System.out.println("This dataset uses the symbol '"+((KeelDataset) data).getCommentValue()+"' for comments");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());
		
		// Load some columns of an Keel dataset
		Dataset dataNew = new KeelDataset(args[0]);
		((KeelDataset) dataNew).readDataset("i%f%fc");
		System.out.println("\nDataset " + dataNew.getName()+" is now loaded.");
		
		//Show the dataset
		it = new InstanceIterator(dataNew);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		
		((KeelDataset) dataNew).writeDataset("Prueba.dat");

	}
}
