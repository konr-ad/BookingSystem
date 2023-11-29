package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.service.DigitalKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

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
        BufferedImage qrImage = digitalKeyService.testGenerateQRCode();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    }
}
