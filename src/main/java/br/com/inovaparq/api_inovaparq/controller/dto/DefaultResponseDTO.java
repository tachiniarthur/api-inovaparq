package br.com.inovaparq.api_inovaparq.controller.dto;

public class DefaultResponseDTO<T> {
    private String message;
    private T data;

    public DefaultResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
