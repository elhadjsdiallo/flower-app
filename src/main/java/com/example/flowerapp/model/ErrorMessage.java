package com.example.flowerapp.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {

    private Integer status;
    private String message;

    private String reason;

    private LocalDateTime creationTime = LocalDateTime.now();

    /**
     * @param status
     * @param message
     * @param reason
     */
    public ErrorMessage(Integer status, String message, String reason) {
        this.status = status;
        this.message = message;
        this.reason = reason;
    }

}
