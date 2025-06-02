package cz.jurina.tennisclub.tennisclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a customer who books tennis court reservations.
 *
 * Each customer has a name, surname, and a unique phone number. Soft deletion is supported.
 *
 * Mapped to the "customers" table in the database.
 *
 * @author Lukas Jurina
 */
@Entity
@Table(name = "customers")
@Data
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
