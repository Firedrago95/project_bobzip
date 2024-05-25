package project.bobzip.recipe.entity;

import lombok.Getter;

@Getter
public enum Unit {
    g("g"),
    kg("kg"),
    ml("ml"),
    L("L");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }
}
