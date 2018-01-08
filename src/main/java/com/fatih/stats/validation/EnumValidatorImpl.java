package com.fatih.stats.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

	List<String> availableValues = null;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && availableValues.contains(value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(EnumValidator constraintAnnotation) {
		availableValues = new ArrayList<>();
		Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

		Enum[] enumValArr = enumClass.getEnumConstants();

		for (Enum enumVal : enumValArr) {
			availableValues.add(enumVal.toString().toLowerCase());
		}

	}

}