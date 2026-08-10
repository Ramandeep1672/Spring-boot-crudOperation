package com.techtez.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class ErrorDetails {
	
	private LocalDateTime time;
	private String msg;
	private int status;
	private String path;
	 private List<String> errors;

}
