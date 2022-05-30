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

@RestController   // responsable de armar el JSON
@RequestMapping("/productos") /// LA URL REFIERE A LOS RECURSOS, en este caso se asume que manda productos
public class MainController {

    @Autowired
    private ProductoService productoService;

    // no devuelve el modelo
    // devuelve JSON para ser consumido desde el front

    // get mapping no lleva /nombre
    // en REST se define el metodo segun el verbo HTTP, GET POST DELETE
    @GetMapping                                        
    public ResponseEntity<List<Producto>> getProductos  // parametros OPCIONALES paginacion y cantidad
                            (@RequestParam(name = "page", required = false) Integer page,
                            @RequestParam(name = "size", required = false) Integer size) {
    // ResponsiveEntity permite devolver un obj con mas informacion, ej el Status
    // para que sea rest tiene que devolver esa informacion

        // Para que devuelva el listado de productos ordenado por nombre, tanto si es
        // con paginacion
        // como si no lo es
        Sort sortByName = Sort.by("nombre");  // crear variable que tenga el criterio de
                                                            // ORDENAMIENTO que quiero
                                                            // se podria pasar tb por param
        ResponseEntity<List<Producto>> responseEntity = null;

        List<Producto> productos = null;  // se podria hacer con VAR tb

        if (page != null && size != null) { // CON paginacion
            

            Pageable pageable = PageRequest.of(page, size, sortByName); 
                                // recibe param pag, cantidad y crit de ordenacion
            productos = productoService.findAll(pageable).getContent();
                                                        // extraer productos 
        } else {
            // Sin paginacion y devolvemos la lista completa de los productos
            productos = productoService.findAll(sortByName);

        }

        if (productos.size() > 0) {
            // si devuelve la lista == status OK
            responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
        } else {
            // si el listado esta vacio == otro status
            responseEntity = new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT); //204
        }

        return responseEntity;
    }

}

// POSTMAN
//ejemplo
// http://localhost:8080/productos?page=1&size=3