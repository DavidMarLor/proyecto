package es.uco.kdis.datapro.demos.datasets;

import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.ArffDataset;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DemoArffDataset {

	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NotAddedValueException, IOException, IllegalFormatSpecificationException
	{
		// Load an Arff dataset
		Dataset data = new ArffDataset(args[0]);
		((ArffDataset) data).readDataset();
		System.out.println("Dataset " + data.getName()+" is now loaded.");
		System.out.println("This dataset uses the symbol '"+((ArffDataset) data).getCommentValue()+"' for comments");
		
		//Show the dataset
		InstanceIterator it = new InstanceIterator(data);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());
		
		// Load some columns of an Arff dataset
		Dataset dataNew = new ArffDataset(args[0]);
		((ArffDataset) dataNew).readDataset("s%%%fcd");
		System.out.println("\nDataset " + dataNew.getName()+" is now loaded.");
		
		//Show the dataset
		it = new InstanceIterator(dataNew);
		do {
			 System.out.println(it.currentInstance());
			it.next();
		} while (!it.isDone());

		
		((ArffDataset) dataNew).writeDataset("Prueba.arff");

	}
}
