///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rest.test;
//
//import java.io.IOException;
//import java.util.Date;
//import javax.ws.rs.client.Client;
//import model.User123;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import service.KwetterService;
//
///**
// *
// * @author Vito Corleone
// */
//public class RestAPITest {
//
//    KwetterService kwetterService;
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        kwetterService = new KwetterService(true);
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void testAddAndGetUser() throws IOException {
//
//        // verify that the user does not exist yet
//        HttpUriRequest request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/vito@kwetter.com");
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(204, httpResponse.getStatusLine().getStatusCode());
//
//        // add user with succes
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/createUser/Vito/vito@kwetter.com/P@ssW0rd");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(200,httpResponse.getStatusLine().getStatusCode());
//
//        // verify user is indeed added
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/vito@kwetter.com");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        
//        assertEquals(200,httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testAddAndGetFollowing() throws IOException {
//        // verify that vito-user does not exist yet
//        HttpUriRequest request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/vito@kwetter.com");
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(204, httpResponse.getStatusLine().getStatusCode());
//
//        // verify that following-user does not exist yet
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/following@kwetter.com");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(204, httpResponse.getStatusLine().getStatusCode());
//
//        // add vito-user with succes
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/createUser/Vito/vito@kwetter.com/P@ssW0rd");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//
//        // add following-user with succes
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/createUser/Following/following@kwetter.com/password");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//
//        // verify vito-user is indeed added
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/vito@kwetter.com");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//
//        // verify following-user is indeed added
//        request = new HttpGet("http://localhost:8080/Kwetter/webresources/rest/user/getUser/following@kwetter.com");
//        httpResponse = HttpClientBuilder.create().build().execute(request);
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//
//    }
//
//    @Test
//    public void testAddAndGetFollowers() throws IOException {
//
//    }
//}
