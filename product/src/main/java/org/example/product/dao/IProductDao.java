package org.example.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.product.entity.Product;
import org.example.product.proto.*;

import java.util.List;

@Mapper
public interface IProductDao {
    //    rpc ListProducts(ListProductsReq) returns (ListProductsResp) {}
//    rpc GetProduct(GetProductReq) returns (GetProductResp) {}
//    rpc SearchProducts(SearchProductsReq) returns (SearchProductsResp) {}
    List<Product> listProducts(@Param("pageOffset") int pageOffset, @Param("pageSize") long pageSize, @Param("categoryName") String categoryName);

    Product getProduct(int id);

    List<Product> searchProducts(String ruery);

}
