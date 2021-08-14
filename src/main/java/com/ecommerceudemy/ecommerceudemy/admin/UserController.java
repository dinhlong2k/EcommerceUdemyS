package com.ecommerceudemy.ecommerceudemy.admin;

import com.ecommerceudemy.ecommerceudemy.DTO.UserDTO;
import com.ecommerceudemy.ecommerceudemy.Exception.UserNotFoundException;
import com.ecommerceudemy.ecommerceudemy.model.Roles;
import com.ecommerceudemy.ecommerceudemy.model.User;
import com.ecommerceudemy.ecommerceudemy.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String HomeUser(ModelMap modelMap){
        List<User> listUser=userService.listUser();
        modelMap.addAttribute("userlist",listUser);
        return "admin/user";
    }

    @GetMapping("/users/createuser")
    public String createUser(ModelMap modelMap){
        UserDTO userDTO=new UserDTO();
        userDTO.setEnabled(true);
        Set<Roles> listRole=new HashSet<>();
        listRole=userService.listRole();
        String title="CREATE NEW USER";

        modelMap.addAttribute("pageTitle",title);
        modelMap.addAttribute("listRole",listRole);
        modelMap.addAttribute("userDto",userDTO);
        return "admin/userForm";
    }

    @PostMapping("/users/saveuser")
    public String create(ModelMap modelMap, @ModelAttribute("userDto") UserDTO userDTO, RedirectAttributes redirectAttributes){

        String email=userDTO.getEmail();
        boolean check=userService.checkIdEmail(userDTO.getEmail(),userDTO.getUserId());

        if(!check){
            redirectAttributes.addFlashAttribute("message1","Another user is used this email");
            return "redirect:/admin/users";
        }

        userService.saveUser(userDTO);
        redirectAttributes.addFlashAttribute("message","The user have been save successfully");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String EditUser(ModelMap modelMap,@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws UserNotFoundException {

        try{
            User user= userService.getUserById(id);
            UserDTO userDTO =new UserDTO();
            userDTO= userService.converUsertoUserDTO(userDTO,user);
            userDTO.setUserId(user.getUserId());
            Set<Roles> setRole=new HashSet<>();
            setRole=userService.listRole();
            String title="EDIT USER";

            modelMap.addAttribute("pageTitle",title);
            modelMap.addAttribute("listRole",setRole);
            modelMap.addAttribute("userDto",userDTO);

            return "admin/userForm";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message1",ex.getMessage());
            
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, ModelMap modelMap,RedirectAttributes redirectAttributes){
        try{

            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message","The user have been deleted successfully");
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message1",ex.getMessage());
        }
        return "redirect:/admin/users";
    }
}
