package es.uco.kdis.datapro.demos.algorithms;

import java.io.IOException;
import java.util.List;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.algorithm.preprocessing.discretization.EqualWidthDiscretization;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.column.RangeColumn;
import es.uco.kdis.datapro.dataset.source.KeelDataset;
import es.uco.kdis.datapro.exception.NotAddedValueException;

/**
 * Demo program for the Remove
 * Percentage algorithm.
 * */
public class DemoWidthDiscretization {

	public static void main(String[] args) {
		
		if(args.length != 1){
			System.err.println("Invalid invocation: java DemoWidthDiscretization <initialDataset>");
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
		DatasetStrategy oAlgorithm = new EqualWidthDiscretization(oDataset, 3, oDataset.getColumn(0).getName(), oDataset.getColumn(0).getName());
		
		oAlgorithm.initialize();
		oAlgorithm.execute();
		
		// Get the resulting dataset
		RangeColumn oResult = (RangeColumn) oAlgorithm.getResult();
		
		//Show the resulting dataset
		System.out.println("Resulting column after discretize");
		List<Object> oValues = oResult.getValues();
		int iSize = oValues.size();
		for(int i = 0; i < iSize; i++)
			System.out.println(oValues.get(i));
		
		
	}
}
