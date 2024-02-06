package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.model.DigitalKey;
import com.booking.bookingSystem.qrCode.QrCodeGenerator;
import com.booking.bookingSystem.service.DigitalKeyService;
import com.booking.bookingSystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RestController
@RequestMapping("/api/digital-key")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;
    private final EmailService emailService;
    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public DigitalKeyController (DigitalKeyService digitalKeyService, EmailService emailService, QrCodeGenerator qrCodeGenerator) {
        this.digitalKeyService = digitalKeyService;
        this.emailService = emailService;
        this.qrCodeGenerator = qrCodeGenerator;
    }

//    @GetMapping("/test-qr")
//    public ResponseEntity<byte[]> testQRCode() throws Exception {
//        BufferedImage qrImage = digitalKeyService.generateTestQRCode();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(qrImage, "png", baos);
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
//    }
@PostMapping("/generateQRCode")
public ResponseEntity<?> generateQRCode(@RequestParam String text) {
    try {
        BufferedImage qrImage = qrCodeGenerator.generateQRCodeImage(text);
        DigitalKey storedQRCode = digitalKeyService.storeQRCode(text); //do wylogowania

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error generating QR Code: " + e.getMessage());
    }
}

    @PostMapping("/sendQRCode")
    public ResponseEntity<?> sendQRCode(@RequestParam String encodedText, @RequestParam String email) {
        try {
            // Retrieve the QR code data from the database using the ID
            DigitalKey qrCode = digitalKeyService.findByEncodedText(encodedText);

            // Generate the QR code image from the stored data
            BufferedImage qrImage = qrCodeGenerator.generateQRCodeImage(qrCode.getEncodedText());

            // Send the QR code via email
            emailService.sendEmailWithQRCode(email, "Your QR Code", "Here is your QR Code.", qrImage);

            return ResponseEntity.ok().body("QR Code sent to email: " + email);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error sending QR Code: " + e.getMessage());
        }
    }

//    @PostMapping("/validate")
//    public ResponseEntity<?> validateQRCode(@RequestBody String decodedText) {
//        // Process the decodedText as needed
//        System.out.println("Received QR Code: " + decodedText);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
