package mx.com.sixdelta.cloud.stream;

import java.util.function.Function;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.sixdelta.cloud.stream.bean.TransformProperties;
import mx.com.sixdelta.cloud.stream.service.TransformDataService;
import mx.com.sixdelta.cloud.stream.service.TransformDataServiceImpl;
import reactor.core.publisher.Flux;

@EnableConfigurationProperties(TransformProperties.class)
@Configuration
public class TransformStream {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransformStream.class);
	private TransformProperties transformProperties;
	private static final String CSVJSON_VALUE = "CSV-JSON";
	private static final String JSONCSV_VALUE = "JSON-CSV";
	private static final String XMLJSON_VALUE = "XML-JSON";
	private static final String JSONXML_VALUE = "JSON-XML";
	
	public TransformStream(TransformProperties transformProperties) {
		this.transformProperties = transformProperties;
	}
	
	@Bean
    public Function<Flux<String>, Flux<String>> transformProcessor(ObjectMapper objectMapper) {
		return jsonProcessor -> jsonProcessor.map(
			data -> {
				TransformDataService transformer = new TransformDataServiceImpl();
				log.info("/**********Transformation starts***************/");
				if (transformProperties.getransformTo().equals(CSVJSON_VALUE)) {
					return transformer.transformCSVData(data, transformProperties.isCsvWhithHeaders());
				} else if (transformProperties.getransformTo().equals(JSONCSV_VALUE)) {
					return transformer.transformJSONData(data);
				} else if (transformProperties.getransformTo().equals(XMLJSON_VALUE)) {
					return transformer.transformXMLData(data);
				} else if (transformProperties.getransformTo().equals(JSONXML_VALUE)) {
					return transformer.transformJSONToXMLData(data);
				} else {
					return "No option available";
				}
			});
	}
	
}
