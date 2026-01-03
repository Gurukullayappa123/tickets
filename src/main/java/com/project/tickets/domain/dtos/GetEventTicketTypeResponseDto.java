package com.project.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventTicketTypeResponseDto {

	private String name;

	private String description;

	private double price;

	private Integer totalAvailable;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
