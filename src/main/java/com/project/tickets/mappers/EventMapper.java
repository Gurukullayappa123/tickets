package com.project.tickets.mappers;

import com.project.tickets.domain.CreateEventRequest;
import com.project.tickets.domain.CreateTicketTypeRequest;
import com.project.tickets.domain.UpdateEventRequest;
import com.project.tickets.domain.UpdateTicketTypeRequest;
import com.project.tickets.domain.dtos.*;
import com.project.tickets.domain.entities.Event;
import com.project.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

	CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto createTypeRequest);

	CreateEventRequest fromDto(CreateEventRequestDTo dto);

	CreateEventResponseDto toDto(Event event);

	ListEventTicketTypeResponseDto toDto(TicketType ticketType);

	ListEventResponseDto toListEventResponseDto(Event event);

	GetEventTicketTypeResponseDto toGetEventTicketTypeResponseDto(TicketType ticketType);

	GetEventDetailsResponseDto togetEventDetailsResponseDto(Event event);

	UpdateTicketTypeRequest fromDTo(UpdateTicketTypeRequestDto dto);

	UpdateEventRequest fromDto(UpdateEventRequestDTo dto);

	UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

	UpdateEventResponseDto toUpdateEventResponseDto(Event event);

	ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

	GetPublishedEventTicketTypeResponseDto toGetPublishedEventTicketTypeResponseDto(TicketType ticketType);

	GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);

}
