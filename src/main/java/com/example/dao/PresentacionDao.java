package com.example.dao;

import com.example.entities.Presentacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentacionDao extends JpaRepository<Presentacion,Long>{
    


}
