package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository{


    private static final String SQL_FIND_POSTS="SELECT * FROM POSTS WHERE USER_ID=? ";
    private static final String SQL_FIND_POST="SELECT * FROM POSTS WHERE USER_ID=? AND POST_ID=?";
    private static final String SQL_CREATE="INSERT INTO POSTS(POST_ID,USER_ID,TITLE,CONTENT,CREATION_DATE,LIKE_COUNT) VALUES(NEXTVAL('POSTS_SEQ'),?,?,?,?,?)";

    private static final String SQL_DELETE="DELETE FROM POSTS WHERE USER_ID=? AND POST_ID=?";
    private static final String SQL_UPDATE_POST="UPDATE POSTS SET TITLE=?,CONTENT=? LIKE_COUNT=? WHERE USER_ID=? AND POST_ID=?";

    private static final String SQL_LIKE_POST="UPDATE POSTS SET  LIKE_COUNT=? WHERE USER_ID=? AND POST_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(Integer userId, String title, String content, long creationDate,Integer likeCount) throws AuthException {

        try {
            System.out.println(userId+title+content+creationDate+likeCount);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, content);
                ps.setInt(4, likeCount);
                ps.setLong(5,creationDate);
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("POST_ID");


        }catch (Exception e){
            throw new BadRequestException(e.getMessage()+"POst failed");
        }
    }

    @Override
    public void removePost(Integer postId, Integer userId) throws ResourceNotFoundException {

        int count=jdbcTemplate.update(SQL_DELETE,new Object[]{userId,postId});
        if(count==0){
            throw new ResourceNotFoundException("Transaction not found!");
        }
    }

    @Override
    public void likePost(Integer postId, Integer userId,Integer likeCount) throws ResourceNotFoundException {
        try{
            jdbcTemplate.update(SQL_LIKE_POST,new Object[]{likeCount,postId,userId});
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Post getPost(Integer postId, Integer userId) throws ResourceNotFoundException {
        try{
            System.out.println("get post >>>>>>");
            return jdbcTemplate.queryForObject(SQL_FIND_POST,new Object[]{userId,postId},postRowMapper);
        }catch(Exception e){
            System.out.println(e);
            throw new ResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public List<Post> getPosts(Integer userId) throws ResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_POSTS,new Object[]{userId},postRowMapper);
    }

    @Override
    public void updatePost(Integer postId, Integer userId, String title, String content, long creationDate,Integer likeCount) throws ResourceNotFoundException {
        try{
            jdbcTemplate.update(SQL_UPDATE_POST,new Object[]{title,content,likeCount,userId,postId});
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private RowMapper<Post> postRowMapper=((rs, rowNum)->{

        return new Post(
                rs.getInt("POST_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("CONTENT"),
                rs.getLong("CREATION_DATE"),
                rs.getInt("LIKE_COUNT")
        );
    });

}
