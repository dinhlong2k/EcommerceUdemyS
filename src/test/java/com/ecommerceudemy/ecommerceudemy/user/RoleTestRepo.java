package com.ecommerceudemy.ecommerceudemy.user;


import com.ecommerceudemy.ecommerceudemy.Repository.RoleRepository;
import com.ecommerceudemy.ecommerceudemy.model.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.management.relation.Role;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleTestRepo {

    @Autowired
    private RoleRepository roleRepo;

    @Test
    public void createRole(){
        Roles roleAdmin=new Roles("Admin","Can do Everything");

        Roles roleSalePerson=new Roles("SalePerson","Manage Product Price, Customers, Shipping, Oders and sales Report");

        Roles roleEditor=new Roles("Editor","Manage Categories, Brands, Products, Articles and Menu");

        Roles roleShipper=new Roles("Shipper","View Products, Orders, and Update Order status");

        Roles roleAssistant=new Roles("Assistant","Manage questions and reviews");

        roleRepo.saveAll(List.of(roleAdmin,roleEditor,roleShipper,roleSalePerson,roleAssistant));
    }
}
