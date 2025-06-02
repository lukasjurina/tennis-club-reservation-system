package cz.jurina.tennisclub.tennisclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity representing a reservation of a tennis court by a customer.
 *
 * A reservation includes information about the customer, the court, the time range,
 * pricing, and whether it is a singles or doubles game. Soft delete is supported.
 *
 * Automatically sets the creation date before persisting.
 *
 * Mapped to the "reservations" table in the database.
 *
 * @author Lukas Jurina
 */
@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id",  nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "is_double", nullable = false)
    private boolean isDouble;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "reservation_price", nullable = false)
    private Double reservationPrice;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }
}
