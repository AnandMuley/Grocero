package com.grocero.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.grocero.utils.MeasurementUnit;

@Document(collection = "products")
public class ProductBean {

	@Id
	private String id;
	private String name;
	private MeasurementUnit measurementUnit;

	public ProductBean() {
	}

	public ProductBean(String id, String name, MeasurementUnit measurementUnit) {
		super();
		this.id = id;
		this.name = name;
		this.measurementUnit = measurementUnit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MeasurementUnit getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(MeasurementUnit measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", name=" + name
				+ ", measurementUnit=" + measurementUnit + "]";
	}

}
