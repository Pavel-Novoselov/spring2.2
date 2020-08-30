package com.geekbrains.july.market.ws;

import com.geekbrains.july.market.entities.Product;
import com.geekbrains.july.market.repositories.ProductsRepository;

import com.geekbrains.july.market.ws.xml.GetProductRequest;
import com.geekbrains.july.market.ws.xml.GetProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8189/market/ws/";

    private ProductsRepository productsRepository;
//поленился делать сервис))
    @Autowired
    public ProductEndpoint(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        Product product = productsRepository.findByTitle(request.getTitle());
        response.setProduct(ProductXMLMapper.xmlBuild(product));
        return response;
    }
}
