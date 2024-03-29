package utils;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * 数据库表结构批量添加字段
 */
public class AddTableColumnUtils {

    public static void main(String[] args) throws Exception {
        String[] tableSpaces = {"KJHS", "ZJSB"};
        String[][] tableNames = {{"VOUCHER","VOUDETAIL"}};

        TableColumnInsert t = new TableColumnInsert();
        for (int j = 0; j < tableNames.length; j++) {
            for (int i = 0; i < tableNames[j].length; i++) {
                t.insertTable(tableSpaces[j], tableNames[j][i]);
                t.queryIsAdmdiv(tableSpaces[j], tableNames[j][i]);
            }
        }
    }

    public void queryIsAdmdiv(String tableSpace ,String tableName) {
        PreparedStatement ps = null,ps1 = null, ps2 = null;
        ResultSet rs = null;
        try {
            Connection conn =getConn();
            int count = 0;
            String sql = " SELECT * FROM ALL_tab_columns WHERE table_name=?  AND OWNER=? AND column_name='ADMDIVCODE' ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, tableName);
            ps.setObject(2, tableSpace);
            rs = ps.executeQuery();
            while(rs.next()){
                count++;
            }
            System.out.println(".............行政区划条数：" + count);
            if(count == 0){
                String sql1 = " INSERT INTO base.tablestructuresetting (GUID, TABLE_FIELD, TABLE_NAME, TABLE_SPACE, FIELD_TYPE, DEFAULT_VALUE, IS_NULL, FIELD_NAME, IS_PKEY) VALUES\n" +
                        "(seq_tablestructuresetting.nextval, 'ADMDIVCODE', ?, ?, 'VARCHAR2(6)', '', '1', '', '0') ";
                String sql2 = " INSERT INTO base.tablestructuresetting (GUID, TABLE_FIELD, TABLE_NAME, TABLE_SPACE, FIELD_TYPE, DEFAULT_VALUE, IS_NULL, FIELD_NAME, IS_PKEY) VALUES\n" +
                        "(seq_tablestructuresetting.nextval, 'ADMDIVNAME', ?, ?, 'VARCHAR2(200)', '', '1', '', '0') ";
                ps1 = conn.prepareStatement(sql1);
                ps1.setObject(1, tableName);
                ps1.setObject(2, tableSpace);
                ps1.execute();

//                ps2 = conn.prepareStatement(sql2);
//                ps2.setObject(1, tableName);
//                ps2.setObject(2, tableSpace);
//                ps2.execute();
                System.out.println(".............【插入】行政区划成功！");
            }else {
                System.out.println(".............【已存在】行政区划");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                if(null != ps1){
                    ps1.close();
                }
                if(null != ps2){
                    ps2.close();
                }
                getConn().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertTable(String tableSpace ,String tableName){
        PreparedStatement ps = null;
        try {
            Connection conn = getConn();
            String sql = " INSERT INTO BASE.TABLESTRUCTURESETTING\n" +
                    "  (GUID,\n" +
                    "   TABLE_FIELD,\n" +
                    "   TABLE_NAME,\n" +
                    "   TABLE_SPACE,\n" +
                    "   FIELD_TYPE,\n" +
                    "   DEFAULT_VALUE,\n" +
                    "   IS_NULL,\n" +
                    "   FIELD_NAME,\n" +
                    "   IS_PKEY)\n" +
                    "  WITH TT AS\n" +
                    "   (SELECT T.COLUMN_NAME    TABLE_FIELD,\n" +
                    "           T.TABLE_NAME     TABLE_NAME,\n" +
                    "           T.OWNER          TABLE_SPACE,\n" +
                    "           T.DATA_TYPE      FIELD_TYPE,\n" +
                    "           T.DATA_DEFAULT   DEFAULT_VALUE,\n" +
                    "           T.NULLABLE       IS_NULL,\n" +
                    "           T.DATA_LENGTH,\n" +
                    "           T.DATA_PRECISION,\n" +
                    "           T.COLUMN_ID\n" +
                    "      FROM ALL_TAB_COLUMNS T\n" +
                    "     WHERE T.TABLE_NAME = ? \n" +
                    "       AND T.OWNER = ?\n" +
                    "     ORDER BY T.COLUMN_ID)\n" +
                    "  SELECT SEQ_TABLESTRUCTURESETTING.NEXTVAL,\n" +
                    "         TT.TABLE_FIELD,\n" +
                    "         TT.TABLE_NAME,\n" +
                    "         TT.TABLE_SPACE,\n" +
                    "         DECODE(TT.FIELD_TYPE,\n" +
                    "                'NUMBER',\n" +
                    "                TT.FIELD_TYPE || '(' || TT.DATA_PRECISION || ')',\n" +
                    "                TT.FIELD_TYPE || '(' || TT.DATA_LENGTH || ')'),\n" +
                    "         '' DEFAULT_VALUE,\n" +
                    "         DECODE(TT.IS_NULL, 'Y', 1, 'N', 0) IS_NULL,\n" +
                    "         '' FIELD_NAME,\n" +
                    "         0 IS_PKEY\n" +
                    "    FROM TT " ;
            ps = conn.prepareStatement(sql);
            ps.setObject(1, tableName);
            ps.setObject(2, tableSpace);
            ps.execute();
            System.out.println(tableSpace + "." + tableName + "插入字段属性成功!");
        }catch (Exception e){
            e.printStackTrace();
            try {
                ps.close();
                getConn().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
            Properties prop=new Properties();
            InputStream inStream= TableColumnInsert.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(inStream);
            String url = prop.getProperty("jdbc.url");
            String user = prop.getProperty("jdbc.username");
            String password = prop.getProperty("jdbc.password");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}

class TableColumnInsert{

    public void insertTable(String tableSpace, String s) {

    }

    public void queryIsAdmdiv(String tableSpace, String s) {

    }
}
