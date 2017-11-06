package com.future.jdiff;

public class MatchedLines {
    /**
     * file jia start index
     */
    private int jiaStartIndex;
    /**
     * File yi start index
     */
    private int yiStartIndex;

    /**
     * how many line are matched
     */
    private int lines;


    public MatchedLines(int jiaStartIndex, int yiStartIndex, int lines) {
        this.jiaStartIndex = jiaStartIndex;
        this.yiStartIndex = yiStartIndex;
        this.lines = lines;
    }

    public int getJiaStartIndex() {
        return jiaStartIndex;
    }

    public int getYiStartIndex() {
        return yiStartIndex;
    }

    public int getLines() {
        return lines;
    }

    public void setJiaStartIndex(int jiaStartIndex) {
        this.jiaStartIndex = jiaStartIndex;
    }

    public void setYiStartIndex(int yiStartIndex) {
        this.yiStartIndex = yiStartIndex;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getNextStartofJia() {
        return jiaStartIndex + lines;
    }

    public int getNextStartofYi() {
        return yiStartIndex + lines;
    }
}
