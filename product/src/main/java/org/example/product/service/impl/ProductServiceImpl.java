package org.example.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.product.dao.IProductDao;
import org.example.product.entity.Product;
import org.example.product.proto.*;
import org.example.product.service.ProductService;
import org.example.userservice.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/product/")
@DubboService(version = "1.0")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductDao productDao;
    @RequestMapping(value = "listProducts", method = RequestMethod.POST)
    @Override
    public ListProductsResp listProducts(ListProductsReq req) {

        List<Product> products = productDao.listProducts((int) ((req.getPage() - 1) * req.getPageSize()), req.getPageSize(), req.getCategoryName());
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
        org.example.product.proto.Product product = exchange(productDao.getProduct(ProductId));

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
