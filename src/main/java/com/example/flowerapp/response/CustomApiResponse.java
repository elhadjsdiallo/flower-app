package com.example.flowerapp.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomApiResponse {

    private Integer status;

    private Object data;

    private String error;

    private String reason;

    private LocalDateTime date=LocalDateTime.now();

    /**
     * @param status
     * @param body
     */
    public CustomApiResponse(HttpStatus  status, Object data) {
        this.status = status.value();
        this.data = data;
    }

    /**
     * @param status
     * @param body
     * @param error
     * @param reason
     */
    public CustomApiResponse(HttpStatus status, String error, String reason) {
        this.status = status.value();
        this.error = error;
        this.reason = reason;
    }



    
}
