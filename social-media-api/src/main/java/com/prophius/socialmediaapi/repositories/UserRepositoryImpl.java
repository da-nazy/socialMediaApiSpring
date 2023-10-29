package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.mindrot.jbcrypt.*;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE="INSERT INTO USERS(USER_ID,USER_NAME,PROFILE_PICTURE,EMAIL,PASSWORD) VALUES(NEXTVAL('USERS_SEQ'),?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM USERS WHERE EMAIL =?";
    private static final String SQL_FIND_BY_ID="SELECT * FROM USERS WHERE USER_ID=?";

    private static final String SQL_FIND_BY_EMAIL="SELECT * FROM USERS WHERE EMAIL=?";

    private static final String SQL_DELETE="DELETE FROM USERS WHERE USER_ID=?";
    private static final String SQL_UPDATE="UPDATE USERS SET PROFILE_PICTURE=?,FOLLOWERS=?,FOLLOWINGS=? WHERE USER_ID=?";

    private static final String SQL_UPDATE_FOLLOWINGS="UPDATE USERS SET FOLLOWINGS=? WHERE USER_ID=?";
    private static final String SQL_UPDATE_FOLLOWERS="UPDATE USERS SET FOLLOWERS=? WHERE USER_ID=?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String username, String profilePicture, String email, String password) throws AuthException {
        String hashedPassword=BCrypt.hashpw(password,BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder =new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps=connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,username);
                ps.setString(2,profilePicture);
                ps.setString(3,email);
                ps.setString(4,hashedPassword);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch(Exception e){
            throw new AuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthException {
        try{
            User user=jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            //  if(!password.equals(user.getPassword()))
            if(!BCrypt.checkpw(password,user.getPassword()))
                throw new AuthException("Invalid email/password");
            return user;
        }catch(EmptyResultDataAccessException e){
            System.out.println(e);
            throw new AuthException("Invalid email/password");
        }
    }



    @Override
    public void update(Integer userId, String profilePicture, ArrayList<String> followers, ArrayList<String> followings) throws BadRequestException {
        try{
            /**
             *
             String[] followersArrayList<String> = new String[followers.size()];
             for (int i = 0; i < followers.size(); i++) {
             followersArrayList<String>[i] = String.valueOf(followers.get(i));
             }
             String[] followingsArrayList<String> = new String[followings.size()];
             for (int i = 0; i < followings.size(); i++) {
             followingsArrayList<String>[i] = String.valueOf(followings.get(i));
             }
             */

            System.out.println(userId+""+profilePicture+""+followers+""+followings);
            jdbcTemplate.update(SQL_UPDATE,new Object[]{profilePicture,followers,followings,userId});
        }catch(Exception e){
            System.out.println(userId+""+profilePicture+""+followers+""+followings);
            throw new BadRequestException(e.getMessage());
        }
    }


    private void updateFollowings(Integer userId,ArrayList<String> followings) throws BadRequestException {
        try{

               String[] followingsArrayList = new String[followings.size()];
                         for (int i = 0; i < followings.size(); i++) {
                             followingsArrayList[i] = String.valueOf(followings.get(i));
                         }

            jdbcTemplate.update(SQL_UPDATE_FOLLOWINGS,new Object[]{followingsArrayList,userId});
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
     public void follow(Integer userId,Integer followerId){
        // add user id to followers of followerId
         // add followerId to followings of user
        try{
            System.out.println(userId+"user:follower"+followerId);
            User user=this.findById(userId);
            User follower=this.findById(followerId);
            ArrayList<String> users=user.getFollowers();
            ArrayList<String> followers=follower.getFollowers();
            boolean containsValue = false;

            for (String element : user.getFollowings()) {
                if (element.equals(userId.toString())) {
                    containsValue = true;
                    break; // Exit the loop once the value is found
                }
            }

            if(containsValue){
                System.out.println("user already exist");

            }else{
                follower.getFollowers().add(userId.toString());
                user.getFollowings().add(followerId.toString());
                System.out.println(follower.getFollowers());
                this.updateFollowers(followerId,follower.getFollowers());
                this.updateFollowings(userId,user.getFollowings());
            }


        }catch(Exception e){
            throw  new ResourceNotFoundException("user not found!"+e.getMessage());
        }

     }
     public void unfollow(Integer userId,Integer followerId){
try{
    System.out.println(userId+"user:follower"+followerId);
    User user=this.findById(userId);
    User follower=this.findById(followerId);
    boolean containsValue = false;

    for (String element : user.getFollowings()) {
        if (element.equals(userId.toString())) {
            containsValue = true;
            break; // Exit the loop once the value is found
        }
    }

    System.out.println("user already remove if exist");
    follower.getFollowers().remove(userId.toString());
    user.getFollowings().remove(followerId.toString());
    System.out.println(follower.getFollowers());
    this.updateFollowers(followerId,follower.getFollowers());
    this.updateFollowings(userId,user.getFollowings());

}catch(Exception e){
    e.printStackTrace();
}
     }

    private void updateFollowers(Integer userId,ArrayList<String>  followers) throws BadRequestException {
    try{



        String[] followersArrayList = new String[followers.size()];
        for (int i = 0; i < followers.size(); i++) {
            followersArrayList[i] = String.valueOf(followers.get(i));
        }
        System.out.println(followersArrayList+"check danny");
        jdbcTemplate.update(SQL_UPDATE_FOLLOWERS,new Object[]{followersArrayList,userId});

    }catch(Exception e){
        System.out.println(e.getMessage());
        throw new BadRequestException(e.getMessage());
    }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId},userRowMapper);
    }

    @Override
    public void removeById(Integer userId) throws ResourceNotFoundException {
        try{
            jdbcTemplate.update(SQL_DELETE,new Object[]{userId});
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private RowMapper<User> userRowMapper=((rs, rowNum)->{
        return new User(rs.getInt("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("PROFILE_PICTURE"),
                arrayToList(rs.getArray("Followers")),
                arrayToList(rs.getArray("Followings")),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD")
        );
    });

    private ArrayList<String> arrayToList(java.sql.Array sqlArray) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (sqlArray != null) {
            try {
                Object[] array = (Object[]) sqlArray.getArray();
                for (Object element : array) {
                    if (element != null) {
                        arrayList.add(element.toString());
                    } else {
                        // Handle the case where element is null (e.g., add a default value or skip it)
                        // arrayList.add("DefaultValue");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception properly in your application
            }
        }
        return arrayList;
    }


}
