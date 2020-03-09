package com.dxy.library.json.fastjson.exception;

import com.dxy.library.exception.FormativeException;

/**
 * @author duanxinyuan
 * 2019/4/10 22:35
 */
public class FastjsonException extends FormativeException {
    public FastjsonException() {
        super();
    }

    public FastjsonException(String message) {
        super(message);
    }

    public FastjsonException(Throwable cause) {
        super(cause);
    }

    public FastjsonException(String format, Object... arguments) {
        super(format, arguments);
    }
}
