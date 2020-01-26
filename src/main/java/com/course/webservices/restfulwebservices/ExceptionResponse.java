package com.course.webservices.restfulwebservices;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

    public Date getTimestamp() {
        return timestamp;
    }

    public ExceptionResponse setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ExceptionResponse setDetails(String details) {
        this.details = details;
        return this;
    }
}
