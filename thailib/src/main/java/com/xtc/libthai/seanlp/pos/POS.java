package com.xtc.libthai.seanlp.pos;

import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.domain.Vertex;

import java.util.List;


/**
 * 词性标注接口
 *
 */
public interface POS {

    List<Term> speechTagging(List<Term> termList);

    public List<Vertex> toVertexList(List<Term> termList, boolean appendStart);


}
