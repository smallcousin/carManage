package dao;

import pojo.User;

import java.util.List;

public interface IUserDao {
    //增加
    public void addUser(User user);

    //改
    public void updateUser(int userID, int carNum,String brand);

    //删
    public void delete(int userID);

    //查1
    public User getOneUser(int userID);

    //查all
    public List<User> getAllUser();
}
