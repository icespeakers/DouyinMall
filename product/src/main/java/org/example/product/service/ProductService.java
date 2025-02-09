package org.example.product.service;

import org.example.product.proto.*;

public interface ProductService {
    ListProductsResp listProducts(ListProductsReq req);

    GetProductResp getProduct(GetProductReq req);

    SearchProductsResp searchProducts(SearchProductsReq req);
}
