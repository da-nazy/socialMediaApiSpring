package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.Comment;
import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
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
public class CommentRepositoryImpl implements CommentRepository{
    private static final String SQL_FIND_COMMENTS="SELECT * FROM COMMENTS WHERE USER_ID=? ";
    private static final String SQL_FIND_COMMENT="SELECT * FROM COMMENTS WHERE USER_ID=? AND COMMENT_ID=?";
    private static final String SQL_CREATE="INSERT INTO COMMENTS (COMMENT_ID,POST_ID,USER_ID,CONTENT,CREATION_DATE) VALUES(NEXTVAL('COMMENTS_SEQ'),?,?,?,?)";

    private static final String SQL_DELETE="DELETE FROM COMMENTS WHERE USER_ID=? AND COMMENT_ID=?";
    private static final String SQL_UPDATE_COMMENT="UPDATE COMMENTS SET CONTENT=?,CREATION_DATE=? WHERE USER_ID=? AND COMMENT_ID=?";

@Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createComment(Integer postId,Integer userId, String content, String creationDate) throws AuthException {
        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,postId);
                ps.setInt(2, userId);
                ps.setString(3, content);
                ps.setString(4,creationDate);
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("COMMENT_ID");


        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public void removeComment(Integer commentId, Integer userId) throws ResourceNotFoundException {
        int count=jdbcTemplate.update(SQL_DELETE,new Object[]{userId,commentId});
        if(count==0){
            throw new ResourceNotFoundException("Transaction not found!");
        }
    }

    @Override
    public Comment getComment(Integer commentId, Integer userId) throws ResourceNotFoundException {
        try{
            System.out.println("get post >>>>>>");
            return jdbcTemplate.queryForObject(SQL_FIND_COMMENT,new Object[]{userId,commentId},commentRowMapper);
        }catch(Exception e){
            System.out.println(e);
            throw new ResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public List<Comment> getComments(Integer userId) throws ResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_COMMENTS,new Object[]{userId},commentRowMapper);
    }

    @Override
    public void updateComment(Integer commentId, Integer userId, String content, String creationDate) throws ResourceNotFoundException {
        try{
            jdbcTemplate.update(SQL_UPDATE_COMMENT,new Object[]{content,creationDate,userId,commentId});
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private RowMapper<Comment> commentRowMapper=((rs, rowNum)->{

        return new Comment(
                rs.getInt("COMMENT_ID"),
                rs.getInt("USER_ID"),
                rs.getInt("POST_ID"),
                rs.getString("CONTENT"),
                rs.getString("CREATION_DATE")
        );
    });
}
