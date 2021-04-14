package com.eltosheva.sporthouse.utils;

import java.io.Serializable;

public class ResponseMsg implements Serializable {
    private Boolean valid;

    public ResponseMsg(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

    public ResponseMsg setValid(Boolean valid) {
        this.valid = valid;
        return this;
    }
}
