package com.geekbrains.july.market.ws;

import com.geekbrains.july.market.entities.Product;
import com.geekbrains.july.market.ws.xml.ProductXML;


public class ProductXMLMapper {
    public static ProductXML xmlBuild (Product product){
        ProductXML productXML = new ProductXML() {
            @Override
            public String getTitle() {
                return product.getTitle();
            }

            @Override
            public double getPrice() {
                return product.getPrice().doubleValue();
            }

            @Override
            public String getCategoriesXML() {
                return product.getCategories().get(0).toString();
            }
        };
        return productXML;
    }
}
