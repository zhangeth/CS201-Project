package com.example.demo.web;

//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.example.demo.domain.*;
//import com.example.demo.payload.*;
//import com.example.demo.service.HomePageService;
//
//@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RequestMapping("/app")
//public class HomePageController
//{
//    @Autowired
//    ClubPageService service;
//    
//    @PostMapping("/clubpage")
//    public ClubPage getClubPage( @Valid @RequestBody ClubPageRequest ClubPageRequest)
//    {
//        ClubPage cp = service.getClubPage(ClubPageRequest.getKey());
//        
//        return cp;
//    }
//}