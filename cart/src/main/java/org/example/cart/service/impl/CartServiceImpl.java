package org.example.cart.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.auth.v1alpha1.Ca;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.cart.dao.ICartDao;
import org.example.cart.entity.Product;
import org.example.cart.proto.*;
import org.example.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/cart/")
@DubboService(version = "1.0")
public class CartServiceImpl implements CartService {
    @Autowired
    private ICartDao cartDao;
    @RequestMapping(value = "addItem", method = RequestMethod.POST)
    @Override
    public AddItemResp addItem(AddItemReq addItemReq) {
        int userId= addItemReq.getUserId();
        CartItem item = addItemReq.getItem();
        int productId = item.getProductId();
        int quantity = item.getQuantity();
        Integer checkQuantity = cartDao.checkProduct(userId,productId);
        if(checkQuantity==null||checkQuantity<=0){
            log.info("add product to cart");
            cartDao.addProductToCart(userId,productId,quantity);
            return AddItemResp.newBuilder().build();
        }
        log.info("update product to cart");
        cartDao.updateProductToCart(userId,productId,quantity+checkQuantity);
        return AddItemResp.newBuilder().build();

    }
    @RequestMapping(value = "getcart", method = RequestMethod.POST)
    @Override
    public GetCartResp getCart(GetCartReq getCartReq) {
        int userId = getCartReq.getUserId();
        List<Product> products = cartDao.getCart(userId);
        GetCartResp.Builder builder = GetCartResp.newBuilder();
        Cart.Builder cartBuilder=Cart.newBuilder();
        for (Product product : products) {
            cartBuilder.addItems(exchange(product));
        }
        return builder.setCart(cartBuilder.build()).build();
    }

    @Override
    public EmptyCartResp emptyCart(EmptyCartReq emptyCartReq) {
        cartDao.emptyCart(emptyCartReq.getUserId());
        return EmptyCartResp.newBuilder().build();

    }


    public CartItem exchange(Product cart){
        CartItem build = CartItem.newBuilder().setProductId(cart.getProductId()).setQuantity(cart.getQuantity()).build();
        return build;
    }
}
