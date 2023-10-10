package com.lcwd.user.service.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private OAuth2AuthorizedClientManager manager;

    private Logger logger= LoggerFactory.getLogger(RestTemplateInterceptor.class);

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

//    overriding the `intercept` method in a `ClientHttpRequestInterceptor` implementation.
//    In this method, you're obtaining an access token using the `manager` and then adding it to the request headers for authorization.
//    By logging the token, you can see the value for verification purposes. It's great to have this level of visibility!
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();

        logger.info("Rest Template interceptor: Token :  {} ",token);

        request.getHeaders().add("Authorization","Bearer "+token);
        return execution.execute(request,body);
    }
}
