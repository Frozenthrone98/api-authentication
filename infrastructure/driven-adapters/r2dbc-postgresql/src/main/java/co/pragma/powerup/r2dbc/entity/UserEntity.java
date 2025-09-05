package co.pragma.powerup.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    private String name;
    @Column(value = "last_name")
    private String lastName;
    @Column(value = "email")
    private String email;
    @Column(value = "identity_document")
    private String identityDocument;
    private String phone;
    @Column(value = "id_role")
    private Long idRole;
    @Column(value = "base_salary")
    private BigDecimal baseSalary;
}
