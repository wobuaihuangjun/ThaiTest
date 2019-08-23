package com.xtc.libthai.pos;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.domain.Vertex;

import java.util.List;

public interface POS {
    List<Term> speechTagging(List<Term> var1);

    List<Vertex> toVertexList(List<Term> var1, boolean var2);
}
