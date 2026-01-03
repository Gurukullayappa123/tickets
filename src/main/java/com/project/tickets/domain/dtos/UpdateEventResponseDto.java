package com.project.tickets.domain.dtos;

import com.project.tickets.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventResponseDto {

	private UUID id;

	private String name;

	private String venue;

	private LocalDateTime start;

	private LocalDateTime end;

	private LocalDateTime salesStart;

	private LocalDateTime salesEnd;

	private EventStatusEnum status;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private List<UpdateTicketTypeResponseDto> ticketTypes;

}
