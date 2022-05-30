package com.example.controllers;

import java.util.List;

import com.example.entities.Producto;
import com.example.services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos") /// LA URL REFIERE A LOS RECURSOS, en este caso se asume que manda productos
public class MainController {

    @Autowired
    private ProductoService productoService;

    // no devuelve el modelo
    // devuelve JSON para ser consumido desde el front

    // get mapping no lleva /nombre
    // en REST se define el metodo segun el verbo HTTP, GET POST DELETE
    @GetMapping
    public ResponseEntity<List<Producto>> findAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size) {

        // Para que devuelva el listado de productos ordenado por nombre, tanto si es
        // con paginacion
        // como si no lo es
        Sort sortByName = Sort.by("nombre");

        ResponseEntity<List<Producto>> responseEntity = null;

        List<Producto> productos = null;

        if (page != null && size != null) {
            // Con paginacion
            Pageable pageable = PageRequest.of(page, size, sortByName);
            productos = productoService.findAll(pageable).getContent();
        } else {
            // Sin paginacion y devolvemos la lista completa de los productos
            productos = productoService.findAll(sortByName);

        }

        if (productos.size() > 0) {
            responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

}
