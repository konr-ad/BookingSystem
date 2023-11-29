package com.booking.bookingSystem.service;

import com.booking.bookingSystem.model.DigitalKey;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.qrCode.QrCodeGenerator;
import com.booking.bookingSystem.repository.DigitalKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Service
public class DigitalKeyService {

    private final DigitalKeyRepository digitalKeyRepository;
    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public DigitalKeyService(DigitalKeyRepository digitalKeyRepository, QrCodeGenerator qrCodeGenerator) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

//    public BufferedImage testGenerateQRCode() throws Exception {
//        String qrCodeData = "http://example.com";
//        return qrCodeGenerator.generateQRCodeImage(qrCodeData);
//    }

    public DigitalKey createAndSaveDigitalKey(String qrCode, LocalDateTime validFrom, LocalDateTime validUntil, Reservation reservation) {
        DigitalKey digitalKey = new DigitalKey();
        digitalKey.setQrCode(qrCode);
        digitalKey.setValidFrom(validFrom);
        digitalKey.setValidUntil(validUntil);
        digitalKey.setReservation(reservation);
        return digitalKeyRepository.save(digitalKey);
    }
}
