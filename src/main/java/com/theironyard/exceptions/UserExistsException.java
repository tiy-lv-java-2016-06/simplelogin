package com.theironyard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jeff on 8/1/16.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class UserExistsException extends RuntimeException{
}
