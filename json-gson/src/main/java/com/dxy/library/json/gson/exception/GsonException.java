package com.dxy.library.json.gson.exception;

import com.dxy.library.exception.FormativeException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duanxinyuan
 * 2019/4/10 22:35
 */
@Setter
@Getter
public class GsonException extends FormativeException {
    public GsonException() {
        super();
    }

    public GsonException(String message) {
        super(message);
    }

    public GsonException(Throwable cause) {
        super(cause);
    }

    public GsonException(String format, Object... arguments) {
        super(format, arguments);
    }
}
