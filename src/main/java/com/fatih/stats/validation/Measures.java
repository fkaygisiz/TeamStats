package com.fatih.stats.validation;

public enum Measures {
	REVENUE("revenue"), CHAMPIONS("champions"), LEAGUES("leagues");

	private String value;

	private Measures(String value) {
		this.value = value;
	}

	public static Measures fromValue(String val) {
		for (Measures measure : Measures.values()) {
			if (measure.getValue().equals(val)) {
				return measure;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}
}
