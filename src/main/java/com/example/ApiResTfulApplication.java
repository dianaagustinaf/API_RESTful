package com.example;

import com.example.entities.Presentacion;
import com.example.entities.Producto;
import com.example.services.PresentacionService;
import com.example.services.ProductoService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiResTfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiResTfulApplication.class, args);
	}


	// Agregar registros de ejemplo a la BD
	@Bean  // (hace los NEW, inversion de control)
	public CommandLineRunner demoData(ProductoService productoService,  // recibe el servicio de prod y de presenta
									PresentacionService presentacionService){
		
		return args -> {

			presentacionService.save(Presentacion.builder().nombre("unidad").build());
			presentacionService.save(Presentacion.builder().nombre("docena").build());

			productoService.save(Producto.builder().nombre("rezma de papel")
													.precio(3.75)
													.stock(10L)
													.presentacion(presentacionService.findById(1L))
													//pq necesito el obj Pres, no solo el id
													.build());
			
			productoService.save(Producto.builder().nombre("cartas")
													.precio(1.00)
													.stock(10L)
													.presentacion(presentacionService.findById(2L))
													.build());

			productoService.save(Producto.builder().nombre("guitarra de juguete")
													.precio(4.5)
													.stock(5L)
													.presentacion(presentacionService.findById(1L))
													.build());
			
			productoService.save(Producto.builder().nombre("teclado para computadora")
													.precio(15.0)
													.stock(5L)
													.presentacion(presentacionService.findById(1L))
													.build());
			
			productoService.save(Producto.builder().nombre("teclado para laptop")
													.precio(40.9)
													.stock(5L)
													.presentacion(presentacionService.findById(1L))
													.build());
			
			productoService.save(Producto.builder().nombre("bocinas bluetooth")
													.precio(15.8).stock(5L).presentacion(presentacionService.findById(1L)).build());
							
			productoService.save(Producto.builder().nombre("lapices 2b")
													.precio(1.5).stock(4L).presentacion(presentacionService.findById(2L)).build());
											
			productoService.save(Producto.builder().nombre("plumas color azul")
													.precio(2.5).stock(10L).presentacion(presentacionService.findById(2L)).build());
											
			productoService.save(Producto.builder().nombre("monitor dell 15p")
													.precio(40.0).stock(5L).presentacion(presentacionService.findById(1L)).build());
											
			productoService.save(Producto.builder().nombre("cargador samsung")
													.precio(10.0).stock(10L).presentacion(presentacionService.findById(1L)).build());
											
			productoService.save(Producto.builder().nombre("mouse básico")
													.precio(5.0).stock(5L).presentacion(presentacionService.findById(1L)).build());
								
			
			};
																	
		}
	}

