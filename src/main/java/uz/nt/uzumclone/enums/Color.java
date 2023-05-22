package uz.nt.uzumclone.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    WHITE("White"),
    YELLOW("Yellow"),
    BLACK("Black"),
    BLUE("Blue"),
    RED("Red"),
    BROWN("Brown"),
    GREEN("Green"),
    VIOLET("Violet");
    private String name;

}
