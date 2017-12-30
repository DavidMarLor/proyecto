package es.uco.kdis.datapro.dataset.source;

import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.dataset.FileDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
// Beware external dependencies
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ExcelDataset is a class that represents a dataset conformant to the Microsoft Excel standard specification.
 * This type of files has the basic features of all spreadsheets, using a grid of cells arranged in numbered 
 * rows and letter-named columns to organize data manipulations like arithmetic operations.
 * <p><b>Note</b><p>This class depends on the external library POI
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
 * <li> (RBL|ARQ, 0.3, April 2015)	Bugs fixed: reading/writing integer values, writing a specific number of decimals, load invalid values
 * <li> (JRR|JML|ARQ, 0.2,  March 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Rafael Barbudo Lunar (RBL, 0.3)
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Mario Orozco Borrego (MOB, 01)
 @version 0.3
 *
 **/
public class ExcelDataset extends FileDataset {

	/**
	 * <code>oExcelWorkBook</code> stores book used to store the dataset.
	 @since 0.1
	 **/
	private Workbook oExcelWorkBook;

	/**
	 * <code>oExcelSheet</code> stores sheet used in a <code>oExcelWorkBook</code> to store the dataset.
	 @since 0.1
	 **/
	private Sheet oExcelSheet;

	/** 
	 * <code>oFileReader</code> is meant for reading streams of characters in the file.
	 @since 0.2 
	 **/
	private InputStream oFileReader;

	/** 
	 * <code>iRowValue</code> is meant for indicating a specific row.
	 @since 0.2 
	 **/
	private int iRowValue;

	/** 
	 * <code>iNumberFinalGap</code> is meant for indicating the number of final instances to be ignored.
	 @since 0.2 
	 **/
	private int iNumberFinalGap;

	/**
	 * Default Microsoft Excel dataset constructor. The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 **/
	public ExcelDataset() {
		super();
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * Microsoft Excel dataset constructor having the name of the file as parameter. 
	 * The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 * @param sFileName
	 *            The name of the file that contains the dataset.
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 @see sFileName
	 **/
	public ExcelDataset(String sFileName) {
		super(sFileName);
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * Open a Microsoft Excel file. 
	 @throws IOException 
	 @since 0.2
	 @see obtainMetadata
	 @see iCursor
	 **/
	@Override
	protected void open() 
			throws IOException {
		/* Initialize iCursor to the first column */
		this.iCursor = 0;
		this.oFileReader = new FileInputStream(this.sFileName);

		/* Check the extension of the input file */
		if (this.sFileName.endsWith(".xlsx"))
			this.oExcelWorkBook = new XSSFWorkbook(this.oFileReader);

		else if (this.sFileName.endsWith(".xls"))
			this.oExcelWorkBook = new HSSFWorkbook(this.oFileReader);

		else
			throw new InputMismatchException("Invalid file extension");

		/* Default sheet used */
		this.oExcelSheet = this.oExcelWorkBook.getSheetAt(0);
	}

	/**
	 * Close the Microsoft Excel file. 
	 @throws IOException 
	 @since 0.2
	 @see oBufferedReader
	 **/	
	@Override
	protected void close() 
			throws IOException {
		this.oFileReader.close();
	}

	/**
	 * Read the metadata of a Microsoft Excel file
	 * @param sContentFormat <code>String</code> that specifies the format file. The following symbols are used:
	 * 			<ul>
	 * 				<li> <code>n</code>: Obtain the attribute names
	 * 				<li> <code>v</code>: Obtain the attribute values
	 * 				<li> <code>%</code>: Skip a row	
	 * 			</ul>
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to be read. Each column
	 * type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>i</code>: Integer column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 @throws IOException 
	 @throws IllegalFormatSpecificationException 
	 @since 0.2
	 @see addAttributeToSpecification
	 **/
	protected void obtainMetadata(String sContentFormat, String sColumnFormat) 
			throws IOException, IllegalFormatSpecificationException {

		int iContentFormatLength = sContentFormat.length();
		for (int i = 0; i < iContentFormatLength; i++) {
			/* Obtain the name of the attributes */
			if(sContentFormat.charAt(i)=='n') {
				Row oRow = this.oExcelSheet.getRow(i);

				/* An empty row */
				if(oRow==null)
					throw new InputMismatchException("Row number "+i+" is an empty row.");

				int iNumberColumns = oRow.getLastCellNum();

				if(sColumnFormat!=null && sColumnFormat.length()!=iNumberColumns)
					throw new InputMismatchException("Incompatible number of columns");

				else {
					int iColumnFormat = sColumnFormat.length();

					for (int j = 0; j < iColumnFormat; j++) {
						switch (sColumnFormat.charAt(j)) {

						/* Nominal column */
						case 's': 
							this.rgoColumns.add(new NominalColumn(oRow.getCell(j).toString()));
							break;

							/* Categorical column */
						case 'c': 
							this.rgoColumns.add(new CategoricalColumn(oRow.getCell(j).toString()));
							break;

							/* Real column */
						case 'f': 
							this.rgoColumns.add(new NumericalColumn(oRow.getCell(j).toString()));
							break;

							/* Integer column */
						case 'i': 
							this.rgoColumns.add(new IntegerColumn(oRow.getCell(j).toString()));
							break;
						}
						this.iCursor++;
					}
				}
			}
			/* Obtain first row for the attribute values */
			if(sContentFormat.charAt(i)=='v') {
				this.iRowValue = i;
				this.iNumberFinalGap = sContentFormat.length()-(this.iRowValue+1);
				break;
			}
		}
	}

	/**
	 * Method that adds all the values/elements of the <code>@DATA</code> section in the
	 * dataset to the columns.
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to 
	 * be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>i</code>: Integer column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws IndexOutOfBoundsException
	 @throws IOException
	 @throws NotAddedValueException
	 @see addValue()
	 @since 0.2
	 **/
	@Override
	protected void addAllValues(String sColumnFormat) 
			throws IndexOutOfBoundsException, IOException, NotAddedValueException {

		int iSize, iColumnIndex;

		/* Search for a non-empty row */
		Row oRow = this.oExcelSheet.getRow(this.iRowValue);

		/* An empty row */
		if(oRow==null)
			throw new InputMismatchException("Row number "+this.iRowValue+" is an empty row.");

		int iNumberColumns = oRow.getLastCellNum();

		if(sColumnFormat.length()!=iNumberColumns)
			throw new InputMismatchException("Incompatible number of columns");

		else {
			/* Number of instances to be read */
			iSize = this.oExcelSheet.getLastRowNum()-this.iNumberFinalGap;
			
			/* Read every instance */
			for(int i = this.iRowValue; i <= iSize; i++) {
				oRow = this.oExcelSheet.getRow(i);
				iColumnIndex = 0;

				/* Obtain all the columns for this row */
				/* Skip empty rows */
				if(oRow != null)
					for (int j = 0; j < sColumnFormat.length(); j++) {	

						String sValue = oRow.getCell(j).toString();
						
						/* Skip the column if the symbol '%' is reached */
						if(sColumnFormat.charAt(j) != '%'){ 

							/* Insert a missing value */
							if (sValue.equalsIgnoreCase(this.sMissingValue))
								this.rgoColumns.get(iColumnIndex).addValue(MissingValue.getMissingValue());

							/* Insert a null value */
							else if (sValue.equalsIgnoreCase(this.sNullValue))
								this.rgoColumns.get(iColumnIndex).addValue(NullValue.getNullValue());

							/* Insert an empty value */
							else if (sValue.equalsIgnoreCase(this.sEmptyValue))
								this.rgoColumns.get(iColumnIndex).addValue(EmptyValue.getEmptyValue());

							else{
								switch (sColumnFormat.charAt(j)) {

								/* Nominal column */
								case 's': 
									this.rgoColumns.get(iColumnIndex).addValue(sValue);
									break;

									/* Categorical column */
								case 'c': 
									this.rgoColumns.get(iColumnIndex).addValue(sValue,true);
									break;

									/* Integer column */
								case 'i':
									sValue = sValue.replace(".0", "");
									this.rgoColumns.get(iColumnIndex).addValue(Integer.valueOf(sValue));
									break;

									/* Real column */
								case 'f': 
									this.rgoColumns.get(iColumnIndex).addValue(Double.valueOf(sValue));
									break;
								}
							}
							
							/* Next column */
							iColumnIndex++;
						}
					}
			}
		}
	}

	/**
	 * Open the source file and obtain all its information (metadata and data).
	 * @param sContentFormat <code>String</code> that specifies the format file. The following symbols are used:
	 * 			<ul>
	 * 				<li> <code>n</code>: Obtain the attribute names
	 * 				<li> <code>v</code>: Obtain the attribute values
	 * 				<li> <code>%</code>: Skip a row	
	 * 			</ul>
	 * 				Example: %n%%v%%% (1) omits the first line, (2) reads the column names, (3) omit
	 * 						the two following lines, (4) read the dataset values and (5) ignore the 
	 * 						last 3 lines.
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>i</code>: Integer column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws NotAddedValueException
	 @throws IOException 
	 @throws IndexOutOfBoundsException 
	 @throws IllegalFormatSpecificationException 
	 @since 0.2
	 @see open()
	 @see obtainMetadat()
	 @see addAllValues()
	 @see close()
	 **/
	@Override
	public void readDataset(String sContentFormat, String sColumnFormat) 
			throws IOException, IndexOutOfBoundsException, NotAddedValueException, IllegalFormatSpecificationException {

		/* Check the sColumnFormat expression */
		if(!sColumnFormat.matches("(s|f|c|i|%)*"))
			throw new InputMismatchException("Invalid format "+sColumnFormat);

		/* Check the sContentFormat expression */
		if(!sContentFormat.matches("%*n%*v%*"))
			throw new InputMismatchException("Invalid format "+sContentFormat);

		this.open();
		this.obtainMetadata(sContentFormat,sColumnFormat);
		this.addAllValues(sColumnFormat);
		this.close();
	}

	/**
	 * Write the metadata in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the metadata.
	 @since 0.2
	 **/
	private void writeMetadata(Sheet oOutputSheet) {

		/* Create a new row to write the name of columns */
		Row oActualOutputRow = oOutputSheet.createRow(0);

		/* Write the names */
		int iNumberColumns = this.rgoColumns.size();
		for (int i = 0; i < iNumberColumns; i++)
			oActualOutputRow.createCell(i).setCellValue(this.rgoColumns.get(i).getName());
	}

	/**
	 * Write the data in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the data.
	 @since 0.2
	 **/
	private void writeData(Sheet oOutputSheet) {
		/* Output sheet where data are being to be written */
		InstanceIterator it = new InstanceIterator(this);

		/* Current instance */
		ArrayList<Object> rgoCurrentInstance;

		/* Row to be written */
		int iCurrentWritingRow = 0;

		int iNumberColumns = this.rgoColumns.size();
		double dExp = Math.pow(10, this.getNumberOfDecimals());

		/* Write the data */
		while(!it.isDone()) {
			iCurrentWritingRow++;
			Row oCurrentRow = oOutputSheet.createRow(iCurrentWritingRow);

			/* Obtain the current instance */
			rgoCurrentInstance = (ArrayList<Object>) it.currentInstance();

			for (int i = 0; i < iNumberColumns; i++) {
				Object oActualValue = rgoCurrentInstance.get(i);

				if (oActualValue != null) {

					if(!(oActualValue instanceof InvalidValue)){

						/* We need to treat booleans in a special way */
						if (getColumn(i).getType() == ColumnType.Binary) { 
							if ((Boolean) rgoCurrentInstance.get(i) == false)
								oCurrentRow.createCell(i).setCellValue(
										rgoValidBinaryFalseValues.get(0).toString());
							else
								oCurrentRow.createCell(i).setCellValue(
										rgoValidBinaryTrueValues.get(0).toString());
						}
						/* Integer value */
						else if (getColumn(i).getType() == ColumnType.Integer)
							oCurrentRow.createCell(i).setCellValue(
									Integer.valueOf(rgoCurrentInstance.get(i).toString()));

						/* Real value */
						else if (getColumn(i).getType() == ColumnType.Numerical){
							String sValue = rgoCurrentInstance.get(i).toString();
							oCurrentRow.createCell(i).setCellValue(
									Math.round(Double.parseDouble(sValue)*dExp)/dExp);
						}

						/* Categorical or nominal value */
						else
							oCurrentRow.createCell(i).setCellValue(
									(rgoCurrentInstance.get(i).toString()));
					} 
					else {
						if (oActualValue instanceof MissingValue)
							oCurrentRow.createCell(i).setCellValue(this.sMissingValue.toString());

						else if (oActualValue instanceof NullValue)
							oCurrentRow.createCell(i).setCellValue(this.sNullValue.toString());

						else
							oCurrentRow.createCell(i).setCellValue(this.sEmptyValue.toString());
					}
				}
				/* The value is null */
				else 
					oCurrentRow.createCell(i).setCellValue(this.sNullValue.toString());
			}
			it.next();
		}
	}

	/**
	 * Write in a new file the complete dataset.
	 * @param sOutputFile The name of the file where the dataset is written.
	 @throws IOException  		
	 @since 0.2
	 @see writeMetadata
	 @see writeData
	 **/
	@Override
	public void writeDataset(String sOutputFile) 
			throws IOException {
		FileOutputStream oFileWriter = new FileOutputStream(sOutputFile);

		/* The output document*/
		Workbook oOutputWorkbook;

		/* Check the extension of the output file */
		if (sOutputFile.endsWith(".xlsx"))
			oOutputWorkbook = new XSSFWorkbook();
		else
			oOutputWorkbook = new HSSFWorkbook();

		Sheet xOutputSheet = oOutputWorkbook.createSheet();

		/* Write the metadata*/
		this.writeMetadata(xOutputSheet);

		/* Write the data*/
		this.writeData(xOutputSheet);

		oOutputWorkbook.write(oFileWriter);		
		/* Close the writer */
		oFileWriter.close();
	}
}