package com.project.tickets.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.project.tickets.domain.entities.QrCode;
import com.project.tickets.domain.entities.QrCodeStateEnum;
import com.project.tickets.domain.entities.Ticket;
import com.project.tickets.exceptions.QrCodeGenerationException;
import com.project.tickets.repositories.QrCodeRepository;
import com.project.tickets.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

	private final QRCodeWriter qrCodeWriter;

	private final QrCodeRepository qrCodeRepository;

	private static final int QR_WIDTH = 300;

	private static final int QR_HEIGHT = 400;

	@Override
	public QrCode generateQrcode(Ticket ticket) throws WriterException, IOException {

		try {
			UUID uniqueId = UUID.randomUUID();

			String QrCodeImage = generateQrCodeImage(uniqueId);

			QrCode qrCode = new QrCode();
			qrCode.setId(uniqueId);
			qrCode.setValue(QrCodeImage);
			qrCode.setStatus(QrCodeStateEnum.ACTIVE);
			qrCode.setTicket(ticket);

			qrCodeRepository.saveAndFlush(qrCode);
			return qrCode;

		}
		catch (WriterException | IOException e) {
			throw new QrCodeGenerationException("Error generating QR code", e);

		}
	}

	private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
		BitMatrix bitMatrix = qrCodeWriter.encode(uniqueId.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

		// BufferedImage qrCodeImage= MatrixToImageWriter.toBufferedImage(bitMatrix);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			// ImageIO.write(qrCodeImage,"PNG",baos);
			byte[] imageBytes = baos.toByteArray();

			return Base64.getEncoder().encodeToString(imageBytes);
		}

	}

}
