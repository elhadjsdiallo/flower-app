package com.example.flowerapp.types;

import lombok.Getter;

@Getter
public enum UserState {

    ENABLED(1),
    DISABLED(0),
    UNKNOWN(-1);

    private Integer value;

    /**
     * @param value
     */
    private UserState(Integer value) {
        this.value = value;
    }

    public static Integer fromInteger(UserState value) {

        switch (value) {
            case ENABLED:
                return 0;
            case DISABLED:
                return 1;

            default:
                return -1;

        }
    }

}
