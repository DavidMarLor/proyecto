package es.uco.kdis.datapro.algorithm.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.dataset.column.ColumnType;

/**
 * This class represents a specific strategy that calculates different partitions
 * of dataset using KFolds algorithm.
 * 
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Jose Maria Luna, MSc 		[jmluna@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (JRR|JML|ARQ, 0.2, March 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, July 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.2
 *
 **/

public class KFolds extends DatasetStrategy{

	/**
	 * <code>iSeed</code> is the 
	 * seed used to create partitions.
	 @since 0.2
	 **/
	private long iSeed;

	/**
	 * <code>iNumberOfPartitions</code> is the 
	 * number of partitions to be created.
	 @since 0.1
	 **/
	private int iNumberOfPartitions;

	/**
	 * <code>oSourceDataset</code> is the source dataset
	 * that will be partitioned.
	 @since 0.1
	 **/
	private Dataset oSourceDataset;

	/**
	 * <code>oClassInstances</code> is the array 
	 * of instances sorted by key.
	 @since 0.1
	 **/
	private Hashtable<String, ArrayList<List<Object>>> oClassInstances = null;

	/**
	 * <code>oDatasetPartitions</code> is the list
	 * with the result partitions.
	 @since 0.1
	 **/
	private List<Dataset> oDatasetPartitions = null;

	/**
	 * Parameterized Constructor.
	 * The class column is supposed to be the last column in dataset.
	 * @param oDataset The source dataset.
	 * @param iNumberOfPartitions The number of partitions to make.
	 * Note: The seed for random generator is initialized at System.currentTimeMillis()
	 @since 0.2
	 **/
	public KFolds (Dataset oDataset, int iNumberOfPartitions){
		this.iNumberOfPartitions = iNumberOfPartitions;	
		setDataset(oDataset);
		this.oSourceDataset = getDataset().copy();
		this.oDatasetPartitions = null;
		this.iSeed = System.currentTimeMillis();
	}

	/**
	 * Parameterized Constructor.
	 * The class column is supposed to be the last column in dataset.
	 * 
	 * @param oDataset The Source dataset.
	 * @param iNumberOfPartitions The number of partitions to make.
	 * @param iSeed The seed to be used.
	 @since 0.2
	 **/
	public KFolds (Dataset oDataset, int iNumberOfPartitions, int iSeed){
		this.iNumberOfPartitions = iNumberOfPartitions;	
		setDataset(oDataset);
		this.oSourceDataset = getDataset().copy();
		this.oDatasetPartitions = null;
		this.iSeed = iSeed;
	}

	/**
	 * Initializes the algorithm to prepare the execution.
	 * The instances are sorted in a hash map keyed by category.
	 @since 0.1
	 **/
	public void initialize() {
		this.oClassInstances = new Hashtable<String, ArrayList<List<Object>>>();
		ColumnType oType = this.oSourceDataset.getColumns().get(this.oSourceDataset.getColumns().size()-1).getType();

		if (oType == ColumnType.Nominal	|| oType == ColumnType.Categorical) {
			InstanceIterator itInstance = new InstanceIterator(this.oSourceDataset);
			do{
				List<Object> rgoCurrentInstance = itInstance.currentInstance();

				Object sCategory = rgoCurrentInstance.get(rgoCurrentInstance.size()-1);

				if (this.oClassInstances.containsKey(sCategory)) {
					ArrayList<List<Object>> aCategoryInstances = this.oClassInstances.get(sCategory);
					aCategoryInstances.add(rgoCurrentInstance.subList(0, rgoCurrentInstance.size()-1));
				}
				else {
					List<List<Object>> aCategoryInstances = new ArrayList<List<Object>>();
					aCategoryInstances.add(rgoCurrentInstance.subList(0, rgoCurrentInstance.size()-1));
					this.oClassInstances.put((String) sCategory, (ArrayList<List<Object>>) aCategoryInstances);
				}
				itInstance.next();
			}while (!itInstance.isDone());
		}
	}

	/**
	 * Implements the strategy of KFolds algorithm
	 @since 0.2
	 **/
	@Override
	public void execute() {
		if (this.oClassInstances != null){

			this.oDatasetPartitions = new ArrayList<Dataset>();
			Hashtable<Integer, ArrayList<List<Object>>> oFinalData = new Hashtable<Integer, ArrayList<List<Object>>>();

			for (int i = 0; i < this.iNumberOfPartitions; i++){
				oFinalData.put(i, new ArrayList<List<Object>>());
			}

			Random rnd = new Random(this.iSeed);

			for (String sCategory : this.oClassInstances.keySet()){

				ArrayList<List<Object>> aActualInstance = this.oClassInstances.get(sCategory);

				Collections.shuffle(aActualInstance, rnd);

				for (int i = 0; i < aActualInstance.size(); i++)
					aActualInstance.get(i).add(sCategory);

				int i=0;
				int iFileCounter = 0;
				for (Iterator<List<Object>> it = aActualInstance.iterator(); it.hasNext(); i++){
					iFileCounter = i%this.iNumberOfPartitions;
					ArrayList<List<Object>> aAuxArray = oFinalData.get(iFileCounter);
					aAuxArray.add(it.next());
					oFinalData.put(iFileCounter, aAuxArray);
				}
			}

			/* Clone the structure to the destination datasets */
			for (int i = 0; i < this.iNumberOfPartitions*2; i++){
				Dataset oAuxDataset = this.oSourceDataset.clone();
				this.oDatasetPartitions.add(oAuxDataset);
			}

			int index = 0;
			for (int i = 0; i < this.iNumberOfPartitions; i++) {	
				for (int j = 0; j < this.iNumberOfPartitions; j++) {
					if (j != i) 
						for (Iterator<List<Object>> it = oFinalData.get(j).iterator(); it.hasNext();) {
							List<Object> aCurrentInstance = ((List<Object>)it.next());
							for (int k = 0; k < aCurrentInstance.size(); k++)
								this.oDatasetPartitions.get(index).getColumn(k).addValue(aCurrentInstance.get(k));
						}
				}
				index++;

				for (Iterator<List<Object>> it = oFinalData.get(i).iterator(); it.hasNext();) {
					List<Object> aCurrentInstance = ((List<Object>)it.next());

					for (int j = 0; j < aCurrentInstance.size(); j++)
						this.oDatasetPartitions.get(index).getColumn(j).addValue(aCurrentInstance.get(j));
				}

				index++;
			}
		}
		setExecutable(false);
	}

	/**
	 * Strategy after the execute method
	 @since 0.1
	 **/
	@Override
	public void postexec() {}

	/**
	 * Return the dataset partitions
	 * @return The result partition dataset
	 @since 0.1
	 **/
	@Override
	public Object getResult() {
		return this.oDatasetPartitions;
	}
}
