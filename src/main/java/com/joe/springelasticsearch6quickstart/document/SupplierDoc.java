package com.joe.springelasticsearch6quickstart.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "supplier", type = "doc")
public class SupplierDoc implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1771788891355351392L;

	public SupplierDoc() {
	} // mandatory for Json Mapping

	public SupplierDoc(Long id, String name, List<I18nField> mainProducts, String type, Boolean isSelfRun, Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.mainProducts = mainProducts;
	}

	@Id
	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text, analyzer = "english")
	private String name;
	public static final String _name = "name";

	@Field(type = FieldType.Nested, analyzer = "english")
	private List<I18nField> mainProducts;
	public static final String _mainProducts_en = "mainProducts.en";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	
	public List<I18nField> getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(List<I18nField> mainProducts) {
		this.mainProducts = mainProducts;
	}

	@Override
	public String toString() {
		return "SupplierDoc [id=" + id + ", name=" + name + ", mainProducts=" + mainProducts + "]";
	}

}
