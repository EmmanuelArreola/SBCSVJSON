package com.n3xgen.processor.cvsjson.cloud.stream.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Jafet Malváez
 *
 * Class with transformation methods,
 * from CSV data to a JSON (and vice versa)
 * and from a XML data to a JSON (and vice versa)
 *
 */
@Log4j2
public class TransformDataServiceImpl implements TransformDataService {
	
	/**
	 * 
	 * Method implementation to transform from a CSV data
	 * to a JSON
	 * 
	 * @param data information to be transformed
	 * @param hasHeaders a flag to handle cases where the CSV file has no headers (true when has headers, false when has no headers)
	 * @return return the data transformed into a JSON
	 */
	@Override
	public String transformCSVData(String data, Boolean hasHeaders) {
		
		String result = "{\"JSON\" : \"Empty JSON\" }";
		List<String> fieldNamesStatic = new ArrayList<String>();
		
		if (data != null && !data.equals("")) {
			
			log.info("Data recieved: {\n" + data + "\n}");
			
			try (InputStream in = new ByteArrayInputStream(data.getBytes())) {
				CSVParser csv = CSVParser.parse(in,Charset.forName("UTF-8"), CSVFormat.EXCEL);			    
			    List<CSVRecord> csvRecords = csv.getRecords();
			    
			    for (int i = 0; i < csvRecords.get(0).size(); i++) {
	    			fieldNamesStatic.add("Column" + (i + 1));
	    		}

			    if (csv.getRecordNumber() > 0) {
			    	List<String> fieldNames = csvRecords.get(0).toList();
		    		
			    	if (hasHeaders) {
			    		csvRecords.remove(0);
			    	}
			    	
			    	log.info("Headers: { " + fieldNames + " }");
			    	log.info("Record number in csv file: { " + csvRecords.size() + " }");
				    
			    	List<Map<String, String>> list = new ArrayList<> ();
				    csvRecords.forEach(element -> {
				       
				    	List<String> x = element.toList();
				        Map< String, String > obj = new LinkedHashMap<> ();
				        
				        for (int i = 0; i < fieldNames.size(); i++) {
				            if (hasHeaders) {
				            	obj.put(fieldNames.get(i), x.get(i));
				            } else {
				            	obj.put(fieldNamesStatic.get(i), x.get(i));
				            }
				        }
				        
				        list.add(obj);
				        
				    });
				    
				    ObjectMapper mapper = new ObjectMapper();
				    mapper.enable(SerializationFeature.INDENT_OUTPUT);
				    result = mapper.writeValueAsString(list);
				    
			    } else {
			    	
			    	log.info("No data was processed");
			    	
			    }
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		} else {
			
			log.info("No data was recieved.");
			
		}
		
		log.info("The result JSON is:\n" + result);
		return result;
		
	}
	
	/**
	 * 
	 * Method implementation to transform from a JSON data
	 * to a CSV
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a CSV
	 * 
	 * @author Method created by Emmanuel Arreola
	 */
	@Override
	public String transformJSONData(String data) {
		
		String csvResult = "CSV\n"
						 + "Empty CSV";
		
		try (InputStream in = new ByteArrayInputStream(data.getBytes())) {
			
			JsonNode jsonTree = new ObjectMapper().readTree(in);

			if (!jsonTree.isNull() && !data.equals("")) {
				
				log.info("Data recieved: {\n" + data + "\n}");
				
				Builder csvSchemaBuilder = new CsvSchema.Builder();

				JsonNode firstObject = jsonTree.elements().next();
				
				if(!firstObject.isEmpty()) {
					
					firstObject.fieldNames().forEachRemaining(fieldName -> {
						csvSchemaBuilder.addColumn(fieldName);
					});

					CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
					CsvMapper csvMapper = new CsvMapper();
					csvResult = csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValueAsString(jsonTree);
					log.info(csvResult);
					
				} else {
					
					log.info("There is no key values");
					
				}
			} else {
				
				log.info("No data was processed");
				
			}

		}catch (JsonParseException e) {
			log.info("JSON not correctly formated");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("The result CSV is:\n" + csvResult);
		return csvResult;
	}
	
	/**
	 * 
	 * Method implementation to transform from a XML data to a JSON
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a JSON
	 * 
	 * @author Method created by Emmanuel Arreola
	 * 
	 */

	@Override
	public String transformXMLData(String data) {
		String JSONResult = "{\"XML\" : \"Not processed\"}";

		if (!data.isEmpty()) {
			
			log.info("Data recieved: {\n" + data + "\n}");
			
			try {
				JSONObject json = XML.toJSONObject(data);
				JSONResult = json.toString();

				if (JSONResult == null) {
					JSONResult = "XML not correctly formated";
				}
			} catch (JSONException e) {
				log.info(e.toString());
			}
		}
		
		log.info("The result JSON is:\n" + JSONResult);
		return JSONResult;
	}
	
	/**
	 * 
	 * Method implementation to transform from JSON data to a XML
	 * 
	 * @param data information to be transformed
	 * @return return the data transformed into a XML
	 * 
	 * @author Method created by Emmanuel Arreola
	 * 
	 */

	@Override
	public String transformJSONToXMLData(String data) {
		
		String XMLResult = "<?xml version=\\\"1.0\\\" ?><result>No JSON data have been processed</result>";
		
		if (!data.isEmpty()) {
			log.info("Data recieved: {\n" + data + "\n}");

			try {

				JSONObject json = new JSONObject(data);

				XMLResult = "<?xml version=\"1.0\" ?>" + XML.toString(json);

			} catch (JSONException e) {
				log.info(e.toString());
			}
		}
		
		log.info("The result XML is:\n" + XMLResult);
		return XMLResult;
	}

}
