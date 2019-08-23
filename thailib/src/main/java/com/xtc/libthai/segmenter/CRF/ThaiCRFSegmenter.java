package com.xtc.libthai.segmenter.CRF;

import com.xtc.libthai.CRF.Table;
import com.xtc.libthai.CRF.model.StaticThaidCRFModel;
import com.xtc.libthai.CRF.model.StaticThaigCRFModel;
import com.xtc.libthai.Config;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.pos.POS;
import com.xtc.libthai.pos.ThaiPOS;
import com.xtc.libthai.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.util.RadicalMap;
import com.xtc.libthai.util.StringUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ThaiCRFSegmenter extends AbstractThaiSegmenter {
    private final POS pos = new ThaiPOS();

    public ThaiCRFSegmenter() {
    }

    protected StringBuffer syllableSegment(char[] chars) {
        if (chars.length == 0) {
            return null;
        } else {
            String[][] v = new String[chars.length][3];
            int length = chars.length;

            for(int i = 0; i < length; ++i) {
                v[i][0] = String.valueOf(chars[i]);
                v[i][1] = RadicalMap.getThaiCharType(chars[i]);
            }

            Table table = new Table();
            table.sheet = v;
            StaticThaidCRFModel.crfThaiSyllableSegmentModel.tag(table);
            StringBuffer sb = new StringBuffer();
            if (Config.DEBUG) {
                System.out.println("CRF标注结果");
                System.out.println(table);
            }

            for(int i = 0; i < table.sheet.length; ++i) {
                String[] line = table.sheet[i];
                switch(line[2].charAt(0)) {
                    case 'B':
                        int begin = i;

                        while(table.sheet[i][2].charAt(0) != 'E') {
                            ++i;
                            if (i == table.sheet.length) {
                                break;
                            }
                        }

                        if (i == table.sheet.length) {
                            sb.append(new String(chars, begin, i - begin) + "|");
                        } else {
                            sb.append(new String(chars, begin, i - begin + 1) + "|");
                        }
                        break;
                    default:
                        sb.append(line[0] + "|");
                }
            }

            return sb;
        }
    }

    protected List<Term> syllableMerging(String[] syllables) {
        if (syllables.length == 0) {
            return Collections.emptyList();
        } else {
            String[][] v = new String[syllables.length][3];
            int length = syllables.length;

            for(int i = 0; i < length; ++i) {
                v[i][0] = String.valueOf(syllables[i]);
                v[i][1] = RadicalMap.getThaiSyllableType(syllables[i]);
            }

            Table table = new Table();
            table.sheet = v;
            StaticThaidCRFModel.crfThaiSyllableMergeModel.tag(table);
            List<Term> termList = new LinkedList();
            if (Config.DEBUG) {
                System.out.println("CRF标注结果");
                System.out.println(table);
            }

            for(int i = 0; i < table.sheet.length; ++i) {
                String[] line = table.sheet[i];
                switch(line[2].charAt(0)) {
                    case 'B':
                        int begin = i;

                        while(table.sheet[i][2].charAt(0) != 'E') {
                            ++i;
                            if (i == table.sheet.length) {
                                break;
                            }
                        }

                        if (i == table.sheet.length) {
                            ((List)termList).add(new Term(StringUtil.merge(syllables, begin, i - begin), (Nature)null));
                        } else {
                            ((List)termList).add(new Term(StringUtil.merge(syllables, begin, i - begin + 1), (Nature)null));
                        }
                        break;
                    default:
                        ((List)termList).add(new Term(line[0], (Nature)null));
                }
            }

            if (Config.BaseConf.speechTagging) {
                termList = this.pos.speechTagging((List)termList);
            }

            return (List)termList;
        }
    }

    protected List<Term> segment(char[] chars) {
        if (chars.length == 0) {
            return null;
        } else {
            Table table = new Table();
            table.sheet = this.createThaiCharSheet(chars);
            StaticThaidCRFModel.crfThaiSyllableSegmentModel.tag(table);
            String[] syllables = this.mergeChar(chars, table).replaceAll("\\|$", "").split("\\|");
            Table syllableTable = new Table();
            syllableTable.sheet = this.createThaiSyllableSheet(syllables);
            StaticThaidCRFModel.crfThaiSyllableMergeModel.tag(syllableTable);
            List<Term> termList = this.mergeSyllable(syllables, syllableTable);
            if (Config.BaseConf.speechTagging) {
                termList = this.pos.speechTagging(termList);
            }

            return termList;
        }
    }

    protected List<Term> gCRFWordSegment(char[] chars) {
        if (chars.length == 0) {
            return Collections.emptyList();
        } else {
            String[][] v = new String[chars.length][3];
            int length = chars.length;

            for(int i = 0; i < length; ++i) {
                v[i][0] = String.valueOf(chars[i]);
                v[i][1] = RadicalMap.getThaiCharType(chars[i]);
            }

            Table table = new Table();
            table.sheet = v;
            StaticThaigCRFModel.crfThaiWordSegmentModel.tag(table);
            List<Term> termList = new LinkedList();
            if (Config.DEBUG) {
                System.out.println("CRF标注结果");
                System.out.println(table);
            }

            for(int i = 0; i < table.sheet.length; ++i) {
                String[] line = table.sheet[i];
                switch(line[2].charAt(0)) {
                    case 'B':
                        int begin = i;

                        while(table.sheet[i][2].charAt(0) != 'E') {
                            ++i;
                            if (i == table.sheet.length) {
                                break;
                            }
                        }

                        if (i == table.sheet.length) {
                            ((List)termList).add(new Term(new String(chars, begin, i - begin), (Nature)null));
                        } else {
                            ((List)termList).add(new Term(new String(chars, begin, i - begin + 1), (Nature)null));
                        }
                        break;
                    default:
                        ((List)termList).add(new Term(line[0], (Nature)null));
                }
            }

            if (Config.BaseConf.speechTagging) {
                termList = this.pos.speechTagging((List)termList);
            }

            return (List)termList;
        }
    }

    public String[][] createThaiCharSheet(char[] chars) {
        int length = chars.length;
        if (length == 0) {
            return (String[][])null;
        } else {
            String[][] sheet = new String[length][3];

            for(int i = 0; i < length; ++i) {
                sheet[i][0] = String.valueOf(chars[i]);
                sheet[i][1] = RadicalMap.getThaiCharType(chars[i]);
            }

            return sheet;
        }
    }

    public String[][] createThaiSyllableSheet(String[] syllables) {
        int length = syllables.length;
        if (length == 0) {
            return (String[][])null;
        } else {
            String[][] sheet = new String[length][3];

            for(int i = 0; i < length; ++i) {
                String syllable = syllables[i];
                sheet[i][0] = syllable;
                sheet[i][1] = RadicalMap.getThaiSyllableType(syllable);
            }

            return sheet;
        }
    }

    public String mergeChar(char[] chars, Table table) {
        StringBuffer sb = new StringBuffer();
        if (Config.DEBUG) {
            System.out.println("CRF标注结果");
            System.out.println(table);
        }

        for(int i = 0; i < table.sheet.length; ++i) {
            String[] line = table.sheet[i];
            switch(line[2].charAt(0)) {
                case 'B':
                    int begin = i;

                    while(table.sheet[i][2].charAt(0) != 'E') {
                        ++i;
                        if (i == table.sheet.length) {
                            break;
                        }
                    }

                    if (i == table.sheet.length) {
                        sb.append(new String(chars, begin, i - begin) + "|");
                    } else {
                        sb.append(new String(chars, begin, i - begin + 1) + "|");
                    }
                    break;
                default:
                    sb.append(line[0] + "|");
            }
        }

        return sb.toString();
    }

    public List<Term> mergeSyllable(String[] syllables, Table table) {
        List<Term> termList = new LinkedList();
        if (Config.DEBUG) {
            System.out.println("CRF标注结果");
            System.out.println(table);
        }

        for(int i = 0; i < table.sheet.length; ++i) {
            String[] line = table.sheet[i];
            switch(line[2].charAt(0)) {
                case 'B':
                    int begin = i;

                    while(table.sheet[i][2].charAt(0) != 'E') {
                        ++i;
                        if (i == table.sheet.length) {
                            break;
                        }
                    }

                    if (i == table.sheet.length) {
                        termList.add(new Term(StringUtil.merge(syllables, begin, i - begin), (Nature)null));
                    } else {
                        termList.add(new Term(StringUtil.merge(syllables, begin, i - begin + 1), (Nature)null));
                    }
                    break;
                default:
                    termList.add(new Term(line[0], (Nature)null));
            }
        }

        return termList;
    }

    protected String syllableSegment(String[] sentences) {
        StringBuffer sb = new StringBuffer();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            sb.append(this.syllableSegment(sentences[i].toCharArray()));
        }

        return sb.toString();
    }

    protected String[] sentenceTosyllables(char[] chars) {
        return this.syllableSegment(chars).toString().replaceAll("\\|$", "").split("\\|");
    }

    protected String[] sentenceTosyllables(String sentence) {
        return this.sentenceTosyllables(sentence.toCharArray());
    }

    protected List<Term> syllableMerge(String sentence) {
        return this.syllableMerging(this.sentenceTosyllables(sentence));
    }

    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> termList = new LinkedList();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            termList.addAll(this.syllableMerge(sentences[i]));
        }

        return termList;
    }

    protected List<Term> syllableMerge(String[] sentences) {
        List<Term> termList = new LinkedList();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            termList.addAll(this.syllableMerge(sentences[i]));
        }

        return termList;
    }

    protected List<Term> gCRFWordSegment(String[] sentences) {
        List<Term> termList = new LinkedList();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            termList.addAll(this.gCRFWordSegment(sentences[i].toCharArray()));
        }

        return termList;
    }

    protected List<Term> segment(String[] sentences) {
        return this.sentenceMerge(sentences);
    }

    public String syllableSegment(String text) {
        return this.syllableSegment(this.sentenceSegment(text));
    }

    public List<Term> dCRFWordSegment(String text) {
        return this.syllableMerge(this.sentenceSegment(text));
    }

    public List<Term> gCRFWordSegment(String text) {
        return this.gCRFWordSegment(this.sentenceSegment(text));
    }

    public List<Term> seg(String text) {
        return this.segment(text.toCharArray());
    }
}
