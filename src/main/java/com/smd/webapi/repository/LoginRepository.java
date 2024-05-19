package com.smd.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smd.webapi.model.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    @Query("SELECT e FROM Login e JOIN FETCH e.roles WHERE e.username= (:username)")
    public Login findByUsername(@Param("username") String username);
}
