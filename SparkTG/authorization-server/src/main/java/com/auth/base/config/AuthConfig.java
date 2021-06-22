package com.auth.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${oauth.accesstoken.validity}")
	private int accessTokenValidity;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsService inMemoryUserDetailsManager;

    @Bean
    public DefaultAccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
    
    @Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()").allowFormAuthenticationForClients();
	}

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .userDetailsService(inMemoryUserDetailsManager)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }
    
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
        .inMemory()
        .withClient(clientId)
        .authorizedGrantTypes("refresh_token", "authorization_code", "password", "client_credentials")
        .authorities("ADMIN","USER")
        .scopes("READ", "WRITE")
        .secret(passwordEncoder.encode(clientSecret))
        .accessTokenValiditySeconds(accessTokenValidity);
	}

	
}
