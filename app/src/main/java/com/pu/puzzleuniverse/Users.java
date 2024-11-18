package com.pu.puzzleuniverse;

public class Users {

    private String userKey,personName,personEmail,personId,gemCount,fireBaseEklenme,satinAlmaDurumu;

    public Users() {
    }

    public Users(String userKey, String personName, String personEmail, String personId, String gemCount, String fireBaseEklenme, String satinAlmaDurumu) {
        this.userKey = userKey;
        this.personName = personName;
        this.personEmail = personEmail;
        this.personId = personId;
        this.gemCount = gemCount;
        this.fireBaseEklenme = fireBaseEklenme;
        this.satinAlmaDurumu = satinAlmaDurumu;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getGemCount() {
        return gemCount;
    }

    public void setGemCount(String gemCount) {
        this.gemCount = gemCount;
    }

    public String getFireBaseEklenme() {
        return fireBaseEklenme;
    }

    public void setFireBaseEklenme(String fireBaseEklenme) {
        this.fireBaseEklenme = fireBaseEklenme;
    }

    public String getSatinAlmaDurumu() {
        return satinAlmaDurumu;
    }

    public void setSatinAlmaDurumu(String satinAlmaDurumu) {
        this.satinAlmaDurumu = satinAlmaDurumu;
    }
}
