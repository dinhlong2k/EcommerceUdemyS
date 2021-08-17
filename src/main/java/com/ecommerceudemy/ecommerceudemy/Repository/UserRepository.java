package com.ecommerceudemy.ecommerceudemy.Repository;

import com.ecommerceudemy.ecommerceudemy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(int i);
    User findByEmail(String email);
    public Long countByUserId(Integer id);
    public void deleteByUserId(Integer id);

    @Query("UPDATE User u SET u.enabled= ?2 WHERE u.userId=?1")
    @Modifying
    public void updateEnableUser(Integer id,boolean enabled);
}
