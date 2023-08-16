package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@NamedQuery(name = "InstantEntity.findBetween", query = "SELECT i from InstantEntity i where i.dateValue between :from and :to")
@NamedQuery(name = "InstantEntity.deleteAll", query = "DELETE FROM InstantEntity i")
@NamedQuery(name = "InstantEntity.updateDateValue2NotWorking",
        query = "UPDATE  InstantEntity i SET i.dateValue2 = :date WHERE i.dateValue < :date")
@NamedQuery(name = "InstantEntity.updateDateValue2Working",
        query = "UPDATE  InstantEntity i SET i.dateValue2 = :dateValue2 WHERE i.dateValue < :dateValue")
@Entity
@Table(name = "INSTANT_ENTITY")
public class InstantEntity {
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    Instant dateValue;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    Instant dateValue2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public InstantEntity() {
    }

    public void setDateValue(Instant dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "InstantEntity{" +
                "dateValue=" + dateValue +
                ", dateValue2=" + dateValue2 +
                ", id=" + id +
                '}';
    }
}
