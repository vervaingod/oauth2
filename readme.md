# Oauth 2.0 4种模式的实现

authorization-server 为认证服务器 端口为8080，client 为客户端 端口为8081，resource-server 为资源服务器 端口为8082。

授权的流程为 http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

密码模式的分支为 password，授权码模式的分支为 authorization-code，简化模式的分支为 implicit,客户端模式的分支为 client-credentials。   

以下以最复杂的授权码模式为例：

## 1、访问客户端 http://localhost:8081/get-token.html

直接获取资源显示没有资源，点击获取token，此时触发：

#### （1）客户端将用户导向认证服务器

http://localhost:8080/oauth/authorize?client_id=clientApp&response_type=code&redirect_uri=http://localhost:8081/api/profile&scope=read_profile

#### （2）用户同意授权

用户输入用户名，密码登陆后点击授权。本例中用户名为admin 密码为123，配置在了内存中

#### （3）此时url跳转到第（1）步的redirect_uri 并携带授权码

#### （4）客户端携带授权码申请令牌

```
String userMsg = "clientApp:secret";
String base64UserMsg = Base64.getEncoder().encodeToString(userMsg.getBytes());
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
headers.add("Authorization", "Basic " + base64UserMsg);

MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
params.add("client_id", "clientApp");
params.add("grant_type", "authorization_code");
params.add("code", code);
params.add("scope", "read_profile");

params.add("redirect_uri", "http://localhost:8081/api/profile");
HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
String token = response.getBody();
```

cline_id和秘钥在header的Authorization中

#### （5）认证服务器发放令牌

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