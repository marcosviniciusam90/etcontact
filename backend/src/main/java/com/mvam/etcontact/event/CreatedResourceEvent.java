package com.mvam.etcontact.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class CreatedResourceEvent extends ApplicationEvent {

    private final Long id;
    private final HttpServletResponse response;

    public CreatedResourceEvent(Object source, Long id, HttpServletResponse response) {
        super(source);
        this.id = id;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
