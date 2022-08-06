package gcokun.tacocloud.taco;

import gcokun.tacocloud.authentication.UserInformation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class TacoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt = new Date();

    @ManyToOne
    private UserInformation user;

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

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Taco> tacoList = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacoList.add(taco);
    }
}
