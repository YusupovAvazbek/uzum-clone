package uz.nt.uzumclone.enums;

public enum ImageQualityType {
    high("high"),
    medium("medium"),
    low("low");
    private String name;
    ImageQualityType(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
