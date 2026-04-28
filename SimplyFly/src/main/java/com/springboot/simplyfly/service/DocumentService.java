package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.DocumentResDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.model.Document;
import com.springboot.simplyfly.model.Passenger;
import com.springboot.simplyfly.repository.DocumentRepository;
import com.springboot.simplyfly.repository.PassengerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final PassengerRepository passengerRepository;

    private final static String UPLOAD_PATH = "D:/SimplyFly-Air Ticket Booking System/simplyfly-app/public";

    public DocumentResDto uploadDocument(long passengerId, MultipartFile file) throws IOException {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Passenger id"));

        // Fetch the file name :- to save it in DB name.extension -split(.)[1]
        String fileName = "DOC_" + UUID.randomUUID().toString().substring(0, 8)
                + "_" + file.getOriginalFilename();

        // Prepare the Path using java.nio
        Path path =  Paths.get(UPLOAD_PATH,fileName);

        Files.write(path, file.getBytes());

        Document document = new Document();
        document.setIdProof(fileName);
        document.setPassenger(passenger);
        documentRepository.save(document);

        return new DocumentResDto(
              document.getIdProof(),
              document.getPassenger().getId(),
              document.getPassenger().getName(),
              document.getPassenger().getBooking().getId()
        );
    }
}