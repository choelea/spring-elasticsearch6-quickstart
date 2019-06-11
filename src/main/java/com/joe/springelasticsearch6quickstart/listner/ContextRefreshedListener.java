package com.joe.springelasticsearch6quickstart.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.joe.springelasticsearch6quickstart.document.I18nField;
import com.joe.springelasticsearch6quickstart.document.ProductDoc;
import com.joe.springelasticsearch6quickstart.document.StoreDoc;
import com.joe.springelasticsearch6quickstart.document.StoreDocBuilder;
import com.joe.springelasticsearch6quickstart.document.SupplierDoc;
import com.joe.springelasticsearch6quickstart.document.SupplierDocBuilder;
 
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		elasticsearchTemplate.deleteIndex(ProductDoc.class);
		elasticsearchTemplate.createIndex(ProductDoc.class);
		elasticsearchTemplate.putMapping(ProductDoc.class);
		
		String setting = "{\r\n" + 
				"    \"analysis\": {\r\n" + 
				"        \"analyzer\": {\r\n" + 
				"            \"whitespaceStanddard\": {\r\n" + 
				"                \"type\":      \"standard\",\r\n" + 
				"                \"tokenizer\": \"whitespace\"\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}";
		elasticsearchTemplate.deleteIndex(StoreDoc.class);
		
		// This is another way to put setting when create index; but the custom analyzer doesn't work for now
//		elasticsearchTemplate.createIndex(StoreDoc.class, setting); 
		elasticsearchTemplate.createIndex(StoreDoc.class);
		elasticsearchTemplate.putMapping(StoreDoc.class);
		createTestData();
	}
 
	private void createTestData(){
		createStoreDocs();
	}
	
	 

	private void createStoreDocs() {
		elasticsearchTemplate.index(new StoreDocBuilder(0l).name("XiaoMi Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(1l).name("Oppo Authorized Owned by Joe in Wuhan Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(2l).name("Meizu Authorized Shop Double Authorized").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(3l).name("Sung Authorized Shop").mainProducts("Smart Phone , Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(4l).name("Vivo Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(5l).name("Lenovo Authorized  Shop").mainProducts("Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(6l).name("Sony Authorized VIP Shop").mainProducts("Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(7l).name("Apple Store").mainProducts("Ipad, Mac Pro").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(8l).name("Samsung Authorized Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(9l).name("Smart Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(10l).name("Mark's Mobile Shop").mainProducts("Smart Phone, Stupid Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(11l).name("Charlice Mobile Shop").mainProducts("Apple Phone, Old Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(12l).name("Charlice Mobile Shop").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(13l).name("Charlice Mobile Shop").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(14l).name("Oppo Authorized Shop Owned by Joe in Wuhan").mainProducts("Apple Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(15l).name("Charlice Fruit Shop").mainProducts("Pear, Watermelon").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(16l).name("Jane's Mobile Shop").mainProducts("Smart-Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(17l).name("Charlise Shop").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(18l).name("Authorized Owned by Joe Phone Meizu Shop").mainProducts("Smart Phone").fullText().buildIndex());		
		elasticsearchTemplate.index(new StoreDocBuilder(19l).name("Meizu Authorized Phone Shop Located in Wuhan Optic Valley Software Park").mainProducts("Smart Phone").fullText().buildIndex());
		elasticsearchTemplate.index(new StoreDocBuilder(20l).name("Authorized Phone Meizu Shop").mainProducts("Smart Phone").fullText().buildIndex());
		for(int i=21; i<30; i++) {
			elasticsearchTemplate.index(new StoreDocBuilder(Long.valueOf(i).longValue()).name("Charlise Shop").mainProducts("Apple Phone").fullText().buildIndex());
		}
	}

	
}
