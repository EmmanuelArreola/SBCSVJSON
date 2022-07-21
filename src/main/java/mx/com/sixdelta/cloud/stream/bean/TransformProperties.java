package mx.com.sixdelta.cloud.stream.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "transform.params")
public class TransformProperties {

	private Boolean csvWhithHeaders = true;
	private String transformTo = "CSV-JSON";
	
	public Boolean isCsvWhithHeaders() {
		return csvWhithHeaders;
	}
	public void setCsvWhithHeaders(Boolean csvwhithheaders) {
		this.csvWhithHeaders = csvwhithheaders;
	}
	public String getransformTo() {
		return transformTo;
	}
	public void settransformTo(String transformto) {
		this.transformTo = transformto;
	}
	
}
