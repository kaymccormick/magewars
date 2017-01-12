package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* Created by kay on 5/14/2014. */
/**
 * Entity class for a spell-book.
 */
@Entity
public class SpellBook implements Serializable {
    @SequenceGenerator(name = "spellbook_spellbookid_seq", sequenceName = "spellbook_spellbookid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spellbook_spellbookid_seq")
    @Id
    private Integer spellbookId;

    @ManyToOne(fetch = FetchType.LAZY)
    private
    User createdByUser;

    private String spellbookName;

    @ManyToOne
    private Mage mage;

    @ElementCollection()
    @CollectionTable(name = "spellbookcard", joinColumns =
    @JoinColumn(name = "spellbook"))
    @Column(name = "copies")
    @MapKeyJoinColumn(name = "card", referencedColumnName = "cardid")
    private Map<Card, Integer> cards;

    /**
     * Create a default instance of a spell-book.
     */
    public SpellBook() {
        cards = new HashMap<>();
    }

    public Integer getSpellbookId() {
        return spellbookId;
    }

    public void setSpellbookId(Integer spellbookId) {
        this.spellbookId = spellbookId;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getSpellbookName() {
        return spellbookName;
    }

    public void setSpellbookName(String spellbookName) {
        this.spellbookName = spellbookName;
    }

    public Map<Card, Integer> getCards() {
        return cards;
    }

    public void setCards(Map<Card, Integer> cards) {
        this.cards = cards;
    }

    public Mage getMage() {
        return mage;
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }
}
