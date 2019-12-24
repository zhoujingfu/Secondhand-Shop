package com.gjj.repositories;

import com.gjj.dao.UserRepositoryCustom;
import com.gjj.models.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, String>, UserRepositoryCustom{
	
	Page<AdminUser> findAllByAccountOrPhone(String account, String phone, Pageable pageable);

}
