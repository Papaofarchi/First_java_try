package org.example.entity;

import lombok.Getter;

public enum OperatorStatus {
    BEELINE("905"),
    MEGAFON("926"),
    TELE2("950"),
    MTS("910");

    @Getter
    private final String operatorCode;

    OperatorStatus(String operatorCode) {
        this.operatorCode = operatorCode;
    }

}
