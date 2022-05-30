package com.example.services;

import java.util.List;

import com.example.entities.Presentacion;

public interface PresentacionService {
    
    public List<Presentacion> findAll();

    public Presentacion findById(Long id);

    public void save(Presentacion presentacion);

}
