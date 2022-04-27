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
	public void setCsvWhithHeaders(Boolean csvWhithHeaders) {
		this.csvWhithHeaders = csvWhithHeaders;
	}
	public String getTransformTo() {
		return transformTo;
	}
	public void setTransformTo(String transformTo) {
		this.transformTo = transformTo;
	}
	
}
