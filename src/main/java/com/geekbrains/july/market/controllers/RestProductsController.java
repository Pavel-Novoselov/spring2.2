package com.geekbrains.july.market.controllers;

import com.geekbrains.july.market.entities.Product;
import com.geekbrains.july.market.entities.ResponseErrorRest;
import com.geekbrains.july.market.entities.dtos.ProductDto;
import com.geekbrains.july.market.exceptions.ProductNotFoundException;
import com.geekbrains.july.market.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
@Api("Set of endpoints for CRUD operations for Products")
public class RestProductsController {
    private ProductsService productsService;

    @Autowired
    public RestProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> list = productsService.findAll();
        if (list.isEmpty()) {
            throw new ProductNotFoundException("Products not found");
            //           return new ResponseEntity<>(new ResponseErrorRest("No one Product has found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping("/dto")
    @ApiOperation("Returns list of all products data transfer objects")
    public ResponseEntity<?> getAllDtoProduct() {
        List<ProductDto> list = productsService.findAllDto();
        if (list.isEmpty()) {
            throw new ProductNotFoundException("Products not found");
//            return new ResponseEntity<>(new ResponseErrorRest("No one Product has found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

    }


    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one product by id")
    public ResponseEntity<?> getOneProduct(
            @PathVariable @ApiParam("Id of the product to be requested. Cannot be empty") Long id
    ) {
        if (!productsService.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
//            return new ResponseEntity<>(new ResponseErrorRest("Product not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productsService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Removes all products")
    public ResponseEntity<?> deleteAllProducts() {
        List<Product> list = productsService.findAll();
        if (list.isEmpty()) {
            throw new ProductNotFoundException("Products not found");
//            return new ResponseEntity<>(new ResponseErrorRest("No one Product has found"), HttpStatus.NOT_FOUND);
        }
        productsService.deleteAll();
        return new ResponseEntity<>(new ResponseErrorRest("All products deleted"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one product by id")
    public ResponseEntity<?> deleteOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            return new ResponseEntity<>(new ResponseErrorRest("Product not found"), HttpStatus.NOT_FOUND);
        }
        productsService.deleteById(id);
        return new ResponseEntity<>(new ResponseErrorRest("Product id:" + id + " deleted"), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new product")
    public ResponseEntity<?> saveNewProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            product.setId(null);
        }
        if (product.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>(
                    new ResponseErrorRest("Product's price cannot be negative"),
                    HttpStatus.BAD_REQUEST
            );
        }
        Product productNew = productsService.saveOrUpdate(product);
        if (productNew == null) {
            return new ResponseEntity<>(
                    new ResponseErrorRest("Product was not saved"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(productNew, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing product")
    public ResponseEntity<?> modifyProduct(@RequestBody Product product) {
        if (product.getId() == null || !productsService.existsById(product.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + product.getId());
//            return new ResponseEntity<>(
//                    new ResponseErrorRest("Product not found, id: " + product.getId()),
//                    HttpStatus.NOT_FOUND
//            );
        }
        if (product.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>(
                    new ResponseErrorRest("Product's price cannot be negative"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(new ResponseErrorRest(exc.getMessage()), HttpStatus.NOT_FOUND);
    }
}