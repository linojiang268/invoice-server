package com.icar.invoice.mapper;

import com.icar.invoice.po.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "name")
    })
    User getUserByUsername(String username);

    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "name")
    })
    List<User> findAll();
//
//    @Select("SELECT * FROM users WHERE id = #{id}")
//    @Results({
//            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
//            @Result(property = "nickName", column = "nick_name")
//    })
//    UserEntity getOne(Long id);

//    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
//    void insert(UserEntity user);
//
//    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
//    void update(UserEntity user);
//
//    @Delete("DELETE FROM users WHERE id =#{id}")
//    void delete(Long id);
}
