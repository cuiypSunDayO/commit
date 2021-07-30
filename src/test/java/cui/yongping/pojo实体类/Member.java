package cui.yongping.pojo实体类;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Timer;

/**
 * @author 崔崔
 * @date 2021/7/21-11:43
 * 小崔hello
 */
public class Member {
    private int id;
    private String reg_name;
    private String pwd;
    private String mobile_phone;
    private int type;
    private BigDecimal leave_amount;
    private Timestamp reg_time;

    public Member(int id, String reg_name, String pwd, String mobile_phone, int type, BigDecimal leave_amount, Timestamp reg_time) {
        this.id = id;
        this.reg_name = reg_name;
        this.pwd = pwd;
        this.mobile_phone = mobile_phone;
        this.type = type;
        this.leave_amount = leave_amount;
        this.reg_time = reg_time;
    }

    public Member() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReg_name() {
        return reg_name;
    }

    public void setReg_name(String reg_name) {
        this.reg_name = reg_name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getLeave_amount() {
        return leave_amount;
    }

    public void setLeave_amount(BigDecimal leave_amount) {
        this.leave_amount = leave_amount;
    }

    public Timestamp getReg_time() {
        return reg_time;
    }

    public void setReg_time(Timestamp reg_time) {
        this.reg_time = reg_time;
    }
}
