package com.pu.puzzleuniverse;

public class Puzzlelar {

    private String puzzle_category;
    private String puzzle_ad;

    public Puzzlelar() {
    }

    public Puzzlelar(String puzzle_category, String puzzle_ad) {
        this.puzzle_category = puzzle_category;
        this.puzzle_ad = puzzle_ad;
    }

    public String getPuzzle_category() {
        return puzzle_category;
    }

    public void setPuzzle_category(String puzzle_category) {
        this.puzzle_category = puzzle_category;
    }

    public String getPuzzle_ad() {
        return puzzle_ad;
    }

    public void setPuzzle_ad(String puzzle_ad) {
        this.puzzle_ad = puzzle_ad;
    }
}
