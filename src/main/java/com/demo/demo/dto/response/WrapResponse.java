package com.demo.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WrapResponse<T> {

    private boolean isSuccess;
    private int status;
    private String message;
    private T data;

    public WrapResponse(boolean isSuccess, int status, String message) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.message = message;
    }

    public static <T> WrapResponse<T> success(int status, String message, T data) {
        return new WrapResponse<>(true, status, message, data);
    }

    public static <T> WrapResponse<T> failure(int status, String message) {
        return new WrapResponse<>(false, status, message, null);
    }

}
