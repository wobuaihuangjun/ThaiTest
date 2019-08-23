package com.xtc.libthai.pos;

import com.xtc.libthai.algoritm.Viterbi;
import com.xtc.libthai.dictionary.CoreDictionary;
import com.xtc.libthai.dictionary.language.ThaiCoreDictionary;
import com.xtc.libthai.dictionary.language.ThaiNatureTransMatrix;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.domain.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ThaiPOS implements POS {
    public ThaiPOS() {
    }

    public List<Term> speechTagging(List<Term> termList) {
        List<Vertex> vertexList = this.toVertexList(termList, true);
        Viterbi.compute(vertexList, ThaiNatureTransMatrix.thaiTransMatrix);
        int i = 0;

        for(Iterator var4 = termList.iterator(); var4.hasNext(); ++i) {
            Term term = (Term)var4.next();
            if (term.getNature() != null) {
                term.setNature(((Vertex)vertexList.get(i + 1)).getNature());
            } else {
                term.setNature(Nature.UN);
            }
        }

        return termList;
    }

    public List<Vertex> toVertexList(List<Term> termList, boolean appendStart) {
        ArrayList<Vertex> vertexList = new ArrayList(termList.size() + 1);
        Vertex.B = new Vertex("始##始", " ", new CoreDictionary.Attribute(Nature.BEGIN, 262468), CoreDictionary.getWordID(ThaiCoreDictionary.thaiDictionary.dictionaryTrie, "始##始"));
        if (appendStart) {
            vertexList.add(Vertex.B);
        }

        Iterator var4 = termList.iterator();

        while(var4.hasNext()) {
            Term term = (Term)var4.next();
            CoreDictionary.Attribute attribute = CoreDictionary.get(ThaiCoreDictionary.thaiDictionary.dictionaryTrie, term.getWord());
            if (attribute == null) {
                if (term.getWord().trim().length() == 0) {
                    attribute = new CoreDictionary.Attribute(Nature.PUNC);
                } else {
                    attribute = new CoreDictionary.Attribute(Nature.NPRP);
                }
            } else {
                term.setNature(attribute.nature[0]);
            }

            Vertex vertex = new Vertex(term.getWord(), attribute);
            vertexList.add(vertex);
        }

        return vertexList;
    }
}

