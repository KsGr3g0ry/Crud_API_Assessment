package com.example.demo.dao;

import com.example.demo.model.Donut;
import org.springframework.data.repository.CrudRepository;

public interface DonutRepository extends CrudRepository<Donut, Long> {
}
