package com.pao.laboratory03.bonus.exceptions;

import com.pao.laboratory03.bonus.enums.Status;

public class InvalidTransitionException extends RuntimeException {
    public InvalidTransitionException(Status fromStatus, Status toStatus) {
        super("InvalidTransitionException: Nu se poate trece de la " + fromStatus + " la " + toStatus + ".");
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
    }

    public Status getFromStatus() { return fromStatus; }
    public Status getToStatus() { return toStatus; }

    private final Status fromStatus;
    private final Status toStatus;
}
