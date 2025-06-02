package cz.jurina.tennisclub.tennisclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a tennis court at the club.
 *
 * Each court is associated with a specific surface type.
 * Courts support soft deletion.
 *
 * Mapped to the "courts" table in the database.
 *
 * @author Lukas Jurina
 */
@Entity
@Table(name = "courts")
@Data
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "court_surface_id", nullable = false)
    private CourtSurface courtSurface;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
