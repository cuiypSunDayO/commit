package cui.yongping.cases;

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import cui.yongping.pojo实体类.CaseInfo;
import cui.yongping.pojo实体类.WriteBackData;
import cui.yongping.utils工具类.*;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.testng.Assert;
import org.testng.annotations.*;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 崔崔
 * @date 2021/6/8-15:31
 * 小崔hello
 */
public class LoginCase extends BaseCase{

    @Test(dataProvider="datas")
    public void test(CaseInfo caseInfo) throws Exception {
        //1参数化，真实手机号和密码
        //sql:select count(*) from member a where a.mobile_phone = '${register_mb}';
        //参数：{"mobile_phone":"${register_mb}","pwd":"${register_pwd}"}
      /*  Vars.put("${register_mb}", ChineseMobileNumberGenerator.getInstance().generate());
        Vars.put("${register_pwd}", 12345678);
        Vars.put("${amount}",5000);*/
        paramsReplace(caseInfo);

        //2.数据库前置断言
        //int beforeSQLResult=(int) SQLUtils.getSingleResult(caseInfo.getSql());
        //调用接口

       String responseBoby= HttpUtils.call(caseInfo,UserData.DEFULT_HEADERS);
       //获取鉴用户鉴权头toke
        getParamsInUserData(responseBoby,"$.data.token_info.token","${token}");
        getParamsInUserData(responseBoby,"$.data.id","${member_id}");
        //断言，预期结果与实际结果对比
        boolean responseAssertFlag = responseAssert(caseInfo.getExpected(), responseBoby);
        //数据库后置断言
       // int afterSQLResult =(int) SQLUtils.getSingleResult(caseInfo.getSql());

       //String assertResult = responseAssert? "PASSED":"FAILED";
        String assertResult = responseAssertFlag ?  Constants.ASSERT_SUCCESS: Constants.ASSERT_FAILED;
        //响应回写
        responseWrite(sheetIndex, caseInfo.getId(), Constants.Response_CELL_NUM,responseBoby);
        Assert.assertEquals(assertResult,Constants.ASSERT_SUCCESS );



    }



    @DataProvider
    public Object[] datas() throws Exception {
        List<CaseInfo> list = ExcelUtils.read(sheetIndex, 1, CaseInfo.class);

        return list.toArray();
    }
}
