package org.fileserver;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.NoSuchFileException;

import static java.lang.String.format;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

@ControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandlers.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoSuchFileException.class)
    public void handleNoSuchFileException(HttpServletRequest request) {
        String requestPath = (String) request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        LOGGER.warn(format("File with path identifier [%s] could not be found!", requestPath));
    }
}
