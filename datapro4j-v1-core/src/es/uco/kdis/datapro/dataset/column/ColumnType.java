package es.uco.kdis.datapro.dataset.column;

/**
 * This enumeration contains the different types of columns supported by the library. 
 * The following types are currently supported:
 * <ul>
 * <li>Binary</li>
 * <li>Bit</li>
 * <li>Categorical</li>
 * <li>Date</li>
 * <li>Integer</li>
 * <li>Long</li>
 * <li>Multivalue</li>
 * <li>Nominal</li>
 * <li>Numerical</li>
 * <li>Range</li>
 * </ul>
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
 * <li>	(ARQ|IMA|RPB, 0.5, April 2015)	Long and multi types created.
 * <li> (AMP, 0.4, July 2012) 			Bit type created.
 * <li> (JRR, 0.3, March 2012) 			Boolean type removed. Binary type created instead.
 * <li> (JRR|JML, 0.2, September 2011) 	Performance, refactoring and designing issues. Improvements.
 * <li> (JTL, 0.1, May 2011) 			Initial version. Intrusion strategies.
 * </ul>
 * <p>
 @author Aurora Ramirez Quesada (ARQ, 0.5)
 @author Rocio Pinillos Bermudez (RPB, 0.5)
 @author Ignacio Monferrer Altava (IMA, 0.5)
 @author Antonio Jose Muñoz Perez (AMP, 0.4)
 @author Jose Raul Romero (JRR, 0.2, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.5
 *
 **/
public enum ColumnType {
	
	/** Boolean attribute */
	Binary,
	
	/** Bit attribute */
	Bit,
	
	/** Categorical attribute */
	Categorical,

	/** Date attribute */
	Date,
	
	/** Integer attribute */
	Integer,
	
	/** Long attribute */
	Long,
	
	/** Multivalue attribute */
	Multivalue,
	
	/** Nominal attribute */
	Nominal,
	
	/** Numerical attribute */
	Numerical,

	/** Range attribute */
	Range
}