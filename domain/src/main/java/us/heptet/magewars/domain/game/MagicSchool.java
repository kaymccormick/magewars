package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/26/14. */
/**
 * Class representing a school of magic.
 */
public class MagicSchool implements Serializable {
    /***
     * Schools available.
     */
    public enum School {
        DARK, HOLY, NATURE, MIND, ARCANE, WAR,
        EARTH, WATER, AIR, FIRE
    }
    private Integer level = null;
    private Boolean allSchools = null;
    private School school;
    private MagicSchool nextMagicSchool;

    public static final MagicSchool DARK = new MagicSchool(School.DARK);
    public static final MagicSchool HOLY= new MagicSchool(School.DARK);
    public static final MagicSchool NATURE = new MagicSchool(School.DARK);
    public static final MagicSchool MIND = new MagicSchool(School.DARK);
    public static final MagicSchool ARCANE = new MagicSchool(School.DARK);
    public static final MagicSchool WAR = new MagicSchool(School.DARK);
    public static final MagicSchool EARTH = new MagicSchool(School.DARK);
    public static final MagicSchool WATER = new MagicSchool(School.DARK);
    public static final MagicSchool AIR = new MagicSchool(School.DARK);
    public static final MagicSchool FIRE = new MagicSchool(School.DARK);

    public MagicSchool() {
        /* Empty constructor for serializable type */
    }

    /***
     * Create an instance with the specified school.
     * @param school School.
     */
    public MagicSchool(School school) {
        this.school = school;
    }

    /***
     * Create an instance with the specified school and level..
     * @param school School.
     * @param level Level.
     */
    public MagicSchool(School school, int level)
    {
        this.school = school;
        this.level = level;
    }

    /***
     * Fluent API method to set the level of the instance.
     * @param level Level.
     * @return Itself.
     */
    public MagicSchool level(int level)
    {
        return new MagicSchool(school, level);
    }

    /***
     * Specify that we should chain to the supplied magic school and that our chain operator should be that of
     * "AND", instead of OR. Changing the operator in successive schools has no effect beyond the first.
     * @param nextMagicSchool The next magic school to chain to.
     * @return The existing instance, not the supplied instance.
     */
    public MagicSchool and(MagicSchool nextMagicSchool)
    {
        this.nextMagicSchool = nextMagicSchool;
        this.allSchools = true;
        return this;
    }

    /***
     * Specify that we should chain to the supplied magic school and that our chain operator should be that of
     * "OR", instead of AND. Changing the operator in successive schools has no effect beyond the first.
     * @param nextMagicSchool The next magic school to chain to.
     * @return The existing instance, not the supplied instance.
     */
    public MagicSchool or(MagicSchool nextMagicSchool)
    {
        this.nextMagicSchool = nextMagicSchool;
        this.allSchools = false;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getAllSchools() {
        return allSchools;
    }

    public void setAllSchools(Boolean allSchools) {
        this.allSchools = allSchools;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public MagicSchool getNextMagicSchool() {
        return nextMagicSchool;
    }

    public void setNextMagicSchool(MagicSchool nextMagicSchool) {
        this.nextMagicSchool = nextMagicSchool;
    }
}
