package org.example.product;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.product.proto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @DubboReference(version = "1.0")
    private org.example.product.service.ProductService productService;
    @Test
    public void test_rpc_listProducts() {
        ListProductsReq req = ListProductsReq.newBuilder()
                .setPage(1)
                .setPageSize(2)
                .setCategoryName("绿色")
                .build();
        // 调用 Dubbo 服务
        ListProductsResp resp = productService.listProducts(req);
        List<Product> productsList = resp.getProductsList();
        // 输出结果
        productsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getId())));
        productsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getName())));
//        log.info(JSON.toJSONString(resp));
    }

    @Test
    public void test_rpc_getProduct() {

        GetProductReq req = GetProductReq.newBuilder()
                .setId(1)
                .build();
        // 调用 Dubbo 服务
        GetProductResp resp = productService.getProduct(req);
        // 输出结果
        log.info(JSON.toJSONString(resp.getProduct().getName()));
//        log.info(JSON.toJSONString(resp));
    }
    @Test
    public void test_rpc_searchProdcuts() {

        SearchProductsReq req = SearchProductsReq.newBuilder()
                .setQuery("select * from product")
                .build();
        // 调用 Dubbo 服务
        SearchProductsResp resp = productService.searchProducts(req);
        // 输出结果
        List<Product> resultsList = resp.getResultsList();
        resultsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getId())));
        resultsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getName())));
//        log.info(JSON.toJSONString(resp));
    }
}
