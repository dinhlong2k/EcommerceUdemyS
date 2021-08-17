package com.ecommerceudemy.ecommerceudemy.service;

import com.ecommerceudemy.ecommerceudemy.DTO.UserDTO;
import com.ecommerceudemy.ecommerceudemy.Exception.UserNotFoundException;
import com.ecommerceudemy.ecommerceudemy.model.Roles;
import com.ecommerceudemy.ecommerceudemy.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    public List<User> listUser();
    public Set<Roles> listRole();
    public User findEmail(String email);
    public void saveUser(UserDTO userDTO);
    public User getUserById(Integer id) throws UserNotFoundException;
    public boolean checkIdEmail(String email,Integer id);
    public User convertUserDTOtoUser(UserDTO userDTO,User user);
    public UserDTO converUsertoUserDTO(UserDTO userDTO,User user);
    public void deleteUser(Integer id) throws UserNotFoundException;
    public void updateUserEnabledStatus(Integer id,boolean enabled);

}
