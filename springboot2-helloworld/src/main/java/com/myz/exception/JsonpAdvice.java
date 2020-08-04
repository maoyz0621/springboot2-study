package com.myz.exception;



/**
 * @author maoyz on 18-4-8.
 */
// @ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpAdvice() {
        super("callback");
    }
}
