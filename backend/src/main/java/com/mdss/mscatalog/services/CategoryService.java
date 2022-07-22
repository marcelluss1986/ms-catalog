package com.mdss.mscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdss.mscatalog.dto.CategoryDto;
import com.mdss.mscatalog.entities.Category;
import com.mdss.mscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll(){
		List<Category> list = repository.findAll();
		
		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
		
		
	}
}
