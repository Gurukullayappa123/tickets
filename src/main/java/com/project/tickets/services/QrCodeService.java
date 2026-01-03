package com.project.tickets.services;

import com.google.zxing.WriterException;
import com.project.tickets.domain.entities.QrCode;
import com.project.tickets.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface QrCodeService {

	QrCode generateQrcode(Ticket ticket) throws WriterException, IOException;

}
