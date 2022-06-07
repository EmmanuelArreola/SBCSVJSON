package com.n3xgen.processor.cvsjson.cloud.stream;

import java.util.function.Function;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3xgen.processor.cvsjson.cloud.stream.bean.TransformProperties;
import com.n3xgen.processor.cvsjson.cloud.stream.service.TransformDataService;
import com.n3xgen.processor.cvsjson.cloud.stream.service.TransformDataServiceImpl;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

/**
 * The class that exposes the bean to be used in
 * the streams as a processor (SCDF)
 * 
 * @author Jafet Malváez
 *
 */
@Log4j2
@EnableConfigurationProperties(TransformProperties.class)
@Configuration
public class TransformStream {

	private TransformProperties transformProperties;
	private static final String CSVJSON_VALUE = "CSV-JSON";
	private static final String JSONCSV_VALUE = "JSON-CSV";
	private static final String XMLJSON_VALUE = "XML-JSON";
	private static final String JSONXML_VALUE = "JSON-XML";
	
	public TransformStream(TransformProperties transformProperties) {
		this.transformProperties = transformProperties;
	}
	
	/**
	 * This method does the transformation selected
	 * from CSV to JSON
	 * from JSON to CSV
	 * from XML to JSON
	 * from JSON to XML
	 * 
	 * @param objectMapper 
	 * @return return the result of transformation selected in a String form
	 */
	@Bean
    public Function<Flux<String>, Flux<String>> transformProcessor(ObjectMapper objectMapper) {
		return jsonProcessor -> jsonProcessor.map(
			data -> {
				TransformDataService transformer = new TransformDataServiceImpl();
				log.info("/**********Transformation starts***************/");
				if (transformProperties.getTransformto().equals(CSVJSON_VALUE)) {
					return transformer.transformCSVData(data, transformProperties.isCsvwhithheaders());
				} else if (transformProperties.getTransformto().equals(JSONCSV_VALUE)) {
					return transformer.transformJSONData(data);
				} else if (transformProperties.getTransformto().equals(XMLJSON_VALUE)) {
					return transformer.transformXMLData(data);
				} else if (transformProperties.getTransformto().equals(JSONXML_VALUE)) {
					return transformer.transformJSONToXMLData(data);
				} else {
					return "No option available";
				}
			});
	}
	
}
