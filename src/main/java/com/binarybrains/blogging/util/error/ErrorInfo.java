package com.binarybrains.blogging.util.error;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInfo {
    private String code;
    private String message;
    private String ruta;
    private ErrorType type;
}
