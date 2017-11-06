package com.future.jdiff;

import java.util.List;

public class DiffBlock {
    /**
     * jia start index
     */
    private int jiaStartIndex;

    /**
     * jia end index
     */
    private int jiaEndIndex;

    /**
     * yi start index
     */
    private int yiStartIndex;

    /**
     * yi end index
     */
    private int yiEndIndex;



    private Operation operation;

    private List<String> contextLines;


    public DiffBlock(int jiaStartIndex, int jiaEndIndex, int yiStartIndex, int yiEndIndex, Operation operation) {
        this.jiaStartIndex = jiaStartIndex;
        this.jiaEndIndex = jiaEndIndex;
        this.yiStartIndex = yiStartIndex;
        this.yiEndIndex = yiEndIndex;
        this.operation = operation;
    }

    public List<String> getContextLines() {
        return contextLines;
    }

    public void setContextLines(List<String> contextLines) {
        this.contextLines = contextLines;
    }

    public int getJiaStartIndex() {
        return jiaStartIndex;
    }

    public int getJiaEndIndex() {
        return jiaEndIndex;
    }

    public int getYiStartIndex() {
        return yiStartIndex;
    }

    public int getYiEndIndex() {
        return yiEndIndex;
    }

    public Operation getOperation() {
        return operation;
    }
}
