package com.edu.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.ecommerce.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {



}
