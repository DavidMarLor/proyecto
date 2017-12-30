package es.uco.kdis.datapro.algorithm.preprocessing.discretization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import es.uco.kdis.datapro.algorithm.base.DatasetStrategy;
import es.uco.kdis.datapro.dataset.Dataset;
import es.uco.kdis.datapro.dataset.column.CategoricalColumn;
import es.uco.kdis.datapro.dataset.column.ColumnAbstraction;
import es.uco.kdis.datapro.dataset.column.ColumnType;
import es.uco.kdis.datapro.dataset.column.IntegerColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.column.RangeColumn;

//TODO ARQ: Extender comentario y poner referencia
/**
 * This class represents a specific strategy that discretizes the whole dataset using
 * MDLP algorithm purposed by Fayyad & Irani.
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
 * <li> (ARQ, 0.2, September 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, July 2011) Initial version.
 * </ul>
 * <p>
 *
@author Aurora Ramirez Quesada (ARQ, 0.2)
@author Mario Orozco Borrego (MOB, 0.1)
@version 0.2
 *
 **/
public class MDLPDiscretize extends DatasetStrategy{

	/**
	 * <code>aLinkedColumns</code> is a list where each element is a linked list that 
	 * contains one whole column. Each value is a double array, where the first value 
	 * is the value of the column, and the second is the label associated with the value.
	 @since 0.1
	 **/
	private List<LinkedList<double[]>> aLinkedColumns;

	// TODO ARQ: Cambiado el nombre de oDiscretization a rgoResultColumns
	/**
	 * <code>oDiscretization</code> is the list of result columns
	 @since 0.1
	 **/
	private List<ColumnAbstraction> rgoResultColumns;

	// TODO ARQ: Cambiado el nombre de oSourceDataset a oDataset
	/**
	 * <code>oDataset</code> is the source dataset.
	 @since 0.1
	 **/
	private Dataset oDataset;

	/**
	 * <code>iNumOfCategories</code> is the number 
	 * of categories from dataset.
	 @since 0.1
	 **/
	private int iNumOfCategories;

	/**
	 * <code>oResultDataset</code> is the resulting dataset.
	 @since 0.1
	 **/
	private Dataset oResultDataset;

	/**
	 * Parameterized constructor
	 * @param oDataset The source dataset
	 * Note: class labels are supposed to be in the last column of the dataset.
	 @since 0.1
	 **/	
	public MDLPDiscretize(Dataset oDataset) {
		super.setDataset(oDataset);
		this.oDataset = getDataset().copy();
		this.oResultDataset = getDataset().clone();
		// TODO ARQ: Esto no se supone que lo debe hacer el usuario cuando vaya a escribir el dataset?
		this.oResultDataset.setNumberOfDecimals(2);
		this.oResultDataset.setValidBinaryValues(getDataset().getValidBinaryFalseValues(), getDataset().getValidBinaryTrueValues());
	}

	/**
	 * The initialize method.
	 * This takes the whole datasets, and distribute each column in a LinkedList, that contains a double array
	 * where the first value is the concrete value of the column, the second value is the label associated.
	 @since 0.1
	 **/
	@Override
	public void initialize() {

		int iColumnsSize = this.oDataset.getColumns().size();
		if (this.oDataset.getColumn(iColumnsSize-1).getType() == ColumnType.Categorical) {
			this.aLinkedColumns = new LinkedList<LinkedList<double[]>>();
			List<ColumnAbstraction> aColumns = this.oDataset.getColumns();
			CategoricalColumn aLabelColumn = (CategoricalColumn) this.oDataset.getColumn(iColumnsSize-1);

			this.iNumOfCategories = aLabelColumn.getCategoryList().size();

			for (int i = 0; i < iColumnsSize-1; i++){ /** The last column is the label */
				ColumnAbstraction aCurrentColumn = aColumns.get(i);
				LinkedList<double[]> aColumnLinkedList = new LinkedList<double[]>();

				/* Only numericals or integer are allowed */
				if (aCurrentColumn.getType() == ColumnType.Numerical ||
						aCurrentColumn.getType() == ColumnType.Integer){

					int iCurrentColumnSize = aCurrentColumn.getSize();
					for (int j = 0; j < iCurrentColumnSize; j++){
						Object oValue = aCurrentColumn.getElement(j);
						Object oLabel = aLabelColumn.getElementIndex(j);

						/* If any of the values get are invalid, then a 0 is put */
						if (oValue != null){
							if (oValue.equals(aCurrentColumn.getMissingValue()) ||
									oValue.equals(aCurrentColumn.getNullValue()) ||
									oValue.equals(aCurrentColumn.getMissingValue()))
								oValue = new Double(0);
						}
						else{
							oValue = new Double(0);
						}

						if (oLabel != null){
							if (oLabel.equals(aLabelColumn.getMissingValue()) ||
									oLabel.equals(aLabelColumn.getNullValue()) ||
									oLabel.equals(aLabelColumn.getMissingValue()))
								oLabel = new Double(0);
						}

						double [] dPairValues = new double[2];
						/* Concreted value */
						dPairValues[0] = Double.parseDouble(oValue.toString());
						/* Label associated with the value */
						dPairValues[1] = Double.parseDouble(oLabel.toString());
						aColumnLinkedList.add(dPairValues);
					}
				}
				this.aLinkedColumns.add(aColumnLinkedList);
			}
		}
	}

