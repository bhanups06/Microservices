package com.bps.user.service.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private HttpStatus status;
    private boolean success;


}
