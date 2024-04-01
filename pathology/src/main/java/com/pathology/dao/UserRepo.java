package com.pathology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;

@Repository
public interface UserRepo extends JpaRepository<PathologyUser,Long> {
    PathologyUser findByEmailId(String emailId);

}
