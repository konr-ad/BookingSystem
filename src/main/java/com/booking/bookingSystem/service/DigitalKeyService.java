package com.booking.bookingSystem.service;

import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.DigitalKey;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.qrCode.QrCodeGenerator;
import com.booking.bookingSystem.repository.DigitalKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
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

    public DigitalKey storeQRCode(String encodedText) {
        DigitalKey qrCode = new DigitalKey();
        qrCode.setEncodedText(encodedText);
        return digitalKeyRepository.save(qrCode);
    }

    public DigitalKey findById(Long qrCodeId) {
        return digitalKeyRepository.findById(qrCodeId)
                .orElseThrow(() -> new EntityNotFoundException("QR Code not found"));
    }

    public DigitalKey findByEncodedText(String encodedText) {
        return digitalKeyRepository.findByEncodedText(encodedText)
                .orElseThrow(() -> new EntityNotFoundException("QR Code not found"));
    }

    public boolean isValid(String encodedText) {
        DigitalKey key = findByEncodedText(encodedText);
        return LocalDateTime.now().isAfter(key.getValidFrom()) && LocalDateTime.now().isBefore(key.getValidUntil());
    }
}