	/**
	 * The execute method.
	 @since 0.1
	 **/
	@Override
	public void execute() {
		/* There is at least one discretizable column (numerical or integer) */
		// TODO ARQ: Eliminar la llamada a método?
		if (this.aLinkedColumns != null) 
			discretize();
		setExecutable(false);
	}

	/** 
	 * The post execution method
	 @since 0.1
	 **/
	@Override
	public void postexec() {
		this.oResultDataset.setColumns(this.rgoResultColumns);
	}

	/**
	 * Get the strategy result.
	 * @returns The discretized dataset
	 @since 0.1
	 **/
	@Override
	public Object getResult() {
		/* The discretization cannot be made (possibly there isn't a category column) */
		if (this.aLinkedColumns != null) 
			return this.oResultDataset;
		else
			return null;
	}

	/** 
	 * This method gets the cutPoints for a concrete column.
	 * @param startPartition The column with the labels associated.
	 * @return The cut-points, if none encountered, then a null value is returned.
	 @since 0.1
	 **/
	private ArrayList<Double> getCutPoints(LinkedList<double[]> aStartPartition){

		LinkedList<LinkedList<double[]>> aLimit = new LinkedList<LinkedList<double[]>>();
		ArrayList<Double> aResult = new ArrayList<Double>();
		aLimit.addLast(aStartPartition);

		while (!aLimit.isEmpty()){
			LinkedList<double[]> aCurrentPartition = aLimit.removeFirst();

			/* Get a valid cut-point */
			Double dCutPoint = getMinEntropyCutPoint(aCurrentPartition);

			if (dCutPoint != null){
				aResult.add(dCutPoint);
				double dSplitValue = dCutPoint.doubleValue();
				LinkedList<double[]> newPartition1 = new LinkedList<double[]>();
				LinkedList<double[]> newPartition2 = new LinkedList<double[]>();
				Iterator<double[]> it = aCurrentPartition.iterator();
				while (it.hasNext()){
					/* Cut the interval generating two partitions */
					double [] attributeLabelPair = it.next();
					if (attributeLabelPair[0] <= dSplitValue)
						newPartition1.addLast(attributeLabelPair);	
					else
						newPartition2.addLast(attributeLabelPair);
				}
				/* Partitions are added to the array, so the can be checked separately, here we have the 'recursion' */
				aLimit.addLast(newPartition1);
				aLimit.addLast(newPartition2);
			}
		}
		return aResult;
	}

