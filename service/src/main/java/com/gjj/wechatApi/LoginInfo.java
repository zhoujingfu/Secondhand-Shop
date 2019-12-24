package com.gjj.wechatApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.enums.UserRole;
import com.gjj.enums.UserState;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.User;
import com.gjj.services.AuthenticationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gjj on 2018-03-25.
 */
@RestController
public class LoginInfo {
    private static String URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static String APPID = "wx319887c41183d389";
    private static String SECRET = "324c43a7832f23944b3f35a391f81834";

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @ResponseBody
    @GetMapping("/weChat/getUserInfo")
    public ResponseEntity<?> getUser(@RequestParam(required = false, value = "code") String code,
                                     @RequestParam(required = false, value = "nickName") String nickName,
                                     @RequestParam(required = false, value = "avatarUrl") String avatarUrl,
                                     @RequestParam(required = false, value = "gender") String gender) {
        String params = "appid="+ APPID +"&secret="+ SECRET +"&js_code="+ code +"&grant_type=authorization_code";
        String result = sendGet(URL,params);
        ObjectMapper objectMapper = new ObjectMapper();
        String session_key;
        String openid;
        try{
             JsonNode jsonNode = objectMapper.readTree(result);
             session_key = jsonNode.get("session_key").textValue().trim();
             openid = jsonNode.get("openid").textValue().trim();
        } catch (IOException e){
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        if (!authenticationUserService.isExistUser(openid)) {
            User user = new User();
            user.setOpenid(openid.trim());
            user.setNickName(nickName.trim());
            user.setAvatarUrl(avatarUrl.trim());
            user.setGender(gender.trim());
            user.setRole(UserRole.ORDINARY.getRole());
            user.setState(UserState.NORMAL.getState());
            authenticationUserService.addWechatUser(user);
        }
        Map<String,Object> map = new HashMap<>();
        Integer id = authenticationUserService.getUserIdByOpenid(openid);
        String username = authenticationUserService.getUsernameByOpenid(openid);
        Integer role = authenticationUserService.getUserRoleByOpenid(openid);
        Integer state = authenticationUserService.getUserStateByOpenid(openid);
        map.put("id",id);
        map.put("username",username);
        map.put("role",role);
        map.put("state",state);
        return ResponseEntity.ok(map);
    }



    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
