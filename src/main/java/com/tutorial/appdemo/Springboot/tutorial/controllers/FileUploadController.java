package com.tutorial.appdemo.Springboot.tutorial.controllers;

import com.tutorial.appdemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.appdemo.Springboot.tutorial.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping (path = "/api/v1/FileUpload")
public class FileUploadController {
// This controller receive file/image from client
    //Inject Storage Service here
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            //save file to a folder => use a service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","upload file succesfully", generatedFileName)
            );
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed",exception.getMessage(), null)
            );
        }
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }

    //load all uploaded files
    @GetMapping("/getAllFiles")
    public ResponseEntity<ResponseObject> getUploadedFiles(){
        try{
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request "readDetailFile")
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile",path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok","List files successful", urls));
        }catch (Exception exception){
            return ResponseEntity.ok(new ResponseObject("failed","List file unsuccessful", null));
        }
    }
}
