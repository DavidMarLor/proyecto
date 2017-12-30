package es.uco.kdis.datapro.algorithm.base;

import es.uco.kdis.datapro.dataset.Dataset;

/**
 * This class represents a generic strategy. 
 * Strategy is a well-known design pattern, where algorithms are encapsulated into classes.
 * Strategies should be executed using either a sequential or a step-by-step process. 
 * In general, every strategy is executed according to the following sequence:
 * <ol>
 * <li><b>Creation</b>: the strategy constructor should collect all the parameters required by the 
 * algorithm to be initialized and executed for the first time. Build as many constructor as required.
 * <li><b>Initialization</b>: the method <code>initialize</code> implements any preprocessing step
 * required to the algorithm to be executed. This preprocessing is not a part of the algorithm itself but
 * it should be executed for the first time that the algorithm is invoked.</li>
 * <li><b>Execution</b>: the method <code>execution</code> runs the algorithm once using the parameters
 * introduced when the constructor was invoked, and initialized afterwards. If the algorithm has finished and 
 * it could not be invoked any more, then the method <code>setExecutable(false)</code> should be called. On the
 * contrary, the execution is allowed until the stop criteria is fulfilled.
 * <li><b>Stop criteria</b>: the method <code>isExecutable</code> returns true if the algorithm can be executed once more
 * over the dataset; false, otherwise.</li>
 * <li><b>Post-execution</b>: Any post-processing step has to be implemented by the method <code>postexec()</code>.</li>
 * <li><b>Result collection</b>: Final results are collected from the dataset, if changed, and returned from the method
 * <code>getResult()</code>.</li>
 * </ol>
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
 * <li> (JRR, 0.2, March 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (JTL, 0.1, May 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.2
 *
 **/
public abstract class DatasetStrategy {

	/**
	 * <code>oDataset</code> is the 
	 * dataset used by the strategy
	 @since 0.1
	 **/
	protected Dataset oDataset;

	/**
	 * <code>bExecutable</code> is
	 * the execution flag
	 @since 0.1
	 **/
	protected boolean bExecutable = true;

	/**
	 * Returns the value of bExecutable
	 * @return True if the strategy is on an executable state
	 @since 0.1
	 **/
	public boolean isExecutable() {
		return this.bExecutable;
	}

	/**
	 * Sets the executable state
	 * @param bExecutable True if the strategy is still executable
	 @since 0.1
	 **/
	protected void setExecutable(boolean bExecutable) {
		this.bExecutable = bExecutable;
	}

	/**
	 * Getter method for the dataset
	 * @return The dataset
	 @since 0.1
	 **/
	protected Dataset getDataset() {
		return this.oDataset;
	}

	/**
	 * Sets the dataset to be used by the strategy
	 * @param oDataset The dataset
	 @since 0.1
	 **/
	protected void setDataset(Dataset oDataset) {
		this.oDataset = oDataset;
	}

	/**
	 * Initialization process for the strategy
	 @since 0.1
	 **/
	public abstract void initialize();

	/**
	 * Execution of the strategy
	 @since 0.1
	 **/
	public abstract void execute();

	/**
	 * Postprocessing method
	 @since 0.1
	 **/
	public abstract void postexec();

	/**
	 * Returns an object comprising the result of the process
	 * @return <code>Object</code> embedding the expected result
	 @since 0.1
	 **/
	public abstract Object getResult();

}
