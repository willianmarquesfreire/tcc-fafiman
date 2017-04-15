//package com.wmfsystem.eurekaserver;
//
//
//import com.wmfsystem.eurekaserver.Application;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by wmfsystem on 4/15/17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest("server.port=0")
//public class ApplicationTests {
//
//    @Value("${local.server.port}")
//    private int port = 0;
//
//    @Test
//    public void catalogLoads() {
//        @SuppressWarnings("rawtypes")
//        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/eureka/apps", Map.class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//    }
//
//    @Test
//    public void adminLoads() {
//        @SuppressWarnings("rawtypes")
//        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/env", Map.class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//    }
//
//}