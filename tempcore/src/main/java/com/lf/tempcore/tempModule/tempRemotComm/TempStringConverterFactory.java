package com.lf.tempcore.tempModule.tempRemotComm;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * String converter factory
 * Created by Zhijun.Pu on 2016/1/11.
 */
public class TempStringConverterFactory extends Converter.Factory {
    private static TempStringConverterFactory instace = new TempStringConverterFactory();

    private TempStringConverterFactory() {
        super();
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        if(type.getClass().equals(String.class)) return new ToStringResponseConverter();
        return super.fromResponseBody(type, annotations);
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        if(type.getClass().equals(String.class)) return new ToStringRequestConverter();
        return super.toRequestBody(type, annotations);
    }

    public static TempStringConverterFactory create() {
        return instace;
    }

    public static class ToStringResponseConverter implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            if(value == null) return "";
            return value.toString();
        }
    }

    public static class ToStringRequestConverter implements Converter<String, RequestBody> {

        @Override
        public RequestBody convert(String value) throws IOException {
            return RequestBody.create(MediaType.parse("text/plain"), value);
        }

    }
}
