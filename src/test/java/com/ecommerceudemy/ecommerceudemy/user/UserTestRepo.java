package com.ecommerceudemy.ecommerceudemy.user;

import com.ecommerceudemy.ecommerceudemy.Repository.RoleRepository;
import com.ecommerceudemy.ecommerceudemy.Repository.UserRepository;
import com.ecommerceudemy.ecommerceudemy.model.Roles;
import com.ecommerceudemy.ecommerceudemy.model.User;
import com.ecommerceudemy.ecommerceudemy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.query.Param;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserTestRepo {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createTestUser(){
        User user=new User();
        user.setEmail("nguyendinhlongndl@gmail.com");
        user.setFirstName("dinh");
        user.setLastName("long");
        user.setPassword("12345");
        user.setPhoneNumber("0787495525");
        //user.setEnabled(1);
        Roles roles=entityManager.find(Roles.class,1);
        user.addRole(roles);
        userRepo.save(user);
    }

    @Test
    public void createTestUserMultiRole(){
        User user=new User();
        user.setEmail("longboy698@gmail.com");
        user.setFirstName("dinh");
        user.setLastName("long1");
        user.setPassword("12345");
        user.setPhoneNumber("0355112694");

        user.addRole(new Roles(1));
        user.addRole(new Roles(2));
        userRepo.save(user);
    }

    @Test
    public void ListRoleOfUser(){
        User user=userRepo.findByUserId(1);
        Set<Roles> roles=user.getRoles();
        System.out.println(user.getEmail());
        for(Roles nameRole: roles) System.out.println(nameRole.getNameRole());
    }

    @Test
    public void testDuplicated(){
        String email="longboy698@gmail.com";

        User user=userRepo.findByEmail(email);

        if(user ==null) System.out.println("OK");
        else System.out.println("Duplicated");
    }
}
