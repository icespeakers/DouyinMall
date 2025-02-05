package org.example.cart.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cart.entity.Product;

import java.util.List;

@Mapper
public interface ICartDao {
    void addProductToCart(@Param("userId") int userId, @Param("productId")int productId, @Param("quantity")int quantity);
    Integer checkProduct(@Param("userId") int userId, @Param("productId")int productId);
    void updateProductToCart(@Param("userId") int userId, @Param("productId")int productId, @Param("quantity")int quantity);
    List<Product> getCart(int userId);
    void emptyCart(int userId);
}
