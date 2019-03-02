package com.dscjss.lineup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Settings were not found.")
public class SettingsNotFoundException extends Exception{
}
