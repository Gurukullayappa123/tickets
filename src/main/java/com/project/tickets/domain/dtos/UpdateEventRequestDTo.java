package com.project.tickets.domain.dtos;

import com.project.tickets.domain.entities.EventStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDTo {

	@NotNull(message = "Event Id is Required")
	private UUID Id;

	@NotNull(message = "name is Required")
	private String name;

	private LocalDateTime start;

	private LocalDateTime end;

	@NotNull(message = "venue details are Required")
	private String venue;

	private LocalDateTime salesStart;

	private LocalDateTime salesEnd;

	@NotNull(message = "Event status is Required")
	private EventStatusEnum status;

	private List<UpdateTicketTypeRequestDto> ticketTypes;

}
