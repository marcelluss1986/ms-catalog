package com.mdss.mscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mdss.mscatalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findAll(){
		List<Category>list = new ArrayList<>();
		list.add(new Category(1L,"Books"));
		list.add(new Category(2L, "Eletronics"));
		return ResponseEntity.ok().body(list);
	}
	
}
