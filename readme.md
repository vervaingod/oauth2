# 授权码模式的实现

因为demo较小 并没有将认证服务器和资源服务器分开，client 为客户端。

## 1、客户端将用户导向认证服务器

http://localhost:8080/oauth/authorize?client_id=clientApp&response_type=code&redirect_uri=http://localhost:8081/api/profile&scope=read_profile

## 2、用户同意授权

用户输入用户名，密码登陆后点击授权。本例中用户名为admin 密码为123，配置在了内存中

## 3、此时url跳转到第一步的redirect_uri 并携带授权码

##4、客户端携带授权码申请令牌

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

## 5、认证服务器发放令牌

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

## 6、用户携带token访问资源

此步用postman模拟，即可得到认证服务器中所得到的资源

![1547800722314](C:\Users\vervain\AppData\Local\Temp\1547800722314.png)