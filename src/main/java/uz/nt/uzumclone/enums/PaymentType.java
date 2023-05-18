package uz.nt.uzumclone.enums;
public enum PaymentType {
    UZCARD("UZCARD"),
    HUMO("HUMO");
    private String name;
    PaymentType(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
