package com.future.jdiff;

import java.util.List;

public class OperationDetail {


    private Operation op;
    private int jiaDiffStart;
    private int jiaDiffEnd;
    private int yiDiffStart;
    private int yiDiffEnd;

    private List<String> contextLines;


    public OperationDetail(Operation op, int jiaDiffStart, int jiaDiffEnd, int yiDiffStart, int yiDiffEnd, List<String> lines) {
        this.op = op;
        this.jiaDiffStart = jiaDiffStart;
        this.jiaDiffEnd = jiaDiffEnd;
        this.yiDiffStart = yiDiffStart;
        this.yiDiffEnd = yiDiffEnd;
        this.contextLines = lines;
    }

    public List<String> getContextLines() {
        return contextLines;
    }

    public void setContextLines(List<String> contextLines) {
        this.contextLines = contextLines;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public int getJiaDiffStart() {
        return jiaDiffStart;
    }

    public void setJiaDiffStart(int jiaDiffStart) {
        this.jiaDiffStart = jiaDiffStart;
    }

    public int getJiaDiffEnd() {
        return jiaDiffEnd;
    }

    public void setJiaDiffEnd(int jiaDiffEnd) {
        this.jiaDiffEnd = jiaDiffEnd;
    }

    public int getYiDiffStart() {
        return yiDiffStart;
    }

    public void setYiDiffStart(int yiDiffStart) {
        this.yiDiffStart = yiDiffStart;
    }

    public int getYiDiffEnd() {
        return yiDiffEnd;
    }

    public void setYiDiffEnd(int yiDiffEnd) {
        this.yiDiffEnd = yiDiffEnd;
    }


    public String toString() {
        String ret = String.format("%c @@ line %d - %d ", op.getSign(), jiaDiffStart, jiaDiffEnd);
        ret += String.join("/n", contextLines);
        return ret;
    }
}
