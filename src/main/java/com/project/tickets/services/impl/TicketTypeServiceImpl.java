package com.project.tickets.services.impl;

import com.project.tickets.domain.entities.Ticket;
import com.project.tickets.domain.entities.TicketStatusEnum;
import com.project.tickets.domain.entities.TicketType;
import com.project.tickets.domain.entities.User;
import com.project.tickets.exceptions.TicketSoldOutException;
import com.project.tickets.exceptions.TicketTypeNotFoundException;
import com.project.tickets.exceptions.UserNotFoundException;
import com.project.tickets.repositories.TicketRepository;
import com.project.tickets.repositories.TicketTypeRepository;
import com.project.tickets.repositories.UserRepository;
import com.project.tickets.services.QrCodeService;
import com.project.tickets.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

	private final UserRepository userRepository;

	private final TicketTypeRepository ticketTypeRepository;

	private final TicketRepository ticketRepository;

	private final QrCodeService qrCodeService;

	@Override
	public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(String.format("User ID is '%s' is not found")));

		TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
			.orElseThrow(
					() -> new TicketTypeNotFoundException(String.format("Ticket Type with  Id  '%s' is not present")));

		int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

		int totalAvailable = ticketType.getTotalAvailable();

		if (purchasedTickets + 1 > totalAvailable) {
			throw new TicketSoldOutException();
		}

		Ticket ticket = new Ticket();
		ticket.setStatus(TicketStatusEnum.PUBLISHED);
		ticket.setTicketType(ticketType);
		ticket.setPurchaser(user);

		Ticket savedTicket = ticketRepository.save(ticket);

		// qrCodeService.generateQrcode(savedTicket);

		return ticketRepository.save(savedTicket);

	}

}
