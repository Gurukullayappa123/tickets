package com.project.tickets.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDto {

	@NotNull(message = "name type is Required")
	private String name;

	@NotNull(message = "price type is Required")
	private Double price;

	@PositiveOrZero(message = "Price must be zero or positive")
	private String description;

	private Integer totalAvailable;

}
