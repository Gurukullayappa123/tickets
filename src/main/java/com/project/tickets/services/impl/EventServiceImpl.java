package com.project.tickets.services.impl;

import com.project.tickets.domain.CreateEventRequest;
import com.project.tickets.domain.UpdateEventRequest;
import com.project.tickets.domain.UpdateTicketTypeRequest;
import com.project.tickets.domain.entities.Event;
import com.project.tickets.domain.entities.EventStatusEnum;
import com.project.tickets.domain.entities.TicketType;
import com.project.tickets.domain.entities.User;
import com.project.tickets.exceptions.EventNotFoundException;
import com.project.tickets.exceptions.EventUpdateException;
import com.project.tickets.exceptions.UserNotFoundException;
import com.project.tickets.repositories.EventRepository;
import com.project.tickets.repositories.UserRepository;
import com.project.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final UserRepository userRepository;

	private final EventRepository eventRepository;

	@Override
	public Event createEvent(UUID organizerId, CreateEventRequest event) {
		User organizer = null;
		if (organizerId == null) {
			throw new UserNotFoundException("Organizer ID cannot be null");
		}
		else {
			organizer = userRepository.findById(organizerId)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("Organizer with ID '%s' not found", organizerId)));
		}

		List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(ticketType -> {
			TicketType newTicketType = new TicketType();
			newTicketType.setName(ticketType.getName());
			newTicketType.setPrice(ticketType.getPrice());
			newTicketType.setDescription(ticketType.getDescription());
			newTicketType.setTotalAvailable(ticketType.getTotalAvailable());
			return newTicketType;
		}).toList();

		Event newEvent = new Event();
		newEvent.setName(event.getName());
		newEvent.setVenue(event.getVenue());
		newEvent.setStart(event.getStart());
		newEvent.setEnd(event.getEnd());
		newEvent.setSalesStart(event.getSalesStart());
		newEvent.setSalesEnd(event.getSalesEnd());
		newEvent.setStatus(event.getStatus());

		newEvent.setOrganizer(organizer);
		newEvent.setTicketTypes(ticketTypesToCreate);

		return eventRepository.save(newEvent);

	}

	@Override
	public Page<Event> getEventsByOrganizer(UUID organizerId, Pageable pageable) {
		return eventRepository.findByOrganizerId(organizerId, pageable);

	}

	@Override
	public Optional<Event> getEventByIdAndOrganizer(UUID organizerId, UUID Id) {
		return eventRepository.findByIdAndOrganizerId(Id, organizerId);
	}

	@Override
	@Transactional
	public Event updateEventForOrganizer(UUID organizerId, UUID ID, UpdateEventRequest event) {

		if (event.getId() == null) {
			throw new EventUpdateException("Event Id cannot be null");
		}
		if (!ID.equals(event.getId())) {
			throw new EventUpdateException("Path ID and Event ID do not match");
		}
		Event existingEvent = eventRepository.findByIdAndOrganizerId(ID, organizerId)
			.orElseThrow(() -> new EventNotFoundException(String.format("Eent with '%s' does not exist")));

		existingEvent.setName(event.getName());
		existingEvent.setVenue(event.getVenue());
		existingEvent.setStart(event.getStart());
		existingEvent.setEnd(event.getEnd());
		existingEvent.setSalesStart(event.getSalesStart());
		existingEvent.setSalesEnd(event.getSalesEnd());
		existingEvent.setStatus(event.getStatus());

		Set<UUID> requestTicketTypeIds = event.getTicketTypes()
			.stream()
			.map(UpdateTicketTypeRequest::getId)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());

		existingEvent.getTicketTypes().removeIf(ticketType -> !requestTicketTypeIds.contains(ticketType.getId()));

		Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes()
			.stream()
			.collect(Collectors.toMap(TicketType::getId, Function.identity()));

		for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
			if (ticketType.getId() != null && existingTicketTypesIndex.containsKey(ticketType.getId())) {
				TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
				existingTicketType.setName(ticketType.getName());
				existingTicketType.setPrice(ticketType.getPrice());
				existingTicketType.setDescription(ticketType.getDescription());
				existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
			}
			else if (ticketType.getId() == null) {
				TicketType newTicketType = new TicketType();
				newTicketType.setName(ticketType.getName());
				newTicketType.setPrice(ticketType.getPrice());
				newTicketType.setDescription(ticketType.getDescription());
				newTicketType.setTotalAvailable(ticketType.getTotalAvailable());
				existingEvent.getTicketTypes().add(newTicketType);
			}
			else {
				throw new EventUpdateException("Ticket Type ID cannot be null for existing ticket types");
			}

		}
		return eventRepository.save(existingEvent);
	}

	@Override
	public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
		getEventByIdAndOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);
	}

	@Override
	public Page<Event> listPublishEvents(Pageable pageable) {
		return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
	}

	@Override
	public Page<Event> searchEvnets(String searchItem, Pageable pageable) {

		return eventRepository.searchEvents(searchItem, pageable);

	}

	@Override
	public Optional<Event> getPublishedEvent(UUID eventId) {
		return eventRepository.findByIdAndOrganizerId(eventId, null)
			.filter(event -> event.getStatus() == EventStatusEnum.PUBLISHED);
	}

}
