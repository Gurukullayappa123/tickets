package com.project.tickets.services;

import com.project.tickets.domain.CreateEventRequest;
import com.project.tickets.domain.UpdateEventRequest;
import com.project.tickets.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {

	Event createEvent(UUID organizerId, CreateEventRequest event);

	Page<Event> getEventsByOrganizer(UUID organizerId, Pageable pageable);

	Optional<Event> getEventByIdAndOrganizer(UUID organizerId, UUID Id);

	Event updateEventForOrganizer(UUID organizerId, UUID ID, UpdateEventRequest updateEventRequest);

	void deleteEventForOrganizer(UUID organizerId, UUID eventId);

	Page<Event> listPublishEvents(Pageable pageable);

	Page<Event> searchEvnets(String searchItem, Pageable pageable);

	Optional<Event> getPublishedEvent(UUID eventId);

}
