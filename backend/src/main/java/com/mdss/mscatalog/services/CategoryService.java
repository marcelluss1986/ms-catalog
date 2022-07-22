package com.mdss.mscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdss.mscatalog.dto.CategoryDto;
import com.mdss.mscatalog.entities.Category;
import com.mdss.mscatalog.repositories.CategoryRepository;
import com.mdss.mscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll(){
		List<Category> list = repository.findAll();
		
		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CategoryDto findById(Long Id) {
		Optional<Category> obj = repository.findById(Id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
		return new CategoryDto(entity);
	}
	
	@Transactional(readOnly = true)
	public CategoryDto insert(CategoryDto dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDto(entity);
	}
}
