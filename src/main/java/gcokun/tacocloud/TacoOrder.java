package gcokun.tacocloud;

import gcokun.tacocloud.taco.Taco;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder {
    private String deliveryName;
    private String deliveryApt;
    private String deliveryStreet;
    private String deliveryCity;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Taco> tacoList = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacoList.add(taco);
    }
}
