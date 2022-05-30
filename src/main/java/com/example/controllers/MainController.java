package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.entities.Producto;
import com.example.services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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



    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable(name = "id") Long id){

        ResponseEntity<Producto> responseEntity = null;

        Producto producto = productoService.findById(id);

        if(producto!=null){
            responseEntity = new ResponseEntity<Producto>(producto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;

    }

    // POST      // le mandamos por JSON los datos a persistir y HAY QUE VALIDARLOS
    @PostMapping          // Map tipo Obj habilita a devolver errores, el objeto, etc
    public ResponseEntity<Map<String,Object>> save(
        @Valid                           // VALIDA el JSON que llega con las validaciones que definimos en la entidad
        @RequestBody Producto producto,  //@RBODY como es post, los datos vienen en el CUERPO de la peticion
        BindingResult result) {          // recopila
                                
        
        ResponseEntity<Map<String,Object>> responseEntity = null;
        
// same as Map<String,Object> name =
        var responseAsMap = new HashMap<String,Object>();

        if(result.hasErrors()){  // si result trae errores

            List<String> errores = new ArrayList<>(); 

            for (ObjectError error : result.getAllErrors()) {  //el metodo getallerrors trae cada error en tipo ObjectError
                errores.add(error.getDefaultMessage());  // lleno la lista con los msj que defini en entity
            }
            
            responseAsMap.put("errores", errores);
            
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            
            return responseEntity;

        } 

        try {
            
            Producto productoDB = productoService.save(producto);

            if(productoDB!=null){  // chequear si se guardo
                
                responseAsMap.put("mensaje", "El producto se ha guardado correctamente. Id Prod: "
                                    + productoDB.getId());
                responseAsMap.put("producto", productoDB);
                // tanto "msj" como "prod" son propiedades que va a mostrar el JSON

                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.CREATED); //201
 
            } else {

                responseAsMap.put("mensaje", "Error creando el producto");
                responseEntity = new ResponseEntity<>(responseAsMap,HttpStatus.BAD_GATEWAY);

            }

        } catch (DataAccessException e) {

            responseAsMap.put("mensaje", "Error creando el producto " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }


}

// POSTMAN
//ejemplo
//GET
// http://localhost:8080/productos?page=1&size=3
// http://localhost:8080/productos/1

//POST
// cambiar metodo a POST
// y los datos van por BODY - raw - JSON