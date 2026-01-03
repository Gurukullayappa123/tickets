package com.project.tickets.repositories;

import com.project.tickets.domain.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

}
