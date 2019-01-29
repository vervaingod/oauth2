# Oauth 2.0 简化模式的实现

authorization-server 为认证服务器 端口为8080，client 为客户端 端口为8081，resource-server 为资源服务器 端口为8082。

授权的流程为 http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

## 1、访问客户端 http://localhost:8081/get-token.html

直接获取资源显示没有资源，点击获取token，此时触发：

#### （1）客户端将用户导向认证服务器

http://localhost:8080/oauth/authorize?response_type=token&client_id=clientApp&redirect_uri=http://localhost:8081/show-token.html

#### （2）用户同意授权

用户输入用户名，密码登陆后点击授权。本例中用户名为admin 密码为123，配置在了内存中

#### （3）此时url跳转到第（1）步的redirect_uri 并在url中携带了token

#### （4）客户端js解析获得token

```
    var show = document.getElementById('show');
    var result = window.location.hash.split("&");
    strs = result[0].split("=");
    token = strs[1];
    console.log(token);
    document.write(token);
```

## 2、用户携带token访问资源

访问 http://localhost:8081/get-resource.html 输入第一步获得的token