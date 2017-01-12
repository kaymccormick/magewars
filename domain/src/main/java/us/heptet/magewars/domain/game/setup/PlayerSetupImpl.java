package us.heptet.magewars.domain.game.setup;

import us.heptet.magewars.domain.game.setup.PlayerSetup;

import java.util.HashMap;
import java.util.Map;

/* Created by jade on 20/09/2016. */

class PlayerSetupImpl implements PlayerSetup {
    private String mageCardEnumName;
    private Map<String, Integer> spellbookCardCount = new HashMap<>();
    private String userName;

    PlayerSetupImpl() {
        /* For serialization, and initialization */
    }

    @Override
    public void setMageCardEnumName(String mageCardEnumName) {
        this.mageCardEnumName = mageCardEnumName;
    }

    @Override
    public void setPlayerSpellbookCardCount(String cardEnumName, int count) {
        spellbookCardCount.put(cardEnumName, count);
    }

    @Override
    public boolean isSetupComplete() {
        return mageCardEnumName != null && !spellbookCardCount.isEmpty();
    }

    @Override
    public String getMageCardEnumName() {
        return mageCardEnumName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
