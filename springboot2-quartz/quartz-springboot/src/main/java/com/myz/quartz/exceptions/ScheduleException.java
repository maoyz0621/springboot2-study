package com.myz.quartz.exceptions;

import com.dexcoder.commons.exceptions.DexcoderException;

/**
 * 自定义异常
 */
public class ScheduleException extends DexcoderException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1921648378954132894L;

    public ScheduleException(Throwable e) {
        super(e);
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String code, String message) {
        super(code, message);
    }
}
