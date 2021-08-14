package com.ecommerceudemy.ecommerceudemy.service.Imp;

import com.ecommerceudemy.ecommerceudemy.DTO.UserDTO;
import com.ecommerceudemy.ecommerceudemy.Exception.UserNotFoundException;
import com.ecommerceudemy.ecommerceudemy.Repository.RoleRepository;
import com.ecommerceudemy.ecommerceudemy.Repository.UserRepository;
import com.ecommerceudemy.ecommerceudemy.model.Roles;
import com.ecommerceudemy.ecommerceudemy.model.User;
import com.ecommerceudemy.ecommerceudemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> listUser() {
        return userRepo.findAll();
    }

    @Override
    public Set<Roles> listRole() {
        List<Roles> listRoles=roleRepo.findAll();

        Set<Roles> setRoles=new HashSet<>(listRoles);
        return  setRoles;
    }

    @Override
    public User findEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        if(userDTO.getUserId() == null){

            User user=new User();
            user=convertUserDTOtoUser(userDTO,user);
            user.setPassword(encoder().encode(userDTO.getPassword()));
            userRepo.save(user);
        }else if(userDTO.getUserId() != null){
            User user=userRepo.findByUserId(userDTO.getUserId());

            if(userDTO.getPassword().isEmpty()){
                String password=user.getPassword();
                user=convertUserDTOtoUser(userDTO,user);
                user.setPassword(password);
            }else{
                String password=encoder().encode(userDTO.getPassword());
                user=convertUserDTOtoUser(userDTO,user);
                user.setPassword(password);
            }

            userRepo.save(user);
        }
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        try{
            User userfind=userRepo.findByUserId(id);
            return userfind;
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    @Override
    public boolean checkIdEmail(String email, Integer id) {
        User findUser=userRepo.findByEmail(email);

        if(findUser == null) return true;

        boolean isCreatingNew = (id==null);

        if(isCreatingNew){
            if(findUser != null) return false;
        }else{
            if(findUser.getUserId() != id){
                return false;
            }
        }
        return true;
    }

    @Override
    public User convertUserDTOtoUser(UserDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEnabled(userDTO.isEnabled());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        Set<Roles> setRole=userDTO.getListRole();
        user.setRoles(setRole);

        return user;

    }

    @Override
    public UserDTO converUsertoUserDTO(UserDTO userDTO, User user) {

        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        Set<Roles> setRoles=user.getRoles();
        userDTO.setListRole(setRoles);
        return userDTO;
    }

    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById=userRepo.countByUserId(id);

        if(countById ==null || countById ==0){
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        userRepo.deleteByUserId(id);
    }
}
