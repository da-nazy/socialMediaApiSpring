package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private static final String SQL_CREATE="INSERT INTO POSTS (POST_ID,USER_ID,TITLE,CONTENT,CREATION_DATE,LIKE_COUNT) VALUES(NEXTVAL('POSTS_SEQ'),?,?,?,?,?)";

    private static final String SQL_DELETE="DELETE FROM POSTS WHERE USER_ID=? AND POST_ID=?";
    private static final String SQL_UPDATE_POST="UPDATE POSTS SET TITLE=?,CONTENT=?,LIKE_COUNT=?,CREATION_DATE=? WHERE USER_ID=? AND POST_ID=?";

    private static final String SQL_LIKE_POST="UPDATE POSTS SET  LIKE_COUNT=? WHERE USER_ID=? AND POST_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(Integer userId, String title, String content, String creationDate,Integer likeCount) throws AuthException {

        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, content);
                ps.setString(4, creationDate);
                ps.setInt(5,likeCount);
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("POST_ID");


        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
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
    public void likePost(Integer postId, Integer userId) throws ResourceNotFoundException {
        try{
          Post post=this.getPost(postId,userId);
            jdbcTemplate.update(SQL_LIKE_POST,new Object[]{post.getLikeCount()+1,postId,userId});
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
    public Page<Post> getPosts(Integer userId, int page, int size) throws ResourceNotFoundException {
        int offset = page * size;
        String sql = "SELECT * FROM POSTS WHERE user_id = ? LIMIT ? OFFSET ?";
        List<Post> posts = jdbcTemplate.query(sql, new Object[]{userId, size, offset}, postRowMapper);

        // You should count the total number of posts for the user to calculate the total pages
        String countSql = "SELECT COUNT(*) FROM POSTS WHERE user_id = ?";
        int totalPosts = jdbcTemplate.queryForObject(countSql, Integer.class, userId);

        return new PageImpl<>(posts, PageRequest.of(page, size), totalPosts);
    }
    /**
     * public List<Post> getPosts(Integer userId) throws ResourceNotFoundException {
     *         return jdbcTemplate.query(SQL_FIND_POSTS,new Object[]{userId},postRowMapper);
     *     }
     */

    @Override
    public void updatePost(Integer postId, Integer userId, String title, String content, String creationDate,Integer likeCount) throws ResourceNotFoundException {
        try{
            jdbcTemplate.update(SQL_UPDATE_POST,new Object[]{title,content,likeCount,creationDate,userId,postId});
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
                rs.getString("CREATION_DATE"),
                rs.getInt("LIKE_COUNT")
        );
    });

}
