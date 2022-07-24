package com.mdss.mscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdss.mscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
