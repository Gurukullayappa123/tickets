package com.project.tickets.controllers;

import com.project.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.project.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.project.tickets.domain.entities.Event;
import com.project.tickets.mappers.EventMapper;
import com.project.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

	@Autowired
	private final EventService eventService;

	private final EventMapper eventMapper;

	@GetMapping
	public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
			@RequestParam(required = false) String Query, Pageable pageable) {
		Page<Event> events;
		if (Query != null && !Query.isEmpty()) {
			events = eventService.searchEvnets(Query, pageable);
		}
		else {
			events = eventService.listPublishEvents(pageable);
		}

		return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
	}

	@GetMapping(path = "/{eventId}")
	public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvents(@PathVariable UUID eventId) {

		return eventService.getPublishedEvent(eventId)
			.map(eventMapper::toGetPublishedEventDetailsResponseDto)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

}
