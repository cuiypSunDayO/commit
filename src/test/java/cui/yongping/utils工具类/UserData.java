package cui.yongping.utils工具类;

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 崔崔
 * @date 2021/7/14-15:17
 * 小崔hello
 */
public class UserData {
   //
   public static  Map<String,Object> VARS = new HashMap<>();
   public static Map<String,String> DEFULT_HEADERS = new HashMap<>();

   static{
      DEFULT_HEADERS.put("X-Lemonban-Media-Type","lemonban.v2");
      DEFULT_HEADERS.put("Content-Type","application/json");

      VARS.put("${register_mb}", ChineseMobileNumberGenerator.getInstance().generate());
      //VARS.put("${login}")数据库查询
      VARS.put("${register_pwd}", 12345678);
      VARS.put("${amount}",5000);
      //
//      Object memberId = UserData.Vars.get("${memberId}");
//     Vars.put("${member_id}",memberId);
     // Authorization(responseBoby,"$.data.id","${member_id}");
      //Vars.put("${member_id}","$.data.id");
   }
}
