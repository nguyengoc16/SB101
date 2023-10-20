package com.tutorial.appdemo.Springboot.tutorial.controllers;

import com.tutorial.appdemo.Springboot.tutorial.models.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping (path = "/api/v1/FileUpload")
public class FileUploadController {
// This controller receive file/image from client

@PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            //save file to a folder => use a service
        }catch (Exception exception){

        }

    }

}
