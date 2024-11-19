package com.creator.console.Creator.Console.JwtConfig;

import org.springframework.web.bind.annotation.RestController;

import com.creator.console.Creator.Console.Course;
import com.creator.console.Creator.Console.CourseRepo;
import com.creator.console.Creator.Console.User;
import com.creator.console.Creator.Console.UserRepo;
import com.creator.console.Creator.Console.Video;
import com.creator.console.Creator.Console.VideoRepo;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class Controller {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CourseRepo courseRepo;
    
    @Autowired
    private VideoRepo videoRepo;

    @PostMapping("/course")
    public String saveCourse(@RequestParam String name, @RequestParam String discription,@RequestParam String price, @RequestParam String url,Principal p) {
        Course course=new Course();
        String creatoremail=p.getName();
        Optional<User> creator= userRepo.findByEmail(creatoremail);
        course.setCreatorid(creator.get().getId());
        course.setDiscription(discription);
        course.setName(name);
        course.setPicurl(url);
        course.setPrice(price);
        courseRepo.save(course);
        return"ok";
        
    }

    @PostMapping("/savevideo")
    public String saveVideo(@RequestParam String url,@RequestParam String title,Principal p) {
       Optional<User> creator=userRepo.findByEmail(p.getName());
       Course course=courseRepo.findByCreatorid(creator.get().getId());
       Video video = new Video();
       video.setCourseid(course.getId());
       video.setUrl(url);
       video.setTitle(title);
       videoRepo.save(video);
       
        return "ok";
    }

    
    
}
