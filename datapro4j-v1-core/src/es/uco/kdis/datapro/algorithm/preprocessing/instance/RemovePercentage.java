package es.uco.kdis.datapro.algorithm.preprocessing.instance;

import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.dataset.Dataset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents a specific strategy that modifies the contain 
 * of a dataset by removing a percentage of dataset instances.
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

public class RemovePercentage extends DatasetStrategy{

	/**
	 * <code>RANDOM</code> is the mode of removal
	 * in which the instances selected to be removed
	 * are chosen randomly.
	 @since 0.2
	 **/
	public static final int RANDOM = 0;
	
	/**
	 * <code>FROMINIT</code> is the mode of removal
	 * in which the instances selected to be removed
	 * are selected from the beginning of the dataset.
	 @since 0.2
	 **/
	public static final int FROMINIT = 1;
		
	/**
	 * <code>RANDOM</code> is the mode of removal
	 * in which the instances selected to be removed
	 * are from the end of the dataset.
	 @since 0.2
	 **/
	public static final int FROMEND = 2;

	/**
	 * <code>oRnd</code> is the random 
	 * generator object.
	 @since 0.1
	 **/
	public Random oRnd = new Random();

	/**
	 * <code>iMode</code> is the mode of removal.
	 * @see <code>RANDOM</code>, <code>FROMINIT</code>, <code>FROMEND</code>
	 @since 0.1
	 **/
	private int iMode;
	
	/**
	 * <code>dPercentage</code> is the percentage (in the interval [0,1])
	 * of instances to remove from dataset.
	 @since 0.1
	 **/
	private double dPercentage;

	/**
	 * <code>oResultDataset</code> is the
	 * dataset with the removal.
	 @since 0.1
	 **/
	private Dataset oResultDataset;

	/**
	 * <code>iInstancesToRemove</code> is the
	 * number of instances to remove.
	 @since 0.1
	 **/
	private int iInstancesToRemove;

	/**
	 * <code>rgiIndexesToRemove</code> is the
	 * array with indexes to remove.
	 **/
	private List<Integer> rgiIndexesToRemove;
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param oDataset The source dataset.
	 * @param iMode The mode of removal. 
	 * @see <code>RANDON</code>, <code>FROMINIT</code>, <code>FROMEND</code>
	 * @param dPercentage The percentage of instances (in [0,1]) to remove from the dataset.
	 @since 0.1
	 **/
	public RemovePercentage(Dataset oDataset, int iMode, double dPercentage) {

		setDataset(oDataset);
		this.iMode = iMode;
		this.dPercentage = dPercentage;
		
		this.oRnd.setSeed(System.currentTimeMillis());
	}

	/**
	 * Initializes the algorithm to prepare the execution.
	 @since 0.1
	 **/
	@Override
	public void initialize() {
		
		if(this.iMode < 0 || this.iMode > 2)
			throw new IndexOutOfBoundsException("The mode selected (#" + this.iMode + ") is out of range [0,2]");
		
		if(this.dPercentage < 0.0 || this.dPercentage > 1.0)
			throw new IndexOutOfBoundsException("The percentage selected (#" + this.dPercentage + ") is out of range [0,1]");
		
		this.iInstancesToRemove = (int)Math.rint(this.oDataset.getColumn(0).getSize()*this.dPercentage);
		this.oResultDataset = getDataset().copy();
		
		this.rgiIndexesToRemove = new ArrayList<Integer>();
	}

	/**
	 * Execute method.
	 @since 0.1
	 **/
	@Override
	public void execute() {
		if(this.iInstancesToRemove == this.getDataset().getColumn(0).getSize()){
			for(int i=this.iInstancesToRemove-1; i>=0; i--)
				this.rgiIndexesToRemove.add(i);
			removeInstances();
		}
		else if(this.iInstancesToRemove > 0){
			generateRemovalIndexes();
			removeInstances();
		}
		setExecutable(false);
	}

	/**
	 * Post execution method
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
	 * Generates an array with the indexes 
	 * to remove, according to the mode.
	 @since 0.1
	 **/
	private void generateRemovalIndexes(){
		
		int iNumOfInstances = this.oDataset.getColumn(0).getSize();
	
		switch(this.iMode){
		
			// Generate random indexes of instances for removal
			case 0:
				int iCounter = 0;
// TODO ARQ: Esta forma de generar los índices de las instancias a eliminar puede ser costoso
// cuando el dataset tenga muchas instancias y el porcentaje sea próximo a 1.
				while(iCounter<this.iInstancesToRemove){
					// Generates a random index in [0, instances]
					int iRndIndex = (int)Math.rint(this.oRnd.nextDouble()*(iNumOfInstances-1));
					if(!this.rgiIndexesToRemove.contains(iRndIndex)){
						this.rgiIndexesToRemove.add(iRndIndex);
						iCounter++;
					}	
				}
				Collections.sort(this.rgiIndexesToRemove, Collections.reverseOrder());
				break;
			
			// Generate indexes for removal from the start of the dataset
			case 1:
				for(int i=this.iInstancesToRemove-1; i>=0; i--){
					this.rgiIndexesToRemove.add(i);
				}
				break;
			
			// Generate indexes for removal starting from the end of the dataset
			case 2: 
				for (int i = iNumOfInstances-1; i >= (iNumOfInstances - this.iInstancesToRemove); i--){
					this.rgiIndexesToRemove.add(i);
				}
				break;
		}
	}

	/**
	 * Removes the instances specified by 
	 * <code>rgiIndexesToRemove</code>.
	 @since 0.1
	 **/
	private void removeInstances(){
		int iNumCols = this.oResultDataset.getColumns().size();
		int iNumIndexes = this.rgiIndexesToRemove.size();
		
		for (int i = 0; i < iNumCols; i++){
			for (int j = 0; j < iNumIndexes; j++){
				this.oResultDataset.getColumns().get(i).removeValue(this.rgiIndexesToRemove.get(j));
			}
		}
	}
}