package com.trevortran.expensetracker.logger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
@Component
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    public RequestLoggingFilter(){
        setIncludeClientInfo(true);
        setIncludeHeaders(true);
        setMaxPayloadLength(1000);
        setIncludePayload(true);
        setIncludeQueryString(true);
        setBeforeMessagePrefix("Request started => ");
    }
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}
