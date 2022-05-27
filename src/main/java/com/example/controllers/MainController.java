package com.example.controllers;

import java.util.List;

import com.example.entities.Producto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/productos")  /// LA URL REFIERE A LOS RECURSOS, en este caso se asume que manda productos
public class MainController {

    // no devuelve el modelo
    // devuelve JSON para ser consumido desde el front
    
    //get mapping no lleva /nombre
    //en REST se define el metodo segun el verbo HTTP, GET POST DELETE
    @GetMapping
    public List<Producto> getProductos(){

        return List;
    } 

}
