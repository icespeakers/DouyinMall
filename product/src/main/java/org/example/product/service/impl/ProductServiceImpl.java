package org.example.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.product.dao.IProductDao;
import org.example.product.entity.Product;
import org.example.product.proto.*;
import org.example.product.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/product/")
@DubboService(version = "1.0")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductDao productDao;
    @Autowired
    private RedisTemplate redisTemplate;

    public static final String PRODUCT_KEY = "product:";

    @RequestMapping(value = "listProducts", method = RequestMethod.POST)
    @Override
    public ListProductsResp listProducts(ListProductsReq req) {
        String RedisKey = "product:" + req.getPage()+req.getPageSize()+req.getCategoryName();
        //从缓存中获取
        Object redisProducts = redisTemplate.opsForValue().get(RedisKey);
        List<Product> products=new ArrayList<>();
        if(redisProducts==null){
            log.info("product not found in redis");
            if(StringUtils.isEmpty(req.getCategoryName())){
                products = productDao.listProducts((int) ((req.getPage() - 1) * req.getPageSize()), req.getPageSize(), req.getCategoryName());
                redisTemplate.opsForValue().set(RedisKey,products,60, TimeUnit.SECONDS);
            }
        }else{
            log.info("product found in redis");
            products = (List<Product>) redisProducts;
        }


        ListProductsResp.Builder builder = ListProductsResp.newBuilder();
        for(int i=0;i<products.size();i++){
            org.example.product.proto.Product product = exchange(products.get(i));
            builder.addProducts(product);
        }
        return builder.build();
    }
    @RequestMapping(value = "getProduct", method = RequestMethod.POST)
    @Override
    public GetProductResp getProduct(GetProductReq req) {
        int ProductId = req.getId();
        if(ProductId==0){
            throw new IllegalArgumentException("product not found");
        }
        //从缓存中获取
        org.example.product.proto.Product product;
        Object redisProduct = redisTemplate.opsForValue().get(PRODUCT_KEY + ProductId);

        if(redisProduct==null){
            log.info("product not found in redis");
            Product pd = productDao.getProduct(ProductId);
            product = exchange(pd);
            redisTemplate.opsForValue().set(PRODUCT_KEY + ProductId,product);
        }else {
            log.info("product found in redis");
            product=(org.example.product.proto.Product) redisProduct;
        }




        return GetProductResp.newBuilder().setProduct(product).build();
    }
    @RequestMapping(value = "searchProducts", method = RequestMethod.POST)
    @Override
    public SearchProductsResp searchProducts(SearchProductsReq req) {
        String query = req.getQuery();
        if(StringUtils.isBlank(query)||query.length()==0){
            throw new IllegalArgumentException("query is null");
        }
        List<Product> products = productDao.searchProducts(query);
        SearchProductsResp.Builder builder = SearchProductsResp.newBuilder();
        for(int i=0;i<products.size();i++){
            org.example.product.proto.Product product = exchange(products.get(i));
            builder.addResults(product);
        }
        return builder.build();
    }


    public org.example.product.proto.Product exchange(org.example.product.entity.Product product){
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
