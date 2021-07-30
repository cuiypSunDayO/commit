package cui.yongping.cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import cui.yongping.pojo实体类.CaseInfo;
import cui.yongping.pojo实体类.WriteBackData;
import cui.yongping.utils工具类.ExcelUtils;
import cui.yongping.utils工具类.UserData;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 崔崔
 * @date 2021/7/16-10:21
 * 小崔hello
 */
public class BaseCase {
   private static final Logger logger = Logger.getLogger(BaseCase.class);
    //成员  Logger logger =Logger.getLogger(RechargeCase.class);变量
    public int sheetIndex;

    /**
     * 加载testng中的参数，变成成员变量，jopo类的set方法类似
     * @param sheetIndex
     */
    @BeforeClass
    @Parameters({"sheetIndex"})
    public void beforeClass(int sheetIndex){
        System.out.println("sheetIndex"+sheetIndex);
        this.sheetIndex = sheetIndex;
    }

    /**
     * 批量回写响应结果到excel表格中
     * @throws Exception
     */
    @AfterSuite
    public void finsh() throws Exception {
        //  System.out.println("===================finish=============================");
        logger.info("===================finish=============================");
        ExcelUtils.batchWrite();
    }

    /**
     * 注释：批量存储响应结果
     * @param sheetIndex
     * @param rowNum
     * @param cellNum
     * @param responseBoby
     */
    public void responseWrite(int sheetIndex,int rowNum, int cellNum,String responseBoby) {
        WriteBackData wbd =new WriteBackData(sheetIndex,rowNum,cellNum,responseBoby);
        //批量存储到一个List集合
        ExcelUtils.wbdList.add(wbd);
    }

    /**
     * 从响应中利用jsonPath取值，获取鉴用户鉴权头token
     * @param responseBoby
     * @param
     * @param userDataKey
     */

    public void getParamsInUserData(String responseBoby,String jsonPathExpression,String userDataKey) {
        Object userDataValue= JSONPath.read(responseBoby,jsonPathExpression);
       // System.out.println("userDataKey:"+userDataKey);
        logger.info("userDataKey:"+userDataKey);

       // System.out.println("userDataValue:"+userDataValue);
        logger.info("userDataValue:"+userDataValue);
        if(userDataValue !=null){
            UserData.VARS.put(userDataKey,userDataValue);
        }
    }

    /**
     * 鉴权头
     * @return
     */
    public Map<String, String> getAuthorizationHeader( ) {
        Object token = UserData.VARS.get("${token}");
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization","Bearer "+ token);
        headers.putAll(UserData.DEFULT_HEADERS);
        return headers;
    }

    /**
     * 接口响应断言
     * @param expect 期望值
     * @param responseBoby 响应
     * @return
     */
    public boolean responseAssert(String expect, String responseBoby) {
        //期望结果
        //expectedResult转成Map
        Map<String,Object> map = JSONObject.parseObject(expect, Map.class);
        //遍历
        Set<String> keySet2 = map.keySet();
        boolean responseAsserFlag = true;
        for (String actualExpression : keySet2) {
            //获取期望值
            Object expectedValue = map.get(actualExpression);
            //通过表达式从响应体中获取实际值
            Object actualValue = JSONPath.read(responseBoby, actualExpression);
            //断言，只要失败一次整个断言就失败
            if(!expectedValue.equals(actualValue)){
                responseAsserFlag = false;
                //断言失败
                break;
            }
        }
       // System.out.println("响应断言结果"+responseAsserFlag);
        logger.info("响应断言结果:"+responseAsserFlag);
        return responseAsserFlag;

    }

    /**
     * 数据参数替换
     * @param caseInfo
     */
    @Step("参数化")
    public void paramsReplace(CaseInfo caseInfo) {
        Set<String> keySet = UserData.VARS.keySet();
        String params = caseInfo.getParams();
        String expected = caseInfo.getExpected();
        String sql = caseInfo.getSql();
        String url =caseInfo.getUrl();
        for (String placeHolder : keySet) {
            String value = UserData.VARS.get(placeHolder).toString();
            //设置参数列
            if (StringUtils.isNotBlank(params)) {
                params = params.replace(placeHolder, value);

            }
            //sql
            if (StringUtils.isNotBlank(sql)) {
                sql = sql.replace(placeHolder, value);

            }
            //期望值
            if (StringUtils.isNotBlank(expected)) {
                expected = expected.replace(placeHolder, value);

            }
            //url
            if (StringUtils.isNotBlank(url)) {
                url = url.replace(placeHolder, value);

            }
        }
            caseInfo.setParams(params);
            caseInfo.setSql(sql);
            caseInfo.setExpected(expected);
            caseInfo.setUrl(url);
            //System.out.println(caseInfo);
            logger.info(caseInfo);

    }

}
