package com.geekbrains.july.market.ws;

import com.geekbrains.july.market.entities.Product;
import com.geekbrains.july.market.repositories.ProductsRepository;
import localhost._8189.market.ws.product.GetProductRequest;
import localhost._8189.market.ws.product.GetProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8189/market/ws/xml";

    private ProductsRepository productsRepository;
//поленился делать сервис))
    @Autowired
    public ProductEndpoint(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetProductResponse getCountry(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        Product product = productsRepository.findByTitle(request.getTitle());
        response.setProduct(ProductXMLMapper.xmlBuild(product));
        return response;
    }
}
