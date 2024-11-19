package com.creator.console.Creator.Console;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepo extends JpaRepository<Video,Long>{
    List<Video> findByCourseid(Long courseid);
}
