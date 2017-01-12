package us.heptet.magewars.domain.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.persistence.exceptions.CardNotFoundInRepositoryException;
import us.heptet.magewars.domain.persistence.repository.CardRepository;
import us.heptet.magewars.domain.persistence.repository.MageRepository;
import us.heptet.magewars.domain.persistence.repository.SpellBookRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* Created by jade on 25/07/2016. */

/**
 * Database initializaton routine for transient in-memeory databases.
 */
public class DatabaseInitializationServiceImpl implements DatabaseInitializationService {
    private static Logger logger = LoggerFactory.getLogger(DatabaseInitializationServiceImpl.class);

    private CardSet cardSet;
    private CardRepository cardRepository;

    private AvailableMages availableMages;

    private MageRepository mageRepository;
    private SpellBookRepository spellBookRepository;

    /***
     * Construct the DatabaseInitializationService implementation.
     * @param cardSet Card set
     * @param cardRepository Card repository
     * @param mageRepository Mage repository
     * @param spellBookRepository Spell book repository
     * @param availableMages Available mages
     */
    @Inject
    public DatabaseInitializationServiceImpl(CardSet cardSet, CardRepository cardRepository, MageRepository mageRepository, SpellBookRepository spellBookRepository, AvailableMages availableMages) {
        this.cardSet = cardSet;
        this.cardRepository = cardRepository;
        this.mageRepository = mageRepository;
        this.spellBookRepository = spellBookRepository;
        this.availableMages = availableMages;
    }

    //@PostConstruct

    /**
     * Perform initial population of the database. Useful for transient, in-memory databases such as HSQL.
     */
    public void initialPopulation() {
        assert cardSet != null;
        try {
            populateCards();
            populateDefaultSpellbooks();
        } catch(DatabaseAlreadyInitializedException ex)
        {
            logger.warn("Database already initialized: {}", ex.getCause(), ex);
        }
    }

    private void populateCards()
    {
        Collection<Card> cards = cardSet.getCards();
        assert cards != null;
        for(Card card: cards)
        {
            processCard(card);
        }
    }

    private void processCard(Card card) {
        us.heptet.magewars.domain.persistence.jpa.Card pCard;
        pCard = cardRepository.findOneByCardEnumName(card.getCardEnum().name());
        if(pCard != null)
        {
            throw new DatabaseAlreadyInitializedException(new Throwable("Card " + card.getCardName() + " already exists in database."));
        }

        if(card instanceof Creature)
        {
            us.heptet.magewars.domain.persistence.jpa.Creature pCreature = new us.heptet.magewars.domain.persistence.jpa.Creature();
            pCard = pCreature;
        }
        else if(card instanceof Enchantment)
        {
            us.heptet.magewars.domain.persistence.jpa.Enchantment pEnchantment = new us.heptet.magewars.domain.persistence.jpa.Enchantment();
            pCard = pEnchantment;
        } else if(card instanceof Equipment)
        {
            us.heptet.magewars.domain.persistence.jpa.Equipment pEquipment = new us.heptet.magewars.domain.persistence.jpa.Equipment();
            pCard = pEquipment;
        } else if(card instanceof Incantation)
        {
            us.heptet.magewars.domain.persistence.jpa.Incantation pIncantation = new us.heptet.magewars.domain.persistence.jpa.Incantation();
            pCard = pIncantation;

        } else if(card instanceof Conjuration)
        {
            us.heptet.magewars.domain.persistence.jpa.Conjuration pConjuration= new us.heptet.magewars.domain.persistence.jpa.Conjuration();
            pCard = pConjuration;

        }else if(card instanceof AttackSpell)
        {
            us.heptet.magewars.domain.persistence.jpa.AttackSpell pAttack = new us.heptet.magewars.domain.persistence.jpa.AttackSpell();
            pCard = pAttack;

        }
        if(pCard != null) {
            pCard.setCardEnumName(card.getCardEnum().name());
            pCard.setCardName(card.getCardName());
            cardRepository.save(pCard);
        }
    }

    /* This creates mages also */
    private void populateDefaultSpellbooks()
    {
        Set<Mage> mageSet = availableMages.getAvailableMageSet();
        for(Mage mage:mageSet) {
            SpellBookDefinition spellBookDefinition = mage.getSpellBookDefinition();
            us.heptet.magewars.domain.persistence.jpa.Mage pMage = createMage(mage);

            us.heptet.magewars.domain.persistence.jpa.SpellBook pSpellbook = new us.heptet.magewars.domain.persistence.jpa.SpellBook();
            pSpellbook.setSpellbookName("Default " + mage.getName());
            pSpellbook.setMage(pMage);
            for(Map.Entry<CardEnum, Integer> spellbookEntry:spellBookDefinition.getSpellbookMap().entrySet()) {
                us.heptet.magewars.domain.persistence.jpa.Card pCard =
                        cardRepository.findOneByCardEnumName(spellbookEntry.getKey().name());
                if(pCard == null)
                {
                    throw new CardNotFoundInRepositoryException(spellbookEntry.getKey().name());
                }

                pSpellbook.getCards().put(pCard, spellbookEntry.getValue());
            }

            spellBookRepository.save(pSpellbook);
            pMage.setDefaultSpellbook(pSpellbook);
            mageRepository.save(pMage);
        }

    }

    private us.heptet.magewars.domain.persistence.jpa.Mage createMage(Mage mage) {
        us.heptet.magewars.domain.persistence.jpa.Mage pMage = new us.heptet.magewars.domain.persistence.jpa.Mage();
        pMage.setCardName(mage.getName());
        pMage.setCardEnumName(mage.getCardEnum().name());

        mageRepository.save(pMage);
        return pMage;
    }

}
