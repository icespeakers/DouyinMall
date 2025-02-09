package org.example.product.util;

import org.example.product.proto.Product;

public class exchangeUtil {

    public Product exchange(org.example.product.entity.Product product) {
        org.example.product.proto.Product ans = org.example.product.proto.Product.newBuilder()
                .setId(product.getId())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPicture(product.getPicture())
                .setPrice(product.getPrice())
                .build();
        return ans;
    }
}
