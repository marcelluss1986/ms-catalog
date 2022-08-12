package com.mdss.mscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdss.mscatalog.dto.RoleDto;
import com.mdss.mscatalog.dto.UserDto;
import com.mdss.mscatalog.dto.UserInsertDto;
import com.mdss.mscatalog.dto.UserUpdateDto;
import com.mdss.mscatalog.entities.Role;
import com.mdss.mscatalog.entities.User;
import com.mdss.mscatalog.repositories.RoleRepository;
import com.mdss.mscatalog.repositories.UserRepository;
import com.mdss.mscatalog.services.exceptions.DatabaseException;
import com.mdss.mscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDto> findAllPaged(Pageable pageable){
		Page<User> list = repository.findAll(pageable);
		return list.map(x-> new UserDto(x));
	}
	
	@Transactional(readOnly = true)
	public UserDto findById(Long Id) {
		Optional<User> obj = repository.findById(Id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		return new UserDto(entity);
	}
	
	@Transactional
	public UserDto insert(UserInsertDto dto) {
		User entity = new User();
		copyEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDto(entity);
	}
	
	@Transactional
	public UserDto update(Long id, UserUpdateDto dto) {
		try{User entity = repository.getReferenceById(id);
		copyEntity(dto, entity);
		return new UserDto(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyEntity(UserDto dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		for (RoleDto roleDto: dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDto.getId());
			entity.getRoles().add(role);
		}
		entity = repository.save(entity);
	}
}
