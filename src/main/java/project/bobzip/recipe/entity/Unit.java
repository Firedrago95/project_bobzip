package project.bobzip.recipe.entity;

import lombok.Getter;

@Getter
public enum Unit {
    g("g"),
    ml("ml"),
    kg("kg"),
    L("L");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }
}
