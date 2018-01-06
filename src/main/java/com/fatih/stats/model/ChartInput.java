package com.fatih.stats.model;

import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChartInput {

	@NotNull(message = "dimensions.null.error")
	@Size(min = 1, max = 1, message = "dimensions.length.error")
	private String[] dimensions;
	
	@NotNull(message = "measures.null.error")
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

	@Override
	public String toString() {
		return "ChartInput [dimensions=" + Arrays.toString(dimensions) + ", measures=" + Arrays.toString(measures)
				+ ", getDimensions()=" + Arrays.toString(getDimensions()) + ", getMeasures()="
				+ Arrays.toString(getMeasures()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
