package com.nestsoft.anjidictionary.Models;

import java.util.List;

public class Meanings {
    String partOfSpeech = "";
    List<Definations> definitions = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definations> getDefinations() {
        return definitions;
    }

    public void setDefinations(List<Definations> definations) {
        this.definitions = definations;
    }
}
