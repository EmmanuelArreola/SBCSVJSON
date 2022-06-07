package com.n3xgen.processor.cvsjson.cloud.stream.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The definition of the parameters to be used in the custom app
 * This parameters should be visible from the deployment page in the SCDF dashboard
 * 
 * @author Jafet Malváez
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "transform.params")
public class TransformProperties {

	private boolean csvwhithheaders = true;
	private String transformto = "CSV-JSON";
	
}
