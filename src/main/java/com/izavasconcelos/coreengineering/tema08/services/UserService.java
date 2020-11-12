package com.izavasconcelos.coreengineering.tema08.services;

import com.izavasconcelos.coreengineering.tema08.dao.UserDAO;
import com.izavasconcelos.coreengineering.tema08.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDAO userDao = new UserDAO();
    private List<User> listUsers = userDao.listAllUsers();


    public boolean registerUser(User user) {
        Optional<User> containUser = searchUserName(user.getUserName());
        if(user.getUserName().isEmpty() || containUser.isPresent()) {
            return false;
        }else{
            listUsers.add(user);
            userDao.saveUser(listUsers);
            return true;
        }
    }
    public boolean deleteUser(String name) {
        Optional<User> nameToRemove = searchUserName(name);
        if(nameToRemove.isPresent()) {
            listUsers.removeIf(user -> user.getUserName().equals(name));
            userDao.saveUser(listUsers);
            return true;
        }
        return false;
    }

    public Optional<User> searchUserName(String name) {
        Optional nullPointerName = Optional.ofNullable(name);
        if(!nullPointerName.isPresent()){
            return Optional.empty();
        }
        return listUsers.stream().filter(user -> user.getUserName().equals(name)).findFirst();
    }
    public List<User> listAllUsers() {
        return listUsers;
    }

    public boolean saveUser(List<User> userList) {
        return userDao.saveUser(userList);
    }
}
