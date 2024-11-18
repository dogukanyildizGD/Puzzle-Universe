package com.pu.puzzleuniverse;

public class Achievements {

    private String rewardCount;
    private String achTitle;
    private String achContent;
    private String achWindowBg;

    public Achievements() {
    }

    public Achievements(String rewardCount, String achTitle, String achContent, String achWindowBg) {
        this.rewardCount = rewardCount;
        this.achTitle = achTitle;
        this.achContent = achContent;
        this.achWindowBg = achWindowBg;
    }

    public String getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(String rewardCount) {
        this.rewardCount = rewardCount;
    }

    public String getAchTitle() {
        return achTitle;
    }

    public void setAchTitle(String achTitle) {
        this.achTitle = achTitle;
    }

    public String getAchContent() {
        return achContent;
    }

    public void setAchContent(String achContent) {
        this.achContent = achContent;
    }

    public String getAchWindowBg() {
        return achWindowBg;
    }

    public void setAchWindowBg(String achWindowBg) {
        this.achWindowBg = achWindowBg;
    }
}
