package Dao;  
  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.util.ArrayList;  
  
import entity.UserInfo;  
  
public class UserInfoDao {  
  
    //查询所有（查）  
    public ArrayList findAll(){  
        Connection con=null;  
        PreparedStatement psta=null;  
        ResultSet rs=null;  
        ArrayList list=new ArrayList();  
        String sql="select * from userinfo";  
        try{  
            con=BaseDao.getConnection();  
            psta=con.prepareStatement(sql);  
            rs=psta.executeQuery();  
            while(rs.next()){  
                UserInfo obj=new UserInfo();  
                obj.setId(rs.getInt(1));  
                obj.setUser_name(rs.getString(2));  
                obj.setUser_sex(rs.getString(3));  
                obj.setUser_age(rs.getInt(4));  
                obj.setUser_hobby(rs.getString(5));  
                obj.setUser_city(rs.getString(6));  
                obj.setMtext(rs.getString(7));  
                list.add(obj);  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                BaseDao.close(rs, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return list;  
        }  
    }  
      
    //插入方法（增）  
    public boolean save(UserInfo obj){  
        Connection con=null;  
        PreparedStatement psta=null;  
        String sql="insert into userinfo values(?,?,?,?,?,?)";  
        boolean flag=false;  
        try{  
            con=BaseDao.getConnection();  
            psta=con.prepareStatement(sql);  
            psta.setString(1, obj.getUser_name());  
            psta.setString(2, obj.getUser_sex());  
            psta.setString(3, obj.getUser_age()+"");  
            psta.setString(4, obj.getUser_hobby());  
            psta.setString(5, obj.getUser_city());  
            psta.setString(6, obj.getMtext());  
            flag=psta.executeUpdate()>0;  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                BaseDao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return flag;  
        }  
    }  
      
    //删除方法（删）  
    public boolean remove(int id){  
        Connection con=null;  
        PreparedStatement psta=null;  
        boolean flag=false;  
        String sql="delete from userinfo where id=?";  
        try{  
            con=BaseDao.getConnection();  
            psta=con.prepareStatement(sql);  
            psta.setInt(1, id);  
            flag=psta.executeUpdate()>0;  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                BaseDao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return flag;  
        }  
    }  
    //通过id修改，为更新做准备的（改）  
            public UserInfo findById(int id){  
                Connection con=null;  
                PreparedStatement psta=null;  
                ResultSet rs=null;  
                UserInfo obj=null;  
                String sql="select * from userinfo where id=?";  
                try{  
                    con=BaseDao.getConnection();  
                    psta=con.prepareStatement(sql);  
                    psta.setInt(1, id);  
                    rs=psta.executeQuery();  
                    if(rs.next()){  
                        obj=new UserInfo();  
                        obj.setId(rs.getInt(1));  
                        obj.setUser_name(rs.getString(2));  
                        obj.setUser_sex(rs.getString(3));  
                        obj.setUser_age(rs.getInt(4));  
                        obj.setUser_hobby(rs.getString(5));  
                        obj.setUser_city(rs.getString(6));  
                        obj.setMtext(rs.getString(7));  
                    }  
                }catch(Exception e){  
                    e.printStackTrace();  
                }finally{  
                    try{  
                        BaseDao.close(rs, psta, con);  
                    }catch(Exception e){  
                        e.printStackTrace();  
                    }  
                    return obj;  
                }  
            }  
      
    //更新方法（修改数据）（改）  
    public boolean update(UserInfo obj){  
        Connection con=null;  
        PreparedStatement psta=null;  
        String sql="update userinfo set user_name=?,user_sex=?,user_age=?,user_hobby=?,user_city=?,mtext=? where id=?";  
        boolean flag=false;  
        try{  
            con=BaseDao.getConnection();  
            psta=con.prepareStatement(sql);  
            psta.setString(1, obj.getUser_name());  
            psta.setString(2, obj.getUser_sex());  
            psta.setString(3, obj.getUser_age()+"");  
            psta.setString(4, obj.getUser_hobby());  
            psta.setString(5, obj.getUser_city());  
            psta.setString(6, obj.getMtext());  
            psta.setInt(7,obj.getId());  
            flag=psta.executeUpdate()>0;  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                BaseDao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return flag;  
        }  
    }  
      
      
}  