	/**
	 * Calculates the minimum entropy split point for a column.
	 * @param aSampleColumn The column subset where a minimum cut-point must be calculated.
	 * @return A minimum entropy split point.
	 @since 0.1
	 **/
	private Double getMinEntropyCutPoint(LinkedList<double[]> aSampleColumn){

		HashSet<Double> candidateSplitPoints = new HashSet<Double>();

		int iSampleColumnSize = aSampleColumn.size();

		int[] iTotalLabelDistribution = new int[this.iNumOfCategories];
		for (int i = 0; i < iTotalLabelDistribution.length; i++)
			iTotalLabelDistribution[i] = 0;

		Iterator<double[]> it = aSampleColumn.iterator();

		double oPrevValue = Double.NaN;
		while (it.hasNext()){
			double[] dAttributeLabelSet = it.next();
			if (oPrevValue != dAttributeLabelSet[0]){
				candidateSplitPoints.add(dAttributeLabelSet[0]);
				oPrevValue = dAttributeLabelSet[0];
			}
			iTotalLabelDistribution[(int)dAttributeLabelSet[1]]++;
		}

		/* Calculate frequencies for each category */
		double [] dTotalFrequencies = new double[this.iNumOfCategories];
		for (int i = 0; i < this.iNumOfCategories; i++){
			dTotalFrequencies[i] = (double)iTotalLabelDistribution[i] / (double)iSampleColumnSize;
		}
		double dTotalEntropy = 0.0d;

		/* Calculate total entropy of the whole partition */
		for (int i = 0; i < this.iNumOfCategories; i++){
			dTotalEntropy -= dTotalFrequencies[i]*logBase2(dTotalFrequencies[i]);
		}

		double dMinClassInformationEntropy = dTotalEntropy;
		double dBestSplitPoint = Double.NaN;
		double dBestSplitPointEntropy1 = Double.POSITIVE_INFINITY;
		double dBestSplitPointEntropy2 = Double.POSITIVE_INFINITY;
		int dK1 = 0;
		int dK2 = 0;

		Iterator<Double> it1 = candidateSplitPoints.iterator();

		while (it1.hasNext()){
			double dCurrentSplitPoint = ((Double)it1.next()).doubleValue();
			// Initialize
			int dInstanceCounterLeft = 0;
			int dInstanceCounterRight = 0;
			dK1 = 0;
			dK2 = 0;
			int[] iLabelDistribution1 = new int[this.iNumOfCategories];
			int[] iLabelDistribution2 = new int[this.iNumOfCategories];

			Iterator<double[]> it2 = aSampleColumn.iterator();
			/* Calculate category distribution for both partitions */
			while (it2.hasNext()){
				double[] dAttributeLabelSet = it2.next();
				double dValueToCompare = dAttributeLabelSet[0];
				int iLabelIndex = (int) dAttributeLabelSet[1];
				if (dValueToCompare <= dCurrentSplitPoint){
					dInstanceCounterLeft++;
					iLabelDistribution1[iLabelIndex]++;
				}
				else{
					dInstanceCounterRight++;
					iLabelDistribution2[iLabelIndex]++;
				}
			}

			double[] dFrequencies1 = new double[this.iNumOfCategories];
			double[] dFrequencies2 = new double[this.iNumOfCategories];

			/* Calculate category frequencies and the number of 
			 different categories for this cutpoint in both sides */
			for (int i = 0; i < this.iNumOfCategories; i++){
				dFrequencies1[i] = (double)iLabelDistribution1[i] / (double)dInstanceCounterLeft;
				dFrequencies2[i] = (double)iLabelDistribution2[i] / (double)dInstanceCounterRight;
				/** Category value exists in left side */
				if (iLabelDistribution1[i] > 0)
					dK1++;
				/** Category value exists in right side */
				if (iLabelDistribution2[i] > 0)
					dK2++;
			}

			/* Calculate entropies of both partitions */
			double dEntropy1 = 0.0d;
			double dEntropy2 = 0.0d;
			for (int i = 0; i < this.iNumOfCategories; i++){
				dEntropy1 -= dFrequencies1[i]*logBase2(dFrequencies1[i]);
				dEntropy2 -= dFrequencies2[i]*logBase2(dFrequencies2[i]);
			}

			double dClassInformationEntropy = ((double)dInstanceCounterLeft / (double)iSampleColumnSize)*dEntropy1 +
					((double)dInstanceCounterRight / (double)iSampleColumnSize)*dEntropy2;

			if (dClassInformationEntropy < dMinClassInformationEntropy){
				dMinClassInformationEntropy = dClassInformationEntropy;
				dBestSplitPoint = dCurrentSplitPoint;
				dBestSplitPointEntropy1 = dEntropy1;
				dBestSplitPointEntropy2 = dEntropy2;
			}

		}

		double dGain = dTotalEntropy - dMinClassInformationEntropy;
		double dDelta = logBase2(Math.pow(3.0, this.iNumOfCategories) - 2) 
				- (this.iNumOfCategories * dTotalEntropy - dK1 * dBestSplitPointEntropy1 - dK2 * dBestSplitPointEntropy2);

		/* Accept criterion */
		if (dGain >= logBase2(iSampleColumnSize - 1)/iSampleColumnSize + dDelta / iSampleColumnSize){
			return Double.valueOf(dBestSplitPoint);
		}
		else{
			return null;
		}
	}

	/**
	 * Calculates a logarithm of x in base 2
	 * @param value x
	 * @return logarithm in base 2 of x
	 @since 0.1
	 **/
	private double logBase2(double value){
		return Math.log(value)/Math.log(2.0d);
	}

