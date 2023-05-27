package com.hotel.app.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicResponse<T> {
    private int code;
    private String message;
    private T data;

}
