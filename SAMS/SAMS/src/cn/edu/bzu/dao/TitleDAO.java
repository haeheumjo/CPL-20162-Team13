package cn.edu.bzu.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import cn.news.jsp.entity.Title;


public class TitleDAO {
    public List readFirstTitle(){
        List<Title> list =new ArrayList<Title>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","1q2w");
            String sql="select * from student";
            psmt=con.prepareStatement(sql);
            rs=psmt.executeQuery();
            
            while(rs.next())
            {
            	String id=rs.getString("s_id");
                String name=rs.getString("s_name");
                String creator=rs.getString("year");
                String createTime=rs.getString("dept");
                Title tl=new Title(id, name, creator, createTime);
                list.add(tl);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally
        {
            try {
                if(rs!=null)
                {
                    rs.close();
                }
                if(psmt!=null)
                {
                    psmt.close();
                }
                if(con!=null)
                {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
}