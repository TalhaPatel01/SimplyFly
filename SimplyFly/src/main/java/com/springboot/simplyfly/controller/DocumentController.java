package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.DocumentResDto;
import com.springboot.simplyfly.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload/{passengerId}")
    public DocumentResDto uploadDocument(@PathVariable long passengerId, @RequestParam("file") MultipartFile file) throws IOException{
        return documentService.uploadDocument(passengerId, file);
    }
}