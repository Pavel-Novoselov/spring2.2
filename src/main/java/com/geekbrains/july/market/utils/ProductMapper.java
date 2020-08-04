package com.geekbrains.july.market.utils;

import com.geekbrains.july.market.entities.Product;
import com.geekbrains.july.market.entities.dtos.ProductDto;

import java.math.BigDecimal;

public class ProductMapper {

    public static ProductDto dtoBuild (Product product){
        ProductDto productDto = new ProductDto() {
            @Override
            public String getTitle() {
                return product.getTitle();
            }

            @Override
            public BigDecimal getPrice() {
                return product.getPrice();
            }

            @Override
            public String getCategory() {
                return product.getCategories().get(0).toString();
            }
        };
        return productDto;
    }
}
