package co.pragma.powerup.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String identityDocument;
    private String phone;
    private Long idRole;
    private BigDecimal baseSalary;
}
