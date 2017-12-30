package es.uco.kdis.datapro.algorithm.preprocessing.instance;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a specific strategy that modifies the contain of a Dataset
 * by removing duplicated instances from the dataset.
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
 * <li> (JRR|JML|ARQ, 0.2, April 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, July 2011) 			Initial version.
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

public class RemoveDuplicates extends DatasetStrategy{

	/**
	 * <code>oResultDataset</code> is the dataset with 
	 * the duplicated instances removed.
	 @since 0.1
	 **/
	private Dataset oResultDataset;

	/**
	 * <code>iActualInstanceIndex</code> is the 
	 * actual instance being processed.
	 @since 0.1
	 **/
	private int iActualInstanceIndex = 0;

	/**
	 * <code>rgiIndexesToRemove</code> is the array 
	 * with the indexes to remove.
	 @since 0.1
	 **/
	private List<Integer> rgiIndexesToRemove;

	/**
	 * Parameterized Constructor
	 * @param oDataset The source dataset to work with.
	 @since 0.1
	 **/
	public RemoveDuplicates(Dataset oDataset) {
		super.setDataset(oDataset);
		this.oResultDataset = super.getDataset().copy();
	}

	/**
	 * Initializes the algorithm to prepare the execution.
	 @since 0.1
	 **/
	@Override
	public void initialize() {}

	/**
	 * Execute method.
	 @since 0.1
	 **/
	@Override
	public void execute() {
		generateRemovalIndexes();
		removeInstances();
		// TODO ARQ: Quitado de removeInstances
		setExecutable(false);
	}
	
	/**
	 * Strategy after the execute method
	 @since 0.1
	 **/
	@Override
	public void postexec() {}

	/**
	 * Get the result of the strategy
	 * @return The modified dataset
	 @since 0.1
	 **/
	@Override
	public Object getResult() {
		return this.oResultDataset;
	}

	/**
	 * Generates an array with the index which 
	 * are needed to be removed.
	 @since 0.1
	 */
	// TODO Procesar con BitSet, para marcar una vez si hay que borrarla o no, luego se recorre en orden inverso
	// sin necesidad de ordenar. Además, será más fácil comprobar si una instancia ya ha sido marcada como
	// eliminada o no.
	private void generateRemovalIndexes(){
		
		this.rgiIndexesToRemove = new ArrayList<Integer>();
		InstanceIterator itInstanceIterator = new InstanceIterator(getDataset());

		// Controls the actual instance being processed
		while (!itInstanceIterator.isDone()){
			List<Object> aCurrentInstance = itInstanceIterator.currentInstance();
			
			InstanceIterator itInstanceToCompare = new InstanceIterator(getDataset());
			int iCounter = 0;
			
			// Advance the second iterator to the position of the main iterator
			
			while (iCounter <= this.iActualInstanceIndex){
				iCounter++;
				itInstanceToCompare.next();
			}
			
			// Manages the second iterator, where instances are compared with the actual instance being processed
			
			while (!itInstanceToCompare.isDone()){
				List<Object> aInstanceToCompare = itInstanceToCompare.currentInstance();
				
				// Check if the actual instance has been marked as duplicate
				if(!this.rgiIndexesToRemove.contains(iCounter))
				{
					boolean bEqualInstanceFlag = true;
					int iSize = aCurrentInstance.size();
					
					// Compare the instance values
					for (int i = 0; i < iSize; i++){
						Object oValueInst1 = aCurrentInstance.get(i);
						Object oValueInst2 = aInstanceToCompare.get(i);
					
						if (oValueInst1 != null && oValueInst2 != null){
							if (!oValueInst1.equals(oValueInst2)){
								bEqualInstanceFlag = false;
								break;
							}
						}
						else{
							bEqualInstanceFlag = false;
							break;
						}
					}
					if (bEqualInstanceFlag){
						this.rgiIndexesToRemove.add(iCounter);
					}
				}
				
				iCounter++;
				itInstanceToCompare.next();
			}
			
			this.iActualInstanceIndex++;
			itInstanceIterator.next();
		}

	}

	/**
	 * Removes the instances specified in 
	 * <code>rgiIndexesToRemove</code>
	 @since 0.1
	 **/
	private void removeInstances(){
	
		int iNumColumns = this.oResultDataset.getColumns().size();
		// For each column
		for (int i = 0; i < iNumColumns; i++){
			// For each instance marked as duplicate
			for (int j=this.rgiIndexesToRemove.size(); j>0; j--){
				this.oResultDataset.getColumns().get(i).removeValue(this.rgiIndexesToRemove.get(j-1));
			}
		}
	}
}