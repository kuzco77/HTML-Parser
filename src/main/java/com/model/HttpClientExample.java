//package com.model;
//
//import com.logger.Logger;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHeaders;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HttpClientExample {
//
//    // one instance, reuse
//    private final CloseableHttpClient httpClient = HttpClients.createDefault();
//
//    public static void main(String[] args) throws Exception {
//
//        HttpClientExample obj = new HttpClientExample();
//
//        try {
//            Logger.log("Testing 1 - Send Http GET request");
//            obj.sendGet();
//
//            Logger.log("Testing 2 - Send Http POST request");
//            obj.sendPost();
//        } finally {
//            obj.close();
//        }
//    }
//
//    public void close() throws IOException {
//        httpClient.close();
//    }
//
//    public void sendGet() throws Exception {
//
//        HttpGet request = new HttpGet("https://www.google.com/search?q=mkyong");
//
//        // add request headers
//        request.addHeader("custom-key", "mkyong");
//        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
//
//        try (CloseableHttpResponse response = httpClient.execute(request)) {
//
//            // Get HttpResponse Status
//            Logger.log(response.getStatusLine().toString());
//
//            HttpEntity entity = response.getEntity();
//            Header headers = entity.getContentType();
//            Logger.log(headers.toString());
//
//            if (entity != null) {
//                // return it as a String
//                String result = EntityUtils.toString(entity);
//                Logger.log(result);
//            }
//
//        }
//
//    }
//
//    private void sendPost() throws Exception {
//
//        HttpPost post = new HttpPost("https://httpbin.org/post");
//
//        // add request parameter, form parameters
//        List<NameValuePair> urlParameters = new ArrayList<>();
//        urlParameters.add(new BasicNameValuePair("username", "abc"));
//        urlParameters.add(new BasicNameValuePair("password", "123"));
//        urlParameters.add(new BasicNameValuePair("custom", "secret"));
//
//        post.setEntity(new UrlEncodedFormEntity(urlParameters));
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//             CloseableHttpResponse response = httpClient.execute(post)) {
//
//            Logger.log(EntityUtils.toString(response.getEntity()));
//        }
//
//    }
//
//}