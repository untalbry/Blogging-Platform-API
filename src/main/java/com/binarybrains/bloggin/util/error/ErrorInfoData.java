package com.binarybrains.bloggin.util.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfoData {
    private List<ErrorInfo> errorsInfo;
}
