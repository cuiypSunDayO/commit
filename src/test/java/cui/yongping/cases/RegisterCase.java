package cui.yongping.cases;

import cui.yongping.pojo实体类.CaseInfo;
import cui.yongping.utils工具类.*;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.*;

/**
 * @author 崔崔
 * @date 2021/5/21-15:39
 * 小崔hello
 */
public class RegisterCase extends BaseCase{
    //成员变量
   Logger logger =Logger.getLogger(RegisterCase.class);
    @Test(dataProvider="datas")
    public void test(CaseInfo caseInfo) throws Exception {
        //参数化
        paramsReplace(caseInfo);
        Long beforeSQLResult = (Long) SQLUtils.getSingleResult(caseInfo.getSql());
       // HttpUtils.call(caseInfo,UserData.DEFULT_HEADERS);
        String responseBody = HttpUtils.call(caseInfo, UserData.DEFULT_HEADERS);
        //        4、断言响应结果
        boolean responseAssertFlag = responseAssert(caseInfo.getExpected(), responseBody);
       // logger.info("注册成功");
        // addWriteBackData(sheetIndex,caseInfo.getId(), Constants.RESPONSE_CELL_NUM, responseBody);
        Long afterSQLResult = (Long)SQLUtils.getSingleResult(caseInfo.getSql());
//        7、数据库断言
        //如果sql为空不需要数据库断言
        boolean sqlAssertFlag = sqlAssert(caseInfo.getSql(), beforeSQLResult, afterSQLResult);
        responseWrite(sheetIndex, caseInfo.getId(), Constants.Response_CELL_NUM,responseBody);
        String assertResult = responseAssertFlag ?  Constants.ASSERT_SUCCESS: Constants.ASSERT_FAILED;
        responseWrite(sheetIndex, caseInfo.getId(),Constants.ASSERT_CELL_NUM,assertResult);
        Assert.assertEquals(assertResult,Constants.ASSERT_SUCCESS );

        //        5、添加接口响应回写内容
    }
    public boolean sqlAssert(String sql, Long beforeSQLResult, Long afterSQLResult) {
        boolean flag = false;
        if(StringUtils.isNotBlank(sql)) {
            System.out.println("beforeSQLResult:" + beforeSQLResult);
            System.out.println("afterSQLResult:" + afterSQLResult);
            if (beforeSQLResult == 0 && afterSQLResult == 1) {
               System.out.println("数据库断言成功");
                //logger.info("数据库断言成功");
                flag = true;
            } else {
               System.out.println("数据库断言失败");
               // logger.info("数据库断言失败");
            }
        }
        return flag;
    }
    @DataProvider
    public Object[] datas() throws Exception {
        List<CaseInfo> list = ExcelUtils.read(sheetIndex, 1, CaseInfo.class);
        //集合转数组
        Object[] datas= list.toArray();
        return datas;
    }
}
