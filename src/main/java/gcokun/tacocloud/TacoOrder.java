package gcokun.tacocloud;

import gcokun.tacocloud.taco.Taco;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder {

    private Long id;

    private Date placedAt;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Apt name is required")
    @Digits(integer = 2, fraction = 0, message = "Should be less than 2 digits")
    private String deliveryApt;

    @NotBlank(message = "Street name is required")
    private String deliveryStreet;

    @NotBlank(message = "City name is required")
    private String deliveryCity;

    //TODO:Disabled for testing
    //@CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacoList = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacoList.add(taco);
    }
}
