package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperatorStatus {
    BEELINE("905"),
    MEGAFON("926"),
    TELE2("950"),
    MTS("910");

    @Getter
    private final String operatorCode;

}
