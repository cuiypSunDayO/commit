package cui.yongping.pojo实体类;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author 崔崔
 * @date 2021/5/24-11:26
 * 小崔hello
 * excel 表格映射类
 */
public class CaseInfo {
    //映射类要求，必须有get，set方法，必须有空参构造，（与json的规则类似）
    //CaseId(用例编号)	Name(接口名)	Type(接口提交类型)	Url(接口地址)	Desc(用例描述)	Params(参数)	Content-Type

    //用例编号	用例描述	接口名称	请求方式	url	参数	参数类型	期望结果		sql
    @Excel(name = "用例编号")
    private int id;
    @Excel(name = "接口名称")
    private String name;
    @Excel(name = "请求方式")
    private String method;
    @Excel(name = "url")
    private String url;
    //不映射 Desc用例描述
    @Excel(name = "参数")
    private String params;
    @Excel(name = "参数类型")
    private String contentType;
    @Excel(name = "期望结果")
    private String expected;
    @Excel(name = "sql")
    private String sql;


    public CaseInfo() {
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expected='" + expected + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
