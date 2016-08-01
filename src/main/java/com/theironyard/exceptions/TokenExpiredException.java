package com.theironyard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jeff on 7/29/16.
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Token Expired")
public class TokenExpiredException extends RuntimeException{
}
