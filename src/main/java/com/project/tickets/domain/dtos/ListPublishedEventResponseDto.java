package com.project.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPublishedEventResponseDto {

	private UUID id;

	private String name;

	private String venue;

	private String start;

	private String end;

}