	/**
	 * This method gets all numeric/integer columns an gets the cut-points by applying Fayyad & Irani MDLP criterion.
	 * Generates a List of range columns where discretization is made.
	 @since 0.1
	 **/
	private void discretize(){

		List<ColumnAbstraction> aRangeDiscretization = new ArrayList<ColumnAbstraction>();
		/* For each column */
		for (int i = 0; i < this.oDataset.getColumns().size()-1; i++){
			ColumnAbstraction oActualColumn = this.oDataset.getColumn(i);

			if (oActualColumn.getType() == ColumnType.Numerical ||
					oActualColumn.getType() == ColumnType.Integer){
				double dMinInterval = 0;
				double dMaxInterval = 0;
				if (oActualColumn.getType() == ColumnType.Numerical){
					dMinInterval = ((NumericalColumn)oActualColumn).getMinValue();
					dMaxInterval = ((NumericalColumn)oActualColumn).getMaxValue();
				}
				else if (oActualColumn.getType() == ColumnType.Integer){
					dMinInterval = ((IntegerColumn)oActualColumn).getMinValue();
					dMaxInterval = ((IntegerColumn)oActualColumn).getMaxValue();
				}
				RangeColumn oResultColumn = new RangeColumn("MDLPDiscretization/" + oActualColumn.getName());


				/* Any of invalid values of the result column cannot be Double.NEGATIVE_INFINITY,
				so values are given by default */
				oResultColumn.setEmptyValue(Double.MAX_VALUE);
				oResultColumn.setMissingValue(Double.MIN_VALUE);
				oResultColumn.setNullValue(null);

				ArrayList<Double> aSplitPoints = getCutPoints(aLinkedColumns.get(i));

				Collections.sort(aSplitPoints);

				double [] aCutPoints = new double[aSplitPoints.size()+2];
				int iCurrentCutPoint = 0;
				aCutPoints[iCurrentCutPoint] = Double.NEGATIVE_INFINITY;
				iCurrentCutPoint++;

				for (int j = 0; j < aSplitPoints.size(); j++){
					aCutPoints[iCurrentCutPoint] = aSplitPoints.get(j);
					iCurrentCutPoint++;
				}

				aCutPoints[iCurrentCutPoint] = Double.POSITIVE_INFINITY;

				/* Create range array where intervals are represented */
				ArrayList<ArrayList<Object>> aRangeBins = new ArrayList<ArrayList<Object>>();

				for (int j = 0; j < aCutPoints.length - 1; j++){
					ArrayList<Object> aAuxiliarRangeArray = new ArrayList<Object>();
					aAuxiliarRangeArray.add(aCutPoints[j]);
					aAuxiliarRangeArray.add(aCutPoints[j+1]);

					aRangeBins.add(aAuxiliarRangeArray);
				}

				aRangeBins.get(0).set(0, dMinInterval);
				aRangeBins.get(aRangeBins.size()-1).set(1, dMaxInterval); /* So the boundaries are not +/- Infinity */

				/* Compute values and bins */
				int iColumnSize = oActualColumn.getSize();
				Object oMissingValue = oActualColumn.getMissingValue();
				Object oNullValue = oActualColumn.getNullValue();
				Object oEmptyValue = oActualColumn.getEmptyValue();

				int iBinIndex = 0;
				boolean iEndFlag = false;
				for (int j = 0; j < iColumnSize; j++){
					iBinIndex = 0;
					iEndFlag = false;

					/* Get a value */
					Object oValue = oActualColumn.getElement(j);

					if (oValue != null){
						if (!oValue.equals(oMissingValue) &&
								!oValue.equals(oEmptyValue) &&
								!oValue.equals(oNullValue)){
							iBinIndex = 1;

							for (int k = 0; k < aCutPoints.length && iEndFlag == false; k++){
								if (oActualColumn.getType() == ColumnType.Numerical){
									if(((Double)oValue).doubleValue() > aCutPoints[k] && ((Double)oValue).doubleValue() <= aCutPoints[k+1])
										iEndFlag = true;
								}
								else
									if(((Integer)oValue).doubleValue() > aCutPoints[k] && ((Integer)oValue).doubleValue() <= aCutPoints[k+1])
										iEndFlag = true;

								iBinIndex++;
							}

							/* Assign the bin to the value, -2 taking account that the counter has been increased by one
							 * in the last interation */
							oResultColumn.addValue(aRangeBins.get(iBinIndex-2));
						}
						else{
							/* It's invalid, then assign the correspondent invalid value to the return column */
							if (oValue.equals(oMissingValue))
								oResultColumn.addValue(oResultColumn.getMissingValue());
							else if (oValue.equals(oNullValue))
								oResultColumn.addValue(oResultColumn.getNullValue());
							else if (oValue.equals(oEmptyValue))
								oResultColumn.addValue(oResultColumn.getEmptyValue());
						}
					}
					else{
						/* If it's null */
						if (oMissingValue == null)
							oResultColumn.addValue(oResultColumn.getMissingValue());
						else if (oNullValue == null)
							oResultColumn.addValue(oResultColumn.getNullValue());
						else if (oEmptyValue == null)
							oResultColumn.addValue(oResultColumn.getEmptyValue());
					}
				}
				aRangeDiscretization.add(oResultColumn);
			}
			else{
				aRangeDiscretization.add(oActualColumn);
			}
		}
		this.rgoResultColumns = aRangeDiscretization;
		/* Adds the label column */
		this.rgoResultColumns.add(this.oDataset.getColumn(this.oDataset.getColumns().size()-1));
	}

}
