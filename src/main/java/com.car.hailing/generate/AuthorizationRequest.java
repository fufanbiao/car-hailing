package com.car.hailing.generate;

/**
 * @Author: fanbiao.fu
 * @Description:
 * @Date: 2024/8/8
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthorizationRequest {

  public static void main(String[] args) throws Exception {
    String authorizationEndpoint = "https://6qp1ilcn.aliyunidaas.com/login/app/app_m2zt4v4u6s2ce6ayaqfgxtfzzm/oauth2/authorize?client_id=app_m2zt4v4u6s2ce6ayaqfgxtfzzm&response_type=code&redirect_uri=https://6qp1ilcn.aliyunidaas.com/login/common/oauth2/callback&response_type=code&scope=openid"; // 替换为实际的Authorization Endpoint
    URL obj = new URL(authorizationEndpoint);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // 设置请求头，例如：
    // con.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN");

    // 发送GET请求
    con.setRequestMethod("GET");

    int responseCode = con.getResponseCode();
    System.out.println("GET Response Code :: " + responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) { // 成功响应
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }

      in.close();

      // 打印结果
      System.out.println(response.toString());
    } else {
      System.out.println("GET request not worked");
    }
  }
}
