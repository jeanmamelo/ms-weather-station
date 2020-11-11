package com.portoseg.enums;


import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CategoryEnum {

    PARTY("party"), MOOD("mood"), CHILL("chill"), FOCUS("focus");

    private final String description;

    CategoryEnum(String getDescription) {
        this.description = getDescription;
    }

    public String getDescription() {
        return description;
    }

    public static String getName(CategoryEnum categoryName) {
        return Stream.of(values()).filter(categoryEnum -> categoryEnum.equals(categoryName))
                .map(CategoryEnum::getDescription)
                .collect(Collectors.joining());
    }
}
