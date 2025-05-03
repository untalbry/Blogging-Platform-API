package com.binarybrains.blogging.util.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;
@Slf4j
@Component
@Getter
public class ErrorInfoGlobalMapper {
    private ErrorInfoData errorInfoData;
    private ErrorInfo rn001;
    private ErrorInfo rn002;
    private ErrorInfo rn003;
    @PostConstruct
    public void init() {
        this.errorInfoData = load("global-errorinfo.json")
                .orElseThrow(() -> new IllegalStateException("No se pudo cargar el archivo global-errorinfo.json"));

        if (errorInfoData.getErrorsInfo().isEmpty()) {
            throw new IllegalStateException("El archivo global-errorinfo.json no contiene errores definidos.");
        }

        this.rn001 = errorInfoData.getErrorsInfo().get(0);
        this.rn002 = errorInfoData.getErrorsInfo().get(1);
        this.rn003 = errorInfoData.getErrorsInfo().get(2);
    }
    public static<T> ErrorInfo constrainsToError(ConstraintViolation<T> ve){
        return ErrorInfo.builder()
                .type(ErrorType.FIELD)
                .code(ve.getMessage())
                .message(ve.getMessage())
                .ruta(ve.getPropertyPath().toString())
                .build();
    }
    public Optional<ErrorInfoData> load(String file){
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        try{
            log.info("Loading {}", file);
            return Optional.of(mapper.readValue(input, ErrorInfoData.class));
        }catch (Exception e){
            log.error("Cannot read");
            return Optional.empty();
        }
    }
}
