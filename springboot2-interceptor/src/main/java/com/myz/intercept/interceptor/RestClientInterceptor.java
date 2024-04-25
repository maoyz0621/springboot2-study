/**
 * Copyright 2024 Inc.
 **/
package com.myz.intercept.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.UUID;

/**
 * @author maoyz0621 on 2024/4/2
 * @version v1.0
 */
public class RestClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("traceId", UUID.randomUUID().toString());
        return execution.execute(request, body);
    }
}