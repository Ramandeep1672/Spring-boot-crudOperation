package com.techtez.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
	
	private LocalDateTime time;

    private int status;

    private String message;

    private String path;

    private T data;

}
