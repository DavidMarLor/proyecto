package es.uco.kdis.datapro.demos.algorithms;

import java.io.IOException;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.algorithm.preprocessing.instance.RemovePercentage;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.KeelDataset;
import es.uco.kdis.datapro.exception.NotAddedValueException;

/**
 * Demo program for the Remove
 * Percentage algorithm.
 * */
public class DemoRemovePercentage {

	public static void main(String[] args) {
		
		if(args.length != 1){
			System.err.println("Invalid invocation: java DemoRemoveDuplicates <initialDataset>");
			System.exit(0);
		}
		
		String sDatasetName = args[0];
		
		// Open the dataset
		Dataset oDataset = new KeelDataset(sDatasetName);

		//Reading dataset
		try {
			((KeelDataset)oDataset).readDataset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NotAddedValueException e) {
			e.printStackTrace();
		}
		 
		//Show the dataset
		System.out.println("Opened dataset: " + oDataset.getName());
		InstanceIterator itInitialDataset = new InstanceIterator(oDataset);
		do {
			 System.out.println(itInitialDataset.currentInstance());
			 itInitialDataset.next();
		} while (!itInitialDataset.isDone());
		
		// Execute the algorithm
		
		// First, remove 20% of instances, randomly chosen
		double dPercentage = 0.2;
		DatasetStrategy oAlgorithm = new RemovePercentage(oDataset, RemovePercentage.RANDOM, dPercentage);
		
		oAlgorithm.initialize();
		oAlgorithm.execute();
		
		// Get the resulting dataset
		Dataset oResult = (KeelDataset)oAlgorithm.getResult();
		
		//Show the resulting dataset
		System.out.println("Resulting dataset after remove " + (dPercentage*100) + "% of instances (randomly chosen)");
		InstanceIterator itFinalDataset = new InstanceIterator(oResult);
		while (!itFinalDataset.isDone()){
			 System.out.println(itFinalDataset.currentInstance());
			 itFinalDataset.next();
		}
		
		// Second, remove 50% of instances starting at the beginning of the dataset
		dPercentage = 0.5;
		oAlgorithm = new RemovePercentage(oDataset, RemovePercentage.FROMINIT, dPercentage);
		
		oAlgorithm.initialize();
		oAlgorithm.execute();
		
		// Get the resulting dataset
		oResult = (KeelDataset)oAlgorithm.getResult();
		
		//Show the resulting dataset
		System.out.println("Resulting dataset after remove " + (dPercentage*100) + "% of instances (from the beginning)");
		itFinalDataset = new InstanceIterator(oResult);
		while (!itFinalDataset.isDone()){
			 System.out.println(itFinalDataset.currentInstance());
			 itFinalDataset.next();
		}
		
		// Third, remove 70% of instances starting at the end of the dataset
		dPercentage = 0.7;
		oAlgorithm = new RemovePercentage(oDataset, RemovePercentage.FROMEND, dPercentage);
		
		oAlgorithm.initialize();
		oAlgorithm.execute();
		
		// Get the resulting dataset
		oResult = (KeelDataset)oAlgorithm.getResult();
		
		//Show the resulting dataset
		System.out.println("Resulting dataset after remove " + (dPercentage*100) + "% of instances (from the end)");
		itFinalDataset = new InstanceIterator(oResult);
		while (!itFinalDataset.isDone()){
			 System.out.println(itFinalDataset.currentInstance());
			 itFinalDataset.next();
		}
	}
}
