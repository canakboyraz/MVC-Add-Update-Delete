package com.works.services;

import com.works.props.Note;
import com.works.props.User;
import com.works.utils.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    public List<Note> notes(int pa) {
        List<Note> ns = new ArrayList<>();
        DB db = new DB();
        try {
            pa = pa - 1;
            pa = pa * 2;
            String sql = "select * from note order by nid DESC limit ?,2";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,pa);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {
                Note n = new Note();
                n.setNid( rs.getInt("nid") );
                n.setTitle( rs.getString("title") );
                n.setDetail( rs.getString("detail") );
                ns.add(n);
            }
        }catch (Exception ex) {
            System.err.println("Notes Error : " + ex);
        }finally {
            db.close();
        }
        return ns;
    }

    public int noteSave( Note note){
        int status = 0;
        DB db = new DB();
        try {
            String sql = "insert into note(nid,title,detail) values(null,?,?)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,note.getTitle());
            pre.setString(2,note.getDetail());
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }

    public int deleteNote(int nid) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "delete from note where nid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,nid);
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }

    public int updateNote(Note note) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "update note set title = ?, detail = ? where nid = ?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1,note.getTitle());
            pre.setString(2,note.getDetail());
            pre.setInt(3,note.getNid());
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            db.close();
        }
        return status;
    }
}
