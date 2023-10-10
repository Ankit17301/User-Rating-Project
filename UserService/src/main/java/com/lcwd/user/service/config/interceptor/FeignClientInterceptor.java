package com.lcwd.user.service.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    // OAuth2AuthorizedClientManager` to obtain an access token for a specific client registration.
    @Autowired
    private OAuth2AuthorizedClientManager manager;

    //  the access token as a header in the `RequestTemplate` object using the `apply` method.
    // This allows you to include the access token in the request headers for authenticated requests.
    // It's a great way to handle authorization when working with OAuth2.
    @Override
    public void apply(RequestTemplate template) {

        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();
        template.header("Authorization", "Bearer " + token);


    }
}
