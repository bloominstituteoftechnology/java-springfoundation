package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the plants table.
 */
@Entity
@Table(name = "plants")
public class Plants
    extends Auditable
{
    /**
     * The primary key (long) of the plants table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plantid;

    /**
     * The nickname (String) of the plant. Cannot be null and must be unique.
     */
    @NotNull
    @Column(unique = true)
    private String nickname;

    /**
     * The species (String) of the plant. Cannot be null.
     */

    @NotNull
    private String species;

    /**
     * The Frequency of watering ex: "Monthly, Biweekly, weekly" (String) of the plant. Cannot be null.
     */

    @NotNull
    private String frequency;

    /**
     * The days of which watering is required "Monday - Sunday" (String) of the plant. Cannot be null.
     */

    @NotNull
    private String days;


    /**
     * The userid of the user assigned to this plant and is what is stored in the database.
     * This is the entire user object!
     * <p>
     * Forms a Many to One relationship between plants and users.
     * A user can have many plants.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "plants",
            allowSetters = true)
    private User user;


    public Plants()
    {
    }

    /**
     * Constructor for plants, new plants must have these values to create new plant object
     */

    public Plants(@NotNull String nickname, @NotNull String species, @NotNull String frequency, @NotNull String days, @NotNull User user) {
        this.nickname = nickname;
        this.species = species;
        this.frequency = frequency;
        this.days = days;
        this.user = user;
    }

    /**
     * Getters and Setters
     */

    public long getPlantid() {
        return plantid;
    }

    public void setPlantid(long plantid) {
        this.plantid = plantid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
