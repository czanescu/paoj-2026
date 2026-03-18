package com.pao.laboratory03.bonus.enums;

public enum Status {
    TO_DO() {
        @Override
        public boolean canTransitionTo(Status status) {
            return status == IN_PROGRESS || status == CANCELLED;
        }
    },
    IN_PROGRESS() {
        @Override
        public boolean canTransitionTo(Status status) {
            return status == DONE || status == CANCELLED;
        }
    },
    DONE() {
        @Override
        public boolean canTransitionTo(Status status) {
            return false;
        }
    },
    CANCELLED() {
        @Override
        public boolean canTransitionTo(Status status) {
            return false;
        }
    };

    Status(){}

    public abstract boolean canTransitionTo(Status status);

}
