package com.alpha.commons.core.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by HP on 2018/10/12.
 *
 *
 */
public class DruidManager {

    private static String driver = "oracle.jdbc.driver.OracleDriver"; //驱动
    private static String url = "jdbc:oracle:thin:@//192.168.101.203:1521/gmrmyy"; //连接字符串
    private static String username = "guest_zhmz"; //用户名
    private static String password = "guest_zhmz"; //密码

    //现场
    public static String alive="select * from V_MZ_GHYY where type in(1,3) and to_char(visitTime,'yyyy-mm-dd')=? and outPatientNo=?";
    //所有
    public static String all="select * from V_MZ_GHYY where  createTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')";

    private DruidManager() {

    }

    private static DruidManager single = getInstance();
    private DruidDataSource dataSource;

    public synchronized static DruidManager getInstance() {
        if (single == null) {
            single = new DruidManager();
            single.initPool();
        }
        return single;
    }

    private void initPool() {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        dataSource.setPoolPreparedStatements(false);
    }

    public  Connection getConnection() {
        Connection connection = null;
        try {
            synchronized (dataSource) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {

        }
        return connection;
    }

    public static List<Map<String, Object>> yygh(String first, String second,String sql) {

        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Connection con=null;
        try {
             con=single.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,first);
            ps.setString(2,second);
            ResultSet resultSet=ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    data.put(resultSetMetaData.getColumnLabel(i),resultSet.getObject(i));
                }
                datas.add(data);
            }
            resultSet.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datas;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> datas = yygh("2018-10-18 10:16:30","2018-10-18 11:19:30",all);
        List<HisRegisterYygh> list=new ArrayList<>();
        datas.stream().forEach(e -> {
//            HisRegisterYygh registerDTO=new HisRegisterYygh();
//            registerDTO.setBirthday(e.get("BIRTHDAY")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)e.get("BIRTHDAY")));
//            registerDTO.setDeptName(e.get("DEPTNAME")==null?null:(String)e.get("DEPTNAME"));
//            registerDTO.setDoctorName(e.get("DOCTORNAME")==null?null:(String)e.get("DOCTORNAME"));
//            registerDTO.setOutPatientNo(e.get("OUTPATIENTNO")==null?null:(String)e.get("OUTPATIENTNO"));
//            registerDTO.setPatientCardNo(e.get("PATIENTCARDNO")==null?null:(String)e.get("PATIENTCARDNO"));
//            registerDTO.setPatientName(e.get("PATIENTNAME")==null?null:(String)e.get("PATIENTNAME"));
//            registerDTO.setPno(e.get("PNO")==null?null:(String.valueOf(e.get("PNO"))));
//            registerDTO.setSex(e.get("SEX")==null?null:(String)e.get("SEX"));
//            registerDTO.setVisitTime(e.get("VISITTIME")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)e.get("VISITTIME")));
//            registerDTO.setCreateTime(e.get("CREATETIME")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)e.get("CREATETIME")));
//            registerDTO.setPhoneNew(e.get("PHONENEW")==null?null:(String)e.get("PHONENEW"));
//            registerDTO.setPhone(e.get("PHONE")==null?null:(String)e.get("PHONE"));
//            registerDTO.setIntervalTime(e.get("INTERVALTIME")==null?null:(String)e.get("INTERVALTIME"));
//            registerDTO.setType(e.get("TYPE")==null?null:(Integer.parseInt((String) e.get("TYPE"))));
//            registerDTO.setYno(e.get("PNONEW")==null?null:(String.valueOf(e.get("PNONEW"))));
//            registerDTO.setStatus(0);
//            registerDTO.setCardNo(e.get("CARDNO")==null?null:(String)e.get("CARDNO"));

//            list.add(registerDTO);
        });
    }
}
