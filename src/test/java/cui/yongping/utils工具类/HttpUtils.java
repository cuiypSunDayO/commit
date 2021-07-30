package cui.yongping.utils工具类;

import com.alibaba.fastjson.JSONObject;
import cui.yongping.pojo实体类.CaseInfo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 崔崔
 * @date 2021/5/21-14:49
 * 小崔hello
 */
public class HttpUtils {
    private  static Logger logger =Logger.getLogger(HttpUtils.class);
//json Method

    /**
     *
     * @param caseInfo
     * @throws Exception
     */
    public static String call(CaseInfo caseInfo,Map<String,String> headers) {
        String responseBoby = "";
        try {
        String params = caseInfo.getParams();
        String url = caseInfo.getUrl();
        String method = caseInfo.getMethod();
        if("post".equals(method)){
            String contentType = caseInfo.getContentType();
            if("json".equalsIgnoreCase(contentType)){
                //{"a":1,"b":2,"c":3}
            }else if("form".equalsIgnoreCase(contentType)) {
                //  json-->map-->String
                //a=1&b=2&c=3
                //Map<String,String> parse = (Map<String, String>) JSONObject.parse(params);
                params=jsonStr2(params);
                // String params1 = formParams.substring(0, formParams.length() - 1);
                 System.out.println("formParams" + params);
                headers.put("Content-Type","application/x-www-form-urlencoded");
            }
            responseBoby=HttpUtils.post(url,params,headers);
        }else if("get".equalsIgnoreCase(method)){
            responseBoby=HttpUtils.get(url,headers);
        }else if("patch".equalsIgnoreCase(method)) {
          //  headers.put("Content-Type", "application/json");
            responseBoby=HttpUtils.patch(url,params,headers);
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return responseBoby;

    }

    public static String jsonStr2(String json) {
        Map<String,String> map = JSONObject.parseObject(json, Map.class);
        Set<String> keySet = map.keySet();
        String formParams ="";
        for (String key : keySet) {
            String value = map.get(key);
            formParams = key +"="+value+"&"+formParams;
        }
       // String params1 = formParams.substring(0, formParams.length() - 1);
       // System.out.println("formParams" + params1);
        return formParams.substring(0, formParams.length() - 1);
    }

    public static String get(String url,Map<String,String> headers) throws Exception {
        /**
         * 发送一个get请求
         * @param url携带参数的url
         *          例如： http://api.lemonban.com/futureloan/loans?pageIndex=1&pageSize=1
         *          例如：http://api.lemonban.com/futureloan/member/${member_id}/info
         * */
        //1.创建请求
        HttpGet get = new HttpGet(url);
        //2.设置请求头
        setHeaders(headers,get);
        //3.创建客户端
        HttpClient client = HttpClients.createDefault();
        //4.发起请求，获取响应
        HttpResponse response = client.execute(get);
        //5、格式化响应对象 response =状态码 + 响应头 + 响应体
        // 5、1响应状态码
        return printResponse(response);
    }
    /**
     * 发送一个post请求
     * @param url
     * @param params
     * @throws Exception
     */
    public static String post(String url, String params, Map<String,String> headers) throws Exception {
        HttpPost post =new HttpPost(url);
        //4.设置请求头
        setHeaders(headers, post);
        //post.setHeader(key,value);
       // post.setHeader("Content-Type","application/json");
        //5.设置参数
        StringEntity boby =new StringEntity(params,"utf-8");
        post.setEntity(boby);
        //6.请求必须由客户端（浏览器，jmeter，httpclient）必须创建一个对象
        //creatdefault静态方案
        HttpClient client = HttpClients.createDefault();
        //execute(httpUriRequest)多态的方法，接收httpUriRequest所有的子实现
        HttpResponse response = client.execute(post);
        return  printResponse(response);
    }

    public static String patch(String url,String params,Map<String,String> headers) throws Exception {
        HttpPatch patch =new HttpPatch(url);
        setHeaders(headers, patch);
        //5.设置
        StringEntity boby =new StringEntity(params,"utf-8");
        patch.setEntity(boby);
        //6.请求必须由客户端（浏览器，jmeter，httpclient）必须创建一个对象
        //creatdefault静态方案
        HttpClient client = HttpClients.createDefault();
        //execute(httpUriRequest)多态的方法，接收httpUriRequest所有的子实现
        HttpResponse response = client.execute(patch);
        return  printResponse(response);
    }


    public static void setHeaders(Map<String, String> headers, HttpRequest request) {
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            String value = headers.get(headerName);
            request.setHeader(headerName,value);
        }
    }

    /**
     *
     * @param response 响应对象
     * @return
     * @throws IOException
     */
    public static String printResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
      //  System.out.println(statusCode);
        logger.info(statusCode);

        //5、2响应头
        Header[] allHeaders = response.getAllHeaders();
        //System.out.println(Arrays.toString(allHeaders));
        logger.info(Arrays.toString(allHeaders));
        //5、3响应体
        HttpEntity entity = response.getEntity();
        String boby = EntityUtils.toString(entity);
        //System.out.println(boby);
        logger.info(boby);
       // System.out.println("======================================================");
        logger.info("======================================================");
        return boby;
    }


}

