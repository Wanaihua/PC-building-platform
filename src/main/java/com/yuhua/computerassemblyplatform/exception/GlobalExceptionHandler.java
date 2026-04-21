package com.yuhua.computerassemblyplatform.exception;

import com.yuhua.computerassemblyplatform.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException e) {
        return Result.error(e.getCode(),e.getMessage());
    }
}
