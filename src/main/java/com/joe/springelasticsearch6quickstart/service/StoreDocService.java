package com.joe.springelasticsearch6quickstart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.joe.springelasticsearch6quickstart.document.StoreDoc;

public interface StoreDocService {

	
	Page<StoreDoc> findAll(Pageable pageable);
	
	
	
	Page<StoreDoc> searchInName(String keyword, Pageable pageable);
	
	/**
	 * 查询name字段 越近越好
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchInNameCloserBetter(String keyword, Pageable pageable);
	
	/**
	 * Search in both name and mainproducts fields
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> search(String keyword, Pageable pageable);
	
	/**
	 * Search in both name and mainproducts fields; 拆词后两个词隔的越近分越高
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchCloserBetter(String keyword, Pageable pageable);
		
	
	Page<StoreDoc> searchFuzzily(String keyword, PageRequest pageable);
	
	/**
	 * Search in full text to avoid cross field IDF impact; But you cannot highlight the field that are not in search fields. 
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchFulltext(String keyword, PageRequest pageable);
	
	/**
	 * Search cross fields: https://www.elastic.co/guide/en/elasticsearch/guide/current/_cross_fields_queries.html
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<StoreDoc> searchCorssFields(String keyword, PageRequest pageable);


 
	
}
