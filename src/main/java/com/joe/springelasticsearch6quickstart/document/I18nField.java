package com.joe.springelasticsearch6quickstart.document;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class I18nField implements Comparable<I18nField>{

	@Field(type = FieldType.Text, analyzer="english")
	private String en;

	@Field(type = FieldType.Text, analyzer="spanish")
	private String es;

	@Field(type = FieldType.Text, analyzer="portuguese")
	private String pt;

	@Field(type = FieldType.Text, analyzer="russian")
	private String ru;
	
	@JsonIgnore
	private int termScore = 0;
	
	public I18nField() {
		super();
	}

	public I18nField(String en, String es, String pt, String ru) {
		super();
		this.en = en;
		this.es = es;
		this.pt = pt;
		this.ru = ru;
	}

	public int getTermScore() {
		return termScore;
	}

	public void setTermScore(int termScore) {
		this.termScore = termScore;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	
	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	@JsonIgnore
	public String getValue() {
		String language = LocaleContextHolder.getLocale().getLanguage();
		String value = null;
		switch (language) {
		case "en":
			value = getEn();
			break;
		case "es":
			value = getEs();
			break;
		case "pt":
			value = getPt();
			break;
		case "ru":
			value = getRu();
			break;
		default:
			value = getEn();
			break;
		}
		if(StringUtils.isEmpty(value)) {
			value = getEn();
		}
		return value;
	}

	@Override
	public int compareTo(I18nField arg0) {
		return arg0.getTermScore()-getTermScore();
	}

}
