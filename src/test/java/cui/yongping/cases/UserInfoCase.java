package cui.yongping.cases;

import com.alibaba.fastjson.JSONPath;
import cui.yongping.pojo实体类.CaseInfo;
import cui.yongping.utils工具类.Constants;
import cui.yongping.utils工具类.ExcelUtils;
import cui.yongping.utils工具类.HttpUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 崔崔
 * @date 2021/6/8-15:31
 * 用户信息接口
 */
public class UserInfoCase extends BaseCase{
   public static  Logger logger =Logger.getLogger(UserInfoCase.class);

    @Test(dataProvider="datas",description = "用户信息测试")
    @Step("Type {caseInfo.id} - {caseInfo.url}.")
    public void test(CaseInfo caseInfo) throws Exception {
        //1.参数化
        paramsReplace(caseInfo);

        //2.数据库前置断言（数据断言必须再接口执行前后都查询）
       // BigDecimal beforeSingleResult = (BigDecimal)SQLUtils.getSingleResult(caseInfo.getSql());
        //获取鉴权头+默认头
        Map<String, String> authorizationHeader = getAuthorizationHeader();
        //3、调用接口
        String responseBoby=HttpUtils.call(caseInfo, authorizationHeader);
       // 4、断言响应结果  断言，期望值和实际值匹配，匹配上了就是断言成功，相反断言失败。
        boolean responseAssertFlag = responseAssert(caseInfo.getExpected(), responseBoby);
        //5.响应回写
        responseWrite(sheetIndex, caseInfo.getId(), Constants.Response_CELL_NUM,responseBoby);
        //断言，预期结果与实际结果对比
        //6.数据库后置查询结果
       // BigDecimal afterSingleResult = (BigDecimal)SQLUtils.getSingleResult(caseInfo.getSql());
        //响应回写
        //7、数据库断言
       // boolean sqlAssert = sqlAssert(caseInfo, beforeSingleResult, afterSingleResult);
        //8、添加断言回写内容
        String assertResult = responseAssertFlag ?  Constants.ASSERT_SUCCESS: Constants.ASSERT_FAILED;
        responseWrite(sheetIndex, caseInfo.getId(),Constants.ASSERT_CELL_NUM,assertResult);

        //10、报表断言 断言失败应该在报表中体现
        Assert.assertEquals(assertResult,Constants.ASSERT_SUCCESS );



    }

    @DataProvider
    public Object[] datas() throws Exception {
        List<CaseInfo> list = ExcelUtils.read(sheetIndex, 1, CaseInfo.class);
        return list.toArray();
    }
}

