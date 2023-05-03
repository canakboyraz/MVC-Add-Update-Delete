package com.works.services;
import com.works.props.User;
import com.works.utils.DB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<User> users(int p) {
        List<User> ls = new ArrayList<>();
        DB db = new DB();
        try {
            p = p - 1;
            p = p * 50;
            String sql = "select * from users where status = 1 order by uid DESC limit ?,50";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,p);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {
                User u = new User();
                u.setUid( rs.getInt("uid") );
                u.setName( rs.getString("name") );
                u.setSurname( rs.getString("surname") );
                u.setEmail( rs.getString("email") );
                u.setDate( rs.getString("date") );
                u.setDeleteUser(rs.getInt("deleteUser"));
                u.setPassword(rs.getString("password"));
                u.setAge(rs.getInt("age"));
                ls.add(u);
            }
        }catch (Exception ex) {
            System.err.println("Users Error : " + ex);
        }finally {
            db.close();
        }
        return ls;
    }

    public int deleteUser(int uid) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "update users set `deleteUser` = 1 where uid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,uid);
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }

    public int revokeUser(int uid) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "update users set `deleteUser` = 0 where uid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,uid);
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }

    public int totalCount(){
        int count = 0;
        DB db = new DB();
        try {
            String sql = "select count(uid) as count from users where status = 1";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                count = rs.getInt("count");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return count;
    }

    public int totalCountNote(){
        int countnid = 0;
        DB db = new DB();
        try {
            String sql = "select count(nid) as countnid from note";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                countnid = rs.getInt("countnid");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return countnid;
    }

    public int userSave(User user){
        int status = 0;
        DB db = new DB();
        try {
            String sql = "insert into users(uid,name,surname,email,password,age,date,status,deleteUser) values(null,?,?,?,?,?,now(),1,0)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,user.getName());
            pre.setString(2,user.getSurname());
            pre.setString(3,user.getEmail());
            pre.setString(4, user.getPassword());
            pre.setInt(5,user.getAge());
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }

    public User showUser( int uid ) {
        DB db = new DB();
        User u = new User();
        try {
            String sql = "select * from users where uid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,uid);
            ResultSet rs = pre.executeQuery();
            if ( rs.next() ) {
                u.setUid( rs.getInt("uid") );
                u.setName( rs.getString("name") );
                u.setSurname( rs.getString("surname") );
                u.setEmail( rs.getString("email") );
                u.setPassword(rs.getString("password"));
                u.setAge(rs.getInt("age"));
                u.setDate( rs.getString("date") );
            }
        }catch (Exception ex) {
            System.err.println("single Error : " + ex);
        }finally {
            db.close();
        }
        return u;
    }

    public int updateUser(User user) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "update users set name = ?, surname = ?, email = ?, password = ?, age = ? where uid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,user.getName());
            pre.setString(2,user.getSurname());
            pre.setString(3,user.getEmail());
            pre.setString(4,user.getPassword());
            pre.setInt(5,user.getAge());
            pre.setInt(6,user.getUid());
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }
}
