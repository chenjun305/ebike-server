//package com.ecgobike.controller;
//
//import com.ecgobike.EbikeServerShopApp;
//import com.ecgobike.pojo.response.AppResponse;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ChenJun on 2018/4/25.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EbikeServerShopApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//public class ShopEBikeControllerTest {
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void infoTest() {
//        Map<String, String> params = new HashMap<>();
//        params.put("ebikeSn", "201804901");
//        Map<String, String> secureParams = ControllerTestHelper.secureParams(params);
//        AppResponse response = restTemplate.postForObject("/ebike/info",
//                secureParams,
//                AppResponse.class);
//        String response = restTemplate.postForObject("/ebike/info",
//                secureParams,
//                String.class);
////        String response = restTemplate.getForObject("/ebike/info", String.class, secureParams);
////        assertThat(response.getCode()).isEqualTo(0);
//        System.out.println(response);
//    }
//}
