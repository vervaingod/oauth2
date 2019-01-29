# 客户端模式的实现

authorization-server 为认证服务器 端口为8080，client 为客户端 端口为8081，resource-server 为资源服务器 端口为8082。

授权的流程为 http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

## 1、访问客户端 http://localhost:8081/get-token.html

直接获取资源显示没有资源，点击获取token，此时触发：

#### （1）客户端向认证服务器进行身份认证，并要求一个访问令牌 

http://localhost:8080/oauth/token?grant_type=client_credentials&client_id=clientApp&client_secret=secret

#### （2）认证服务器发放令牌

已使用jwt生成了token

```
@Bean
public ApprovalStore approvalStore() {
    TokenApprovalStore store = new TokenApprovalStore();
    store.setTokenStore(tokenStore());
    return store;
}

@Bean
public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
}

@Bean
public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey("secret");
    return jwtAccessTokenConverter;
}


@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .accessTokenConverter(jwtAccessTokenConverter());
}
```

## 2、用户携带token访问资源

访问 http://localhost:8081/get-resource.html 输入第一步获得的token