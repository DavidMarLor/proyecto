package es.uco.kdis.datapro.demos.algorithms;

import java.io.IOException;
import java.util.InputMismatchException;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.algorithm.preprocessing.instance.RemoveDuplicates;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.source.KeelDataset;
import es.uco.kdis.datapro.exception.NotAddedValueException;

/**
 * Demo program for the Remove
 * Duplicates algorithm.
 * */
public class DemoRemoveDuplicates {

	 public static void main (String args[]){
		
		if(args.length != 2){
			System.err.println("Invalid invocation: java DemoRemoveDuplicates <initialDataset> <finalDataset>");
			System.exit(0);
		}
		
		String sDatasetName = args[0];
		String sResultDatasetName = args[1];
		
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
		DatasetStrategy oAlgorithm = new RemoveDuplicates(oDataset);
		oAlgorithm.initialize();
		oAlgorithm.execute();
		
		// Get the resulting dataset
		Dataset oResult = (KeelDataset)oAlgorithm.getResult();
		
		//Show the resulting dataset
		System.out.println("Resulting dataset");
		InstanceIterator itFinalDataset = new InstanceIterator(oResult);
		do {
			 System.out.println(itFinalDataset.currentInstance());
			 itFinalDataset.next();
		} while (!itFinalDataset.isDone());
		
		// Write resulting dataset
		try {
			((KeelDataset)oResult).writeDataset(sResultDatasetName);
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	 }
}
