package com.example.pokedex.Modelos_JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ability implements Serializable {

    @SerializedName("ability")
    @Expose
    private Ability__1 ability;
    @SerializedName("is_hidden")
    @Expose
    private Boolean isHidden;
    @SerializedName("slot")
    @Expose
    private Integer slot;

    /**
     * No args constructor for use in serialization
     *
     */
    public Ability() {
    }

    /**
     *
     * @param ability
     * @param slot
     * @param isHidden
     */
    public Ability(Ability__1 ability, Boolean isHidden, Integer slot) {
        super();
        this.ability = ability;
        this.isHidden = isHidden;
        this.slot = slot;
    }

    public Ability__1 getAbility() {
        return ability;
    }

    public void setAbility(Ability__1 ability) {
        this.ability = ability;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Ability.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("ability");
        sb.append('=');
        sb.append(((this.ability == null)?"<null>":this.ability));
        sb.append(',');
        sb.append("isHidden");
        sb.append('=');
        sb.append(((this.isHidden == null)?"<null>":this.isHidden));
        sb.append(',');
        sb.append("slot");
        sb.append('=');
        sb.append(((this.slot == null)?"<null>":this.slot));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.ability == null)? 0 :this.ability.hashCode()));
        result = ((result* 31)+((this.slot == null)? 0 :this.slot.hashCode()));
        result = ((result* 31)+((this.isHidden == null)? 0 :this.isHidden.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Ability) == false) {
            return false;
        }
        Ability rhs = ((Ability) other);
        return ((((this.ability == rhs.ability)||((this.ability!= null)&&this.ability.equals(rhs.ability)))&&((this.slot == rhs.slot)||((this.slot!= null)&&this.slot.equals(rhs.slot))))&&((this.isHidden == rhs.isHidden)||((this.isHidden!= null)&&this.isHidden.equals(rhs.isHidden))));
    }

}