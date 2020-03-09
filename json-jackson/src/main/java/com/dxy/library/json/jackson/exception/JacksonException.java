package com.dxy.library.json.jackson.exception;

import com.dxy.library.exception.FormativeException;

/**
 * @author duanxinyuan
 * 2019/4/10 22:35
 */
public class JacksonException extends FormativeException {
    public JacksonException() {
        super();
    }

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(Throwable cause) {
        super(cause);
    }

    public JacksonException(String format, Object... arguments) {
        super(format, arguments);
    }
}
