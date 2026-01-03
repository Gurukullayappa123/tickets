package com.project.tickets.controllers;

import com.project.tickets.domain.CreateEventRequest;
import com.project.tickets.domain.UpdateEventRequest;
import com.project.tickets.domain.dtos.*;
import com.project.tickets.domain.entities.Event;
import com.project.tickets.mappers.EventMapper;
import com.project.tickets.services.EventService;
import com.project.tickets.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static com.project.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {

	private EventMapper eventMapper;

	private EventService eventService;

	@PostMapping
	public ResponseEntity<CreateEventResponseDto> createEvent(@AuthenticationPrincipal Jwt jwt,
			@Valid @RequestBody CreateEventRequestDTo createEventRequestDTo) {

		CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDTo);
		UUID userId = parseUserId(jwt);

		Event CreatedEvent = eventService.createEvent(userId, createEventRequest);

		CreateEventResponseDto dto = eventMapper.toDto(CreatedEvent);

		return new ResponseEntity<>(dto, HttpStatus.CREATED);

	}

	@PutMapping(path = "/{eventId}")
	public ResponseEntity<UpdateEventResponseDto> updateEvent(@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID eventId, @Valid @RequestBody UpdateEventRequestDTo updateEventRequestDTo) {

		UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDTo);

		UUID userId = parseUserId(jwt);

		Event updatedEvent = eventService.updateEventForOrganizer(userId, eventId, updateEventRequest);

		UpdateEventResponseDto dto = eventMapper.toUpdateEventResponseDto(updatedEvent);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<Page<ListEventResponseDto>> getEventsByOrganizer(@AuthenticationPrincipal Jwt jwt,
			Pageable pageable) {

		UUID userId = parseUserId(jwt);
		Page<Event> events = eventService.getEventsByOrganizer(userId, pageable);
		Page<ListEventResponseDto> EventResponses = events.map(eventMapper::toListEventResponseDto);

		return ResponseEntity.ok(EventResponses);

	}

	@GetMapping("/{eventId}")
	public ResponseEntity<GetEventDetailsResponseDto> getEventDetails(@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID eventId) {
		UUID userId = parseUserId(jwt);
		{
			return eventService.getEventByIdAndOrganizer(userId, eventId)
				.map(eventMapper::togetEventDetailsResponseDto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());

		}
	}

	@DeleteMapping(path = "/{eventId}")
	public ResponseEntity<String> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventID) {
		UUID userID = parseUserId(jwt);
		eventService.deleteEventForOrganizer(userID, eventID);
		return ResponseEntity.ok("Event deleted successfully");
	}

}
