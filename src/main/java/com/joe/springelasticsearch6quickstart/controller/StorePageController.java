package com.joe.springelasticsearch6quickstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joe.springelasticsearch6quickstart.document.StoreDoc;
import com.joe.springelasticsearch6quickstart.service.StoreDocService;

@Controller
@RequestMapping("/page")
public class StorePageController {
	@Autowired
	private StoreDocService storeDocService;
	
	@GetMapping("/store-names")
    public String searchStoreNames(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchInName(keyword, PageRequest.of(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNames";
    }
	
	@GetMapping("/store-names-closer-better")
    public String searchStoreNamesCloserBetter(Model model,String keyword) {
		Page<StoreDoc> page = null;
		if (StringUtils.isEmpty(keyword)) {
			page = storeDocService.findAll( PageRequest.of(0, 20));
		}else {
			page = storeDocService.searchInNameCloserBetter(keyword,  PageRequest.of(0, 20));			
		}
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeNamesCloserBetter";
    }
	

	@GetMapping("/stores")
    public String searchStores(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.search(keyword,  PageRequest.of(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "store/stores";
    }
	@GetMapping("/stores-closer-better")
    public String searchStoresCloserBetter(Model model,String keyword) {
		Page<StoreDoc> page = null;
		if (StringUtils.isEmpty(keyword)) {
			page = storeDocService.findAll( PageRequest.of(0, 20));
		}else {
			page =  storeDocService.searchCloserBetter( keyword,  PageRequest.of(0, 20));
		}
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storesCloserBetter";
    }
	
	@GetMapping("/stores-cross-fields-search")
    public String searchStoresCrossFields(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchCorssFields(keyword,  PageRequest.of(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/stores-cross-fields-search";
    }
	
	@GetMapping("/stores/fuzzy")
    public String searchStoresFuzzily(Model model,String keyword) {
		Page<StoreDoc> page =  storeDocService.searchFuzzily(keyword,  PageRequest.of(0, 20));
		model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "/store/storeFuzzy";
    }

}
