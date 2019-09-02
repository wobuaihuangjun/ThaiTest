package com.xtc.libthai.seanlp.segmenter.matcher;

import com.xtc.libthai.seanlp.collection.trie.DATrie;
import com.xtc.libthai.seanlp.recognition.RecogTool;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.util.StringUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 基于词典的逆向最大匹配算法
 * Dictionary-based reverse maximum matching algorithm
 *
 */
public class ReverseMaximumMatcher  extends AbstractMatcher {

    public ReverseMaximumMatcher(DATrie<?> dict) {
        super(dict);
    }

    @Override
    public List<Term> segment(String[] strs) {
        List<Term> result = new LinkedList<>();
        //文本长度
        final int textLen=strs.length;
        //从未分词的文本中截取的长度
        //nt len=getInterceptLength();
        int len = wordMaxLength;
        //剩下未分词的文本的索引
        int start=textLen-len;
        //处理文本长度小于最大词长的情况
        if(start<0){
            start=0;
        }
        if(len>textLen-start){
            //如果未分词的文本的长度小于截取的长度
            //则缩短截取的长度
            len=textLen-start;
        }
        //只要有词未切分完就一直继续
        while(start>=0 && len>0){
            //用长为len的字符串查词典，并做特殊情况识别
            while(!dict.contains(StringUtil.merge(strs, start, len)) && !RecogTool.recog(StringUtil.merge(strs, start, len))){
                //如果长度为一且在词典中未找到匹配
                //则按长度为一切分
                if(len==1){
                    break;
                }
                //如果查不到，则长度减一
                //索引向后移动一个字，然后继续
                len--;
                start++;
            }
            result.add(0, new Term(StringUtil.merge(strs, start, len), null));
            //每一次成功切词后都要重置截取长度
            len = wordMaxLength;
            //len=getInterceptLength();
            if(len>start){
                //如果未分词的文本的长度小于截取的长度
                //则缩短截取的长度
                len=start;
            }
            //每一次成功切词后都要重置开始索引位置
            //从待分词文本中向前移动最大词长个索引
            //将未分词的文本纳入下次分词的范围
            start-=len;
        }
        return result;
    }

//	@Override
//	public List<Term> segment(String sentence) {
//		List<Term> result = new LinkedList<Term>();
//        //文本长度
//        final int textLen=sentence.length();
//        //从未分词的文本中截取的长度
//        //nt len=getInterceptLength();
//        int len = wordMaxLength;
//        //剩下未分词的文本的索引
//        int start=textLen-len;
//        //处理文本长度小于最大词长的情况
//        if(start<0){
//            start=0;
//        }
//        if(len>textLen-start){
//            //如果未分词的文本的长度小于截取的长度
//            //则缩短截取的长度
//            len=textLen-start;
//        }
//        //只要有词未切分完就一直继续
//        while(start>=0 && len>0){
//            //用长为len的字符串查词典，并做特殊情况识别
//        	 while(!dict.contains(sentence, start, len)  && !RecogTool.recog(new String(sentence.toCharArray(), start, len))){
//                //如果长度为一且在词典中未找到匹配
//                //则按长度为一切分
//                if(len==1){
//                    break;
//                }
//                //如果查不到，则长度减一
//                //索引向后移动一个字，然后继续
//                len--;
//                start++;
//            }
//            result.add(0, new Term(new String(sentence.toCharArray(), start, len), null));
//            //result.add(word);
//            //每一次成功切词后都要重置截取长度
//            len = wordMaxLength;
//            //len=getInterceptLength();
//            if(len>start){
//                //如果未分词的文本的长度小于截取的长度
//                //则缩短截取的长度
//                len=start;
//            }
//            //每一次成功切词后都要重置开始索引位置
//            //从待分词文本中向前移动最大词长个索引
//            //将未分词的文本纳入下次分词的范围
//            start-=len;
//        }
//        return result;
//	}

    @Override
    public List<Term> segment(char[] sentence) {
        List<Term> result = new LinkedList<>();
        //文本长度
        final int textLen=sentence.length;
        //从未分词的文本中截取的长度
        //nt len=getInterceptLength();
        int len = wordMaxLength;
        //剩下未分词的文本的索引
        int start=textLen-len;
        //处理文本长度小于最大词长的情况
        if(start<0){
            start=0;
        }
        if(len>textLen-start){
            //如果未分词的文本的长度小于截取的长度
            //则缩短截取的长度
            len=textLen-start;
        }
        //只要有词未切分完就一直继续
        while(start>=0 && len>0){
            //用长为len的字符串查词典，并做特殊情况识别
            while(!dict.contains(sentence, start, len)  && !RecogTool.recog(new String(sentence, start, len))){
                //如果长度为一且在词典中未找到匹配
                //则按长度为一切分
                if(len==1){
                    break;
                }
                //如果查不到，则长度减一
                //索引向后移动一个字，然后继续
                len--;
                start++;
            }
            result.add(0, new Term(new String(sentence, start, len), null));
            //result.add(word);
            //每一次成功切词后都要重置截取长度
            len = wordMaxLength;
            //len=getInterceptLength();
            if(len>start){
                //如果未分词的文本的长度小于截取的长度
                //则缩短截取的长度
                len=start;
            }
            //每一次成功切词后都要重置开始索引位置
            //从待分词文本中向前移动最大词长个索引
            //将未分词的文本纳入下次分词的范围
            start-=len;
        }
        return result;
    }

    @Override
    public List<Term> segment(String sentence) {
        return segment(sentence.toCharArray());
    }

}
