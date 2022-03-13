
package com.example.pokedex.Modelos_JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Other implements Serializable {

    @SerializedName("dream_world")
    @Expose
    private DreamWorld dreamWorld;
    @SerializedName("home")
    @Expose
    private Home home;
    @SerializedName("official-artwork")
    @Expose
    private OfficialArtwork officialArtwork;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Other() {
    }

    /**
     * 
     * @param officialArtwork
     * @param dreamWorld
     * @param home
     */
    public Other(DreamWorld dreamWorld, Home home, OfficialArtwork officialArtwork) {
        super();
        this.dreamWorld = dreamWorld;
        this.home = home;
        this.officialArtwork = officialArtwork;
    }

    public DreamWorld getDreamWorld() {
        return dreamWorld;
    }

    public void setDreamWorld(DreamWorld dreamWorld) {
        this.dreamWorld = dreamWorld;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }

    public void setOfficialArtwork(OfficialArtwork officialArtwork) {
        this.officialArtwork = officialArtwork;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Other.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dreamWorld");
        sb.append('=');
        sb.append(((this.dreamWorld == null)?"<null>":this.dreamWorld));
        sb.append(',');
        sb.append("home");
        sb.append('=');
        sb.append(((this.home == null)?"<null>":this.home));
        sb.append(',');
        sb.append("officialArtwork");
        sb.append('=');
        sb.append(((this.officialArtwork == null)?"<null>":this.officialArtwork));
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
        result = ((result* 31)+((this.officialArtwork == null)? 0 :this.officialArtwork.hashCode()));
        result = ((result* 31)+((this.dreamWorld == null)? 0 :this.dreamWorld.hashCode()));
        result = ((result* 31)+((this.home == null)? 0 :this.home.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Other) == false) {
            return false;
        }
        Other rhs = ((Other) other);
        return ((((this.officialArtwork == rhs.officialArtwork)||((this.officialArtwork!= null)&&this.officialArtwork.equals(rhs.officialArtwork)))&&((this.dreamWorld == rhs.dreamWorld)||((this.dreamWorld!= null)&&this.dreamWorld.equals(rhs.dreamWorld))))&&((this.home == rhs.home)||((this.home!= null)&&this.home.equals(rhs.home))));
    }

}
