package com.pu.puzzleuniverse;

public class PuzzlelarVT {



    private int puzzle_id;
    private String puzzle_category,puzzle_link,puzzle_satinal,piece9,piece25,piece100,piece225;

    public PuzzlelarVT() {
    }

    public PuzzlelarVT(int puzzle_id, String puzzle_category, String puzzle_link, String puzzle_satinal, String piece9, String piece25, String piece100, String piece225) {
        this.puzzle_id = puzzle_id;
        this.puzzle_category = puzzle_category;
        this.puzzle_link = puzzle_link;
        this.puzzle_satinal = puzzle_satinal;
        this.piece9 = piece9;
        this.piece25 = piece25;
        this.piece100 = piece100;
        this.piece225 = piece225;
    }

    public int getPuzzle_id() {
        return puzzle_id;
    }

    public void setPuzzle_id(int puzzle_id) {
        this.puzzle_id = puzzle_id;
    }

    public String getPuzzle_category() {
        return puzzle_category;
    }

    public void setPuzzle_category(String puzzle_category) {
        this.puzzle_category = puzzle_category;
    }

    public String getPuzzle_link() {
        return puzzle_link;
    }

    public void setPuzzle_link(String puzzle_link) {
        this.puzzle_link = puzzle_link;
    }

    public String getPuzzle_satinal() {
        return puzzle_satinal;
    }

    public void setPuzzle_satinal(String puzzle_satinal) {
        this.puzzle_satinal = puzzle_satinal;
    }

    public String getPiece9() {
        return piece9;
    }

    public void setPiece9(String piece9) {
        this.piece9 = piece9;
    }

    public String getPiece25() {
        return piece25;
    }

    public void setPiece25(String piece25) {
        this.piece25 = piece25;
    }

    public String getPiece100() {
        return piece100;
    }

    public void setPiece100(String piece100) {
        this.piece100 = piece100;
    }

    public String getPiece225() {
        return piece225;
    }

    public void setPiece225(String piece225) {
        this.piece225 = piece225;
    }
}
