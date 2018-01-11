package com.fatih.stats.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fatih.stats.validation.ArrayEnumValidator;
import com.fatih.stats.validation.Measures;

public class ChartInput {

	@NotNull(message = "dimensions.null.error")
	@Size(min = 1, max = 1, message = "dimensions.length.error")
	private String[] dimensions;

	@ArrayEnumValidator(enumClazz = Measures.class, message = "measures.values.error")
	@Size(min = 1, max = 3, message = "measures.length.error")
	private String[] measures;

	public String[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(String[] dimensions) {
		this.dimensions = dimensions;
	}

	public String[] getMeasures() {
		return measures;
	}

	public void setMeasures(String[] measures) {
		this.measures = measures;
	}

}
