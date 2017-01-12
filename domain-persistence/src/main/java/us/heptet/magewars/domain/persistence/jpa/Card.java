package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;

/* Created by kay on 5/13/2014. */
/**
 * Entity class for a card.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "cardenumname")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="disc", discriminatorType = DiscriminatorType.STRING)
public abstract class Card implements Serializable {
    @SequenceGenerator(name="card_cardid_seq", sequenceName = "card_cardid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_cardid_seq")
    @Id
    private int cardId;

    @Column(name = "cardenumname", nullable = false)
    private String cardEnumName;

    private String cardName;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardEnumName() {
        return cardEnumName;
    }

    public void setCardEnumName(String cardEnumName) {
        this.cardEnumName = cardEnumName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public String
    toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", cardEnumName='" + cardEnumName + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }
}
