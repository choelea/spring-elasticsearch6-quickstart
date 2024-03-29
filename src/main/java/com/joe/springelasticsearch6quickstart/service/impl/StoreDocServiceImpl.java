package com.joe.springelasticsearch6quickstart.service.impl;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.joe.springelasticsearch6quickstart.ExtResultMapper;
import com.joe.springelasticsearch6quickstart.document.StoreDoc;
import com.joe.springelasticsearch6quickstart.service.StoreDocService;

@Service
public class StoreDocServiceImpl implements StoreDocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreDocServiceImpl.class);

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ExtResultMapper extResultMapper;

	@Override
	public Page<StoreDoc> findAll(Pageable pageable) {
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1)).build();
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	@Override
	public Page<StoreDoc> searchInName(String keyword, Pageable pageable) {
		QueryBuilder queryBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			queryBuilder = QueryBuilders.matchAllQuery();
		} else {
			queryBuilder = QueryBuilders.matchQuery(StoreDoc._name, keyword);
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1)).build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	@Override
	public Page<StoreDoc> searchInNameCloserBetter(String keyword, Pageable pageable) {
		Assert.notNull(keyword, "keyword cannot be null");
		Assert.hasLength(keyword.trim(), "keyword cannot be null nor empty");
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.matchQuery(StoreDoc._name, keyword).boost(10));
		queryBuilder.should(QueryBuilders.matchPhraseQuery(StoreDoc._name, keyword).slop(30).boost(10));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1)).build();
		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	@Override
	public Page<StoreDoc> search(String keyword, Pageable pageable) {
		QueryBuilder queryBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			queryBuilder = QueryBuilders.matchAllQuery();
		} else {
			queryBuilder = QueryBuilders.multiMatchQuery(keyword, StoreDoc._name, StoreDoc._mainProducts);
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1),
						new HighlightBuilder.Field(StoreDoc._mainProducts).numOfFragments(1))
				.build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class,  extResultMapper);
		return page;
	}

	@Override
	public Page<StoreDoc> searchCloserBetter(String keyword, Pageable pageable) {
		Assert.notNull(keyword, "keyword cannot be null");
		Assert.hasLength(keyword.trim(), "keyword cannot be null nor empty");
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(
				QueryBuilders.multiMatchQuery(keyword).field(StoreDoc._name, 10).field(StoreDoc._mainProducts, 10));
		queryBuilder.should(QueryBuilders.matchPhraseQuery(StoreDoc._name, keyword).slop(3).boost(50));

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1),
						new HighlightBuilder.Field(StoreDoc._mainProducts).numOfFragments(1))
				.build();
		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	@Override
	public Page<StoreDoc> searchFuzzily(String keyword, PageRequest pageable) {
		QueryBuilder queryBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			queryBuilder = QueryBuilders.matchAllQuery();
		} else {
			queryBuilder = QueryBuilders.matchQuery(StoreDoc._name, keyword).fuzziness(Fuzziness.AUTO);
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1)).build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	 

	@Override
	public Page<StoreDoc> searchFulltext(String keyword, PageRequest pageable) {
		QueryBuilder queryBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			queryBuilder = QueryBuilders.matchAllQuery();
		} else {
			queryBuilder = QueryBuilders.matchQuery(StoreDoc._fullText, keyword);
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1),
						new HighlightBuilder.Field(StoreDoc._mainProducts).numOfFragments(1))
				.build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

	@Override
	public Page<StoreDoc> searchCorssFields(String keyword, PageRequest pageable) {
		QueryBuilder queryBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			queryBuilder = QueryBuilders.matchAllQuery();
		} else {
			queryBuilder = QueryBuilders.multiMatchQuery(keyword, StoreDoc._name, StoreDoc._mainProducts)
					.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
				.withHighlightFields(new HighlightBuilder.Field(StoreDoc._name).numOfFragments(1),
						new HighlightBuilder.Field(StoreDoc._mainProducts).numOfFragments(1))
				.build();

		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		Page<StoreDoc> page = elasticsearchTemplate.queryForPage(searchQuery, StoreDoc.class);
		return page;
	}

}
