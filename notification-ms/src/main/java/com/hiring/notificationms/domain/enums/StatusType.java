package com.hiring.notificationms.domain.enums;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum StatusType {
    APPLIED,
    INTERVIEWED,
    OFFERED,
    ONBOARDED;

    private static final Map<StatusType, Set<StatusType>> validTransitions = Map.of(
            APPLIED, EnumSet.of(INTERVIEWED),
            INTERVIEWED, EnumSet.of(OFFERED),
            OFFERED, EnumSet.of(ONBOARDED),
            ONBOARDED, EnumSet.noneOf(StatusType.class)
    );

    public boolean canTransitionTo(StatusType nextStatus) {
        return validTransitions.getOrDefault(this, EnumSet.noneOf(StatusType.class)).contains(nextStatus);
    }
}
