package com.n3xgen.processor.cvsjson.cloud.stream.service;

/**
 * 
 * @author Jafet Malváez
 * 
 * Interface for CSV and XML transformation
 * to a JSON
 *
 */
public interface TransformDataService {

	/**
	 * 
	 * Method definition to transform from a CSV data
	 * to a JSON
	 * 
	 * @param data information to be transformed
	 * @param hasHeaders a flag to handle cases where the CSV file has no headers (true when has headers, false when has no headers)
	 * @return return the data transformed into a JSON
	 */
	public String transformCSVData(String data, Boolean hasHeaders);
	
	/**
	 * 
	 * Method definition to transform from a JSON data
	 * to a CSV
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a CSV
	 */
	public String transformJSONData(String data);
	
	/**
	 * 
	 * Method definition to transform from XML data
	 * to a JSON
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a CSV
	 */
	public String transformXMLData(String data);
	
	/**
	 * 
	 * Method definition to transform from JSON data
	 * to a XML
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a CSV
	 */
	public String transformJSONToXMLData(String data);
	
}
