package com.example.flowerapp.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.flowerapp.response.CustomApiResponse;
import com.example.flowerapp.response.CustomBaseResponse;

@ControllerAdvice(annotations = CustomBaseResponse.class)
public class CustomResponseEntityHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
            //custom response for only json media
        if (selectedContentType.equals(MediaType.APPLICATION_JSON)) {
            if (body instanceof CustomApiResponse) {
                return body;
            }
            return new CustomApiResponse(HttpStatus.OK, body);
        }
        return body;
    }

}
