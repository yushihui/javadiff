package com.future.jdiff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/**
 * Created by shihui on 9/13/2017.
 * we try to compare 2 list of strings, I am going to use jia(甲) & yi(乙) 2 variables
 * the Differences are computed as "what do we need to do to 'jia' to change it into 'yi'?"
 */
public class DifferFetcher {


    /**
     * this is the first variable to compare
     */
    private final List<String> jia;

    /**
     * this is the second variable to compare
     */
    private final List<String> yi;

    private IsJunkLines junkChecker;

    private List<MatchedLines> matchedBlocks;

    /**
     * line content as the key, line indexes are the values; just
     */
    private Map<String, List<Integer>> linesOfYi;

    public DifferFetcher(List<String> jia, List<String> yi) {
        this.jia = jia;
        this.yi = yi;
        matchedBlocks = new ArrayList<>();
    }

    public DifferFetcher(List<String> jia, List<String> yi, IsJunkLines junkChecker) {
        this.jia = jia;
        this.yi = yi;
        this.junkChecker = junkChecker;
        matchedBlocks = new ArrayList<>();
    }

    public List<MatchedLines> getMatchedBlocks() {
        return matchedBlocks;
    }

    private double calculateRatio(long matches, long length) {
        if (length <= 0) {
            return 1.0;
        }
        return 2.0d * matches / length;
    }

    private void calculateLinesOfYi() {
        this.linesOfYi = IntStream.range(0, yi.size()).boxed().collect(toMap(c -> yi.get(c), s -> {
            List<Integer> l = new ArrayList<>();
            l.add(s);
            return l;
        }, (a, b) -> {
            a.add(b.get(0));
            return a;
        }));
    }

    private MatchedLines findBestMatch(int jiaLo, int jiaHi, int yiLo, int yiHi) {
        Map<Integer, Integer> yiIndexMatchedLines = new HashMap<>();
        int bestJ = jiaLo;
        int bestY = yiLo;
        int bestSize = 0;

        for (int i = jiaLo; i < jiaHi; i++) {
            List<Integer> occur = linesOfYi.get(jia.get(i));
            Map<Integer, Integer> yiIndexLength = new HashMap<>();
            if (occur == null) {
                yiIndexMatchedLines = yiIndexLength;
                continue;
            }

            for (int j = 0; j != occur.size(); j++) {
                int indexOfj = occur.get(j);
                if (indexOfj < yiLo) {
                    continue;
                }
                if (indexOfj > yiHi) {
                    break;
                }
                // yiIndexMatchedLines.get(j - 1);
                int length = 1;
                if (yiIndexMatchedLines.containsKey(indexOfj - 1)) {
                    length = yiIndexMatchedLines.get(indexOfj - 1) + 1;
                }
                yiIndexLength.put(indexOfj, length);
                if (length > bestSize) {
                    // bestLength = length;
                    bestJ = i - length + 1;
                    bestY = indexOfj - length + 1;
                    bestSize = length;
                }
            }
           // System.out.println(bestJ + ":" + bestY + ":" + bestSize);
            yiIndexMatchedLines = yiIndexLength;
        }
        while (bestJ > jiaLo && bestY > yiLo && jia.get(bestJ - 1).equalsIgnoreCase(yi.get(bestY - 1))) {
            bestJ = bestJ - 1;
            bestY = bestY - 1;
            bestSize += 1;
        }
        while (bestJ + bestSize < jiaHi && bestY + bestSize > yiHi && jia.get(bestJ + bestSize).equalsIgnoreCase(yi.get(bestY + bestSize))) {
            bestSize += 1;
        }
        MatchedLines result = new MatchedLines(bestJ, bestY, bestSize);
        return result;
    }

    /**
     * @param blocks
     * @return
     */
    private List<MatchedLines> mergeMatchedBlocks(List<MatchedLines> blocks) {
        List<MatchedLines> result = new ArrayList<>();
        int j1 = 0, y1 = 0, l1 = 0;
        for (MatchedLines block : blocks) {
            int j2 = block.getJiaStartIndex();
            int y2 = block.getYiStartIndex();
            int l2 = block.getLines();
            if (j1 + l1 == j2 && y1 + l1 == y2) {
                l1 += l2;
            } else {
                if (l1 > 0) {
                    result.add(new MatchedLines(j1, y1, l1));
                }
                j1 = j2;
                y1 = y2;
                l1 = l2;
            }
        }
        if (l1 > 0) {
            result.add(new MatchedLines(j1, y1, l1));
        }
        return result;
    }

    public List<OperationDetail> operationsFromJia2Yi() {
        List<MatchedLines> matched = new ArrayList<>();
        calculateLinesOfYi();
        recursivelyFetching(matched, 0, jia.size(), 0, yi.size());
        matched = mergeMatchedBlocks(matched);
        return getChangedOperations(matched);
    }


    private List<OperationDetail> getChangedOperations(List<MatchedLines> matchedBlocks) {
        int j = 0;
        int y = 0;
        List<OperationDetail> operations = new ArrayList<>();
        for (MatchedLines matchedLines : matchedBlocks) {
            Operation op = Operations.EQUAL;
            List<String> lines = new ArrayList<>();
            if (j < matchedLines.getJiaStartIndex() && y < matchedLines.getYiStartIndex()) {//change
                op = Operations.CHANGE;
            } else if (j < matchedLines.getJiaStartIndex()) {
                op = Operations.DELETE;
                lines = jia.subList(j, matchedLines.getJiaStartIndex());
            } else if (y < matchedLines.getYiStartIndex()) {
                op = Operations.ADD;
                lines = yi.subList(y, matchedLines.getYiStartIndex());
            }
            if (op.getSign() != '=') {
                operations.add(new OperationDetail(op, j + 1, matchedLines.getJiaStartIndex(), y + 1, matchedLines.getYiStartIndex(), lines));
            }
            j = matchedLines.getNextStartofJia();
            y = matchedLines.getNextStartofYi();
        }
        int lenJ = jia.size() - 1;
        int lenY = yi.size() - 1;
        if (j > lenJ && y > lenY) {

        } else {
            List<String> lines = new ArrayList<>();
            Operation op = Operations.EQUAL;
            if (j <= lenJ && y <= lenY) {
                op = Operations.CHANGE;
            } else if (j <= lenJ) {
                op = Operations.DELETE;
                lines = jia.subList(j, lenJ);
            } else if (y <= lenY) {
                op = Operations.ADD;
                lines = yi.subList(y, lenY);
            }
            if (op.getSign() != '=') {
                operations.add(new OperationDetail(op, j, lenJ, y, lenY, lines));
            }
        }
        return operations;
    }


    private void recursivelyFetching(List<MatchedLines> matched, int jiaLo, int jiaHi, int yiLo, int yiHi) {

        MatchedLines block = findBestMatch(jiaLo, jiaHi, yiLo, yiHi);
        if (block.getLines() > 0) {
            if (jiaLo < block.getJiaStartIndex() && yiLo < block.getYiStartIndex()) {
                recursivelyFetching(matched, jiaLo, block.getJiaStartIndex(), yiLo, block.getYiStartIndex());
            }
            matched.add(block);
            if (block.getNextStartofJia() < jiaHi && block.getNextStartofYi() < yiHi) {
                recursivelyFetching(matched, block.getNextStartofJia(), jiaHi, block.getNextStartofYi(), yiHi);
            }
        }
        return;
    }


}
