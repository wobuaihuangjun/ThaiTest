package com.xtc.libthai.segmenter.CC;

import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCC extends AbstractCC {
    private static final String CON = "[ก-ฮ]";
    private static final String YOYAK = "ย";
    private static final String O_ANG = "อ";
    private static final String WA = "วะ";
    private static final String SIGN = "ฯ";
    private static final String A = "ะ";
    private static final String MAI_HAN_AKAT = "ั";
    private static final String SARA_AA = "า";
    private static final String SARA_AM = "ำ";
    private static final String SARA_I = "ิ";
    private static final String SARA_II = "ี";
    private static final String SARA_UE = "ึ";
    private static final String SARA_UEE = "ื";
    private static final String SARA_U = "ุ";
    private static final String SARA_UU = "ู";
    private static final String PHINTHU = "ฺ";
    private static final String SYMBOL_BAHT = "฿";
    private static final String EE = "เ";
    private static final String AE = "แ";
    private static final String OO = "โ";
    private static final String MAIMUAN = "ใ";
    private static final String MAIMALAI = "ไ";
    private static final String REPE_MARK = "ๆ";
    private static final String MAITAIKHU = "็";
    private static final String TON = "[่้๊๋]";
    private static final String THANTHAKHAT = "์";
    private static final String NIKHAHIT = "ํ";
    private static final String YAMAKKAN = "๎";
    private static final String FONGMAN = "๏";
    private static final String THAI_DIGITS = "[๐-๙]+";
    private static final String ANGKHANKHU = "๚";
    private static final String KHOMUT = "๛";
    private static final String vback = "ั|ื";
    private static final String vfront = "ิ|ี|ุ|ู";
    private static final String vO1 = "ะ|า|ำ";
    private static final String vneedl = "ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ";
    private static final String needl = "ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ|์";
    private static final String vO2 = "เ|แ|โ|ใ|ไ";
    private static final String con = "[ก-ฮ]|ย|อ";
    private static final String newlines = "([\r\n]+)?";
    private static final String tccfirst = "เ|แ|โ|ใ|ไ|([ก-ฮ]|ย|อ)|วะ|.";
    private static final String dont_know = "วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.";
    private static final String tcc = "(เ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)[่้๊๋]?าะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)อะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ([ก-ฮ]|ย|อ)์([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ[่้๊๋]?([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)ีอะ?)|(เ([ก-ฮ]|ย|อ)ื[่้๊๋]อะ?)|(เ([ก-ฮ]|ย|อ)ือะ?)|(เ([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู)[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|((เ|แ|โ|ใ|ไ)([ก-ฮ]|ย|อ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)ั[่้๊๋]?วะ)|(([ก-ฮ]|ย|อ)(ั|ื)[่้๊๋]?[ก-ฮ]|ย|อ(ุ|ิ|ะ)?)|(([ก-ฮ]|ย|อ)(ิ|ุ)?์)|(([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)็)|(([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|(([ก-ฮ]|ย|อ)[่้๊๋]?(ะ|า|ำ)?)|(แ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(แ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(โ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.";

    public TCC() {
    }

    public String token(String sentence) {
        Pattern p = Pattern.compile("(เ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)[่้๊๋]?าะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)อะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ([ก-ฮ]|ย|อ)์([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ[่้๊๋]?([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)ีอะ?)|(เ([ก-ฮ]|ย|อ)ื[่้๊๋]อะ?)|(เ([ก-ฮ]|ย|อ)ือะ?)|(เ([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู)[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|((เ|แ|โ|ใ|ไ)([ก-ฮ]|ย|อ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)ั[่้๊๋]?วะ)|(([ก-ฮ]|ย|อ)(ั|ื)[่้๊๋]?[ก-ฮ]|ย|อ(ุ|ิ|ะ)?)|(([ก-ฮ]|ย|อ)(ิ|ุ)?์)|(([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)็)|(([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|(([ก-ฮ]|ย|อ)[่้๊๋]?(ะ|า|ำ)?)|(แ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(แ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(โ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.");
        Matcher m = p.matcher(sentence);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            sb.append(m.group()).append("|");
        }

        return sb.toString();
    }

    public List<Term> segment(String sentence) {
        Pattern p = Pattern.compile("(เ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)[่้๊๋]?าะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)อะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ([ก-ฮ]|ย|อ)์([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ[่้๊๋]?([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)ีอะ?)|(เ([ก-ฮ]|ย|อ)ื[่้๊๋]อะ?)|(เ([ก-ฮ]|ย|อ)ือะ?)|(เ([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู)[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|((เ|แ|โ|ใ|ไ)([ก-ฮ]|ย|อ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)ั[่้๊๋]?วะ)|(([ก-ฮ]|ย|อ)(ั|ื)[่้๊๋]?[ก-ฮ]|ย|อ(ุ|ิ|ะ)?)|(([ก-ฮ]|ย|อ)(ิ|ุ)?์)|(([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)็)|(([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|(([ก-ฮ]|ย|อ)[่้๊๋]?(ะ|า|ำ)?)|(แ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(แ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(โ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.");
        Matcher m = p.matcher(sentence);
        ArrayList result = new ArrayList();

        while(m.find()) {
            result.add(new Term(m.group(), (Nature)null));
        }

        return result;
    }

    public static String[] toTCC(String sentence) {
        Pattern p = Pattern.compile("(เ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)[่้๊๋]?าะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)อะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ([ก-ฮ]|ย|อ)์([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ[่้๊๋]?([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)ีอะ?)|(เ([ก-ฮ]|ย|อ)ื[่้๊๋]อะ?)|(เ([ก-ฮ]|ย|อ)ือะ?)|(เ([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู)[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|((เ|แ|โ|ใ|ไ)([ก-ฮ]|ย|อ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)ั[่้๊๋]?วะ)|(([ก-ฮ]|ย|อ)(ั|ื)[่้๊๋]?[ก-ฮ]|ย|อ(ุ|ิ|ะ)?)|(([ก-ฮ]|ย|อ)(ิ|ุ)?์)|(([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)็)|(([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|(([ก-ฮ]|ย|อ)[่้๊๋]?(ะ|า|ำ)?)|(แ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(แ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(โ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.");
        Matcher m = p.matcher(sentence);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            sb.append(m.group()).append("|");
        }

        return sb.toString().split("\\|");
    }

    public String regexToken(String regex, String sentence) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sentence);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            sb.append(m.group()).append("|");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String content = "เจ้าของบ้านจะต้องไปติดต่อที่การไฟฟ้านครหลวง หรือการไฟฟ้าส่วนภูมิภาคของเขตนั้นๆ เพื่อขออนุญาตใช้ไฟฟ้าเอ็ง \n ds:gdf!`sa。fds!af*f(jh)sa&dhk^kh……ggf;dg.ds2423sd234324@#@$d我；";
        TCC tCC = new TCC();
        System.out.println(toTCC(content));
        System.out.println(tCC.regexToken("(เ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)[่้๊๋]?าะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)ี[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)อะ)|(เ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ([ก-ฮ]|ย|อ)์([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ิ[่้๊๋]?([ก-ฮ]|ย|อ))|(เ([ก-ฮ]|ย|อ)ี[่้๊๋]?ยะ)|(เ([ก-ฮ]|ย|อ)ีอะ?)|(เ([ก-ฮ]|ย|อ)ื[่้๊๋]อะ?)|(เ([ก-ฮ]|ย|อ)ือะ?)|(เ([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู)[่้๊๋]?ย)|(เ([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|((เ|แ|โ|ใ|ไ)([ก-ฮ]|ย|อ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)ั[่้๊๋]?วะ)|(([ก-ฮ]|ย|อ)(ั|ื)[่้๊๋]?[ก-ฮ]|ย|อ(ุ|ิ|ะ)?)|(([ก-ฮ]|ย|อ)(ิ|ุ)?์)|(([ก-ฮ]|ย|อ)(ิ|ี|ุ|ู|ะ|า|ำ|ื|ึ)[่้๊๋]?)|(([ก-ฮ]|ย|อ)็)|(([ก-ฮ]|ย|อ)[่้๊๋]?า?ะ?)|(([ก-ฮ]|ย|อ)[่้๊๋]?(ะ|า|ำ)?)|(แ([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(แ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)็([ก-ฮ]|ย|อ))|(แ([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)([ก-ฮ]|ย|อ)์)|(โ([ก-ฮ]|ย|อ)[่้๊๋]?ะ)|วะ|เ|แ|โ|ใ|ไ|ย|็|์|ะ|อ|ึ|ื|ิ|ี|า|ำ|ั|ุ|ู|[่้๊๋]|[ก-ฮ]|ฯ|ๆ|ฺ|฿|ํ|๎|๏|[๐-๙]+|๚|๛|[0-9]+|[a-zA-Z]+|[\r\n]+|[\\pP‘’“”]+|[\\s]+|.", content));
        System.out.println("เจ้า|ข|อ|ง|บ้า|น|จะ|ต้|อ|ง|ไป|ติ|ด|ต่|อ|ที่|กา|ร|ไฟ|ฟ้า|น|ค|ร|ห|ล|ว|ง| |ห|รือ|กา|ร|ไฟ|ฟ้า|ส่|ว|น|ภู|มิ|ภา|ค|ข|อ|ง|เข|ต|นั้น|ๆ| |เพื่อ|ข|อ|อ|นุ|ญา|ต|ใช้|ไฟ|ฟ้า|เอ็ง|");
        int segWords = content.length();
        int pressure = 10000;
        long start = System.currentTimeMillis();

        for(int i = 0; i < pressure; ++i) {
            tCC.token(content);
        }

        double costTime = (double)(System.currentTimeMillis() - start) / 1000.0D;
        System.out.printf("速度：%.2f 字符每秒", (double)(segWords * pressure) / costTime);
    }
}

