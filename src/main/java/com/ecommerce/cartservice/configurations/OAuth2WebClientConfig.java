package com.ecommerce.cartservice.configurations;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OAuth2WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository, authorizedClientRepository);
        oauth2.setDefaultClientRegistrationId("my-client");
        return WebClient.builder()
                .apply(oauth2.oauth2Configuration());
        // .filter(...) // your other filters if needed
    }

}
