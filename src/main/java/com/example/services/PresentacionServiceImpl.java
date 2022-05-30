package com.example.services;

import java.util.List;

import com.example.dao.PresentacionDao;
import com.example.entities.Presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentacionServiceImpl implements PresentacionService{

    @Autowired
    private PresentacionDao presentacionDao;

    @Override
    public List<Presentacion> findAll() {
        return presentacionDao.findAll();
    }

    @Override
    public Presentacion findById(Long id) {
        return presentacionDao.findById(id).get();
    }

    @Override
    public void save(Presentacion presentacion) {
        presentacionDao.save(presentacion);
    }
    
}
