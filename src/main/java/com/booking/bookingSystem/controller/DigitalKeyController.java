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

@RestController
@RequestMapping("/api/digital-key")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;

    @Autowired
    public DigitalKeyController (DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    @GetMapping("/test-qr")
    public ResponseEntity<byte[]> testQRCode() throws Exception {
        BufferedImage qrImage = digitalKeyService.generateTestQRCode();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    }
    @GetMapping("/send-qr")
    public ResponseEntity<?> sendQRCode() throws Exception {
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        String base64QRCode = qrCodeGenerator.generateQRCodeImage("Twoje Dane QR");

        BufferedImage qrImage = qrCodeGenerator.decodeBase64ToImage(base64QRCode);
        File qrFile = qrCodeGenerator.saveImageToFile(qrImage, "tempQRCode.png", "png");

        EmailService emailService = new EmailService();
        emailService.sendEmailWithQRCode("konradk95@gmail.com", "Testowy Kod QR", "Oto Twój kod QR:", qrFile.getAbsolutePath());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createQrCode(@RequestBody DigitalKey digitalKey) {
        DigitalKey createdKey = digitalKeyService.createDigitalKey(digitalKey);
        return new ResponseEntity<>(createdKey, HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateQRCode(@RequestParam String qrCode) {
        boolean isValid = digitalKeyService.validateQRCode(qrCode);
        return new ResponseEntity<>(isValid ? "Valid QR Code" : "Invalid or Expired QR Code", HttpStatus.OK);
    }

}
