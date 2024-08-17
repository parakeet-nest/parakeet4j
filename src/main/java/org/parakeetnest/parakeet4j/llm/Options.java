package org.parakeetnest.parakeet4j.llm;

import java.util.List;

public class Options {
    private int repeatLastN;
    private double temperature;
    private int seed;
    private double repeatPenalty;
    private List<String> stop;

    private int numKeep;
    private int numPredict;
    private int topK;
    private double topP;
    private double tfsZ;
    private double typicalP;
    private double presencePenalty;
    private double frequencyPenalty;
    private int mirostat;
    private double mirostatTau;
    private double mirostatEta;
    private boolean penalizeNewline;

    public Options() {
        this.verbose = false;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public Options setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    private boolean verbose;

    public int getRepeatLastN() {
        return repeatLastN;
    }

    public Options setRepeatLastN(int repeatLastN) {
        this.repeatLastN = repeatLastN;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public Options setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public int getSeed() {
        return seed;
    }

    public Options setSeed(int seed) {
        this.seed = seed;
        return this;
    }

    public double getRepeatPenalty() {
        return repeatPenalty;
    }

    public Options setRepeatPenalty(double repeatPenalty) {
        this.repeatPenalty = repeatPenalty;
        return this;
    }

    public List<String> getStop() {
        return stop;
    }

    public Options setStop(List<String> stop) {
        this.stop = stop;
        return this;
    }

    public int getNumKeep() {
        return numKeep;
    }

    public Options setNumKeep(int numKeep) {
        this.numKeep = numKeep;
        return this;
    }

    public int getNumPredict() {
        return numPredict;
    }

    public Options setNumPredict(int numPredict) {
        this.numPredict = numPredict;
        return this;
    }

    public int getTopK() {
        return topK;
    }

    public Options setTopK(int topK) {
        this.topK = topK;
        return this;
    }

    public double getTopP() {
        return topP;
    }

    public Options setTopP(double topP) {
        this.topP = topP;
        return this;
    }

    public double getTfsZ() {
        return tfsZ;
    }

    public Options setTfsZ(double tfsZ) {
        this.tfsZ = tfsZ;
        return this;
    }

    public double getTypicalP() {
        return typicalP;
    }

    public Options setTypicalP(double typicalP) {
        this.typicalP = typicalP;
        return this;
    }

    public double getPresencePenalty() {
        return presencePenalty;
    }

    public Options setPresencePenalty(double presencePenalty) {
        this.presencePenalty = presencePenalty;
        return this;
    }

    public double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public Options setFrequencyPenalty(double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
        return this;
    }

    public int getMirostat() {
        return mirostat;
    }

    public Options setMirostat(int mirostat) {
        this.mirostat = mirostat;
        return this;
    }

    public double getMirostatTau() {
        return mirostatTau;
    }

    public Options setMirostatTau(double mirostatTau) {
        this.mirostatTau = mirostatTau;
        return this;
    }

    public double getMirostatEta() {
        return mirostatEta;
    }

    public Options setMirostatEta(double mirostatEta) {
        this.mirostatEta = mirostatEta;
        return this;
    }

    public boolean isPenalizeNewline() {
        return penalizeNewline;
    }

    public Options setPenalizeNewline(boolean penalizeNewline) {
        this.penalizeNewline = penalizeNewline;
        return this;
    }
}


