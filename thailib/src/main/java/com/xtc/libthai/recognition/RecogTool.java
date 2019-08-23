package com.xtc.libthai.recognition;


import com.xtc.libthai.Config;

public class RecogTool {
    public RecogTool() {
    }

    public static boolean recog(String text) {
        return recog(text, 0, text.length());
    }

    public static boolean recog(String text, int offset, int count) {
        return isNumber(text, offset, count) || isEnglish(text, offset, count) || isThaiDigit(text, offset, count) || isLaoDigit(text, offset, count) || isKhmerDigit(text, offset, count) || isMyanmarDigit(text, offset, count);
    }

    public static boolean isDigit(char c) {
        if (c > '9' && c < '０') {
            return false;
        } else if (c < '0') {
            return false;
        } else {
            return c <= '９';
        }
    }

    public static boolean isDigit(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            if (!isDigit(text.charAt(i))) {
                return false;
            }
        }

        if (offset > 0 && isDigit(text.charAt(offset - 1))) {
            return false;
        } else if (offset + count < text.length() && isDigit(text.charAt(offset + count))) {
            return false;
        } else {
            if (Config.DEBUG) {
                Config.Log.logger.severe("识别出数字：" + text.substring(offset, offset + count));
            }

            return true;
        }
    }

    public static boolean isDigit(String text) {
        return isDigit(text, 0, text.length());
    }

    public static boolean isPlusAndMinus(char c) {
        return c == '-' || c == '+';
    }

    public static boolean isInteger(String text, int offset, int count) {
        char c = text.charAt(offset);
        if (count - offset == 1 && !isDigit(c)) {
            return false;
        } else if (count - offset == 2 && (!isDigit(c) && !isPlusAndMinus(c) || c == '0' || !isDigit(text.charAt(offset + 1)))) {
            return false;
        } else {
            if (count - offset > 2) {
                if (!isDigit(c) && !isPlusAndMinus(c) || c == '0') {
                    return false;
                }

                if (isPlusAndMinus(c) && text.charAt(offset + 1) == '0') {
                    return false;
                }

                for(int i = offset + 1; i < offset + count; ++i) {
                    if (!isDigit(text.charAt(i))) {
                        return false;
                    }
                }
            }

            if (offset > 0 && isDigit(text.charAt(offset - 1))) {
                return false;
            } else if (offset + count < text.length() && isDigit(text.charAt(offset + count))) {
                return false;
            } else {
                if (Config.DEBUG) {
                    Config.Log.logger.severe("识别出整数：" + text.substring(offset, offset + count));
                }

                return true;
            }
        }
    }

    public static boolean isInteger(String text) {
        return isInteger(text, 0, text.length());
    }

    public static boolean isFraction(String text, int offset, int count) {
        if (count < 3) {
            return false;
        } else {
            int index = -1;

            int beforeLen;
            for(beforeLen = offset; beforeLen < offset + count; ++beforeLen) {
                char c = text.charAt(beforeLen);
                if (c == '.' || c == '/' || c == '／' || c == '．' || c == 183) {
                    index = beforeLen;
                    break;
                }
            }

            if (index != -1 && index != offset && index != offset + count - 1) {
                if ((text.charAt(index) == '/' || text.charAt(index) == '／') && text.charAt(index + 1) == '0') {
                    return false;
                } else {
                    beforeLen = index - offset;
                    return isInteger(text, offset, beforeLen) && isDigit(text, index + 1, count - (beforeLen + 1));
                }
            } else {
                return false;
            }
        }
    }

    public static boolean isFraction(String text) {
        return isFraction(text, 0, text.length());
    }

    public static boolean isNumber(String text, int offset, int count) {
        return isDigit(text, offset, count) || isInteger(text, offset, count) || isFraction(text, offset, count);
    }

    public static boolean isNumber(String text) {
        return isNumber(text, 0, text.length());
    }

    public static boolean isEnglish(char c) {
        if (c > 'z' && c < 'Ａ') {
            return false;
        } else if (c < 'A') {
            return false;
        } else if (c > 'Z' && c < 'a') {
            return false;
        } else if (c > 'Ｚ' && c < 'ａ') {
            return false;
        } else {
            return c <= 'ｚ';
        }
    }

    public static boolean isEnglish(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            char c = text.charAt(i);
            if (!isEnglish(c)) {
                return false;
            }
        }

        char c;
        if (offset > 0) {
            c = text.charAt(offset - 1);
            if (isEnglish(c)) {
                return false;
            }
        }

        if (offset + count < text.length()) {
            c = text.charAt(offset + count);
            if (isEnglish(c)) {
                return false;
            }
        }

        if (Config.DEBUG) {
            Config.Log.logger.severe("识别出英文单词：" + text.substring(offset, offset + count));
        }

        return true;
    }

    public static boolean isEnglish(String text) {
        return isEnglish(text, 0, text.length());
    }

    public static boolean isThaiDigit(char c) {
        if (c < 3664) {
            return false;
        } else {
            return c <= 3673;
        }
    }

    public static boolean isThaiDigit(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            if (!isThaiDigit(text.charAt(i))) {
                return false;
            }
        }

        if (offset > 0 && isThaiDigit(text.charAt(offset - 1))) {
            return false;
        } else if (offset + count < text.length() && isThaiDigit(text.charAt(offset + count))) {
            return false;
        } else {
            if (Config.DEBUG) {
                Config.Log.logger.severe("识别出泰语数字：" + text.substring(offset, offset + count));
            }

            return true;
        }
    }

    public static boolean isThaiDigit(String text) {
        return isThaiDigit(text, 0, text.length());
    }

    public static boolean isLaoDigit(char c) {
        if (c < 3792) {
            return false;
        } else {
            return c <= 3801;
        }
    }

    public static boolean isLaoDigit(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            if (!isLaoDigit(text.charAt(i))) {
                return false;
            }
        }

        if (offset > 0 && isLaoDigit(text.charAt(offset - 1))) {
            return false;
        } else if (offset + count < text.length() && isLaoDigit(text.charAt(offset + count))) {
            return false;
        } else {
            if (Config.DEBUG) {
                Config.Log.logger.severe("识别出老挝语数字：" + text.substring(offset, offset + count));
            }

            return true;
        }
    }

    public static boolean isLaoDigit(String text) {
        return isLaoDigit(text, 0, text.length());
    }

    public static boolean isKhmerDigit(char c) {
        if (c > 6121 && c < 6128) {
            return false;
        } else if (c < 6112) {
            return false;
        } else {
            return c <= 6137;
        }
    }

    public static boolean isKhmerDigit(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            if (!isKhmerDigit(text.charAt(i))) {
                return false;
            }
        }

        if (offset > 0 && isKhmerDigit(text.charAt(offset - 1))) {
            return false;
        } else if (offset + count < text.length() && isKhmerDigit(text.charAt(offset + count))) {
            return false;
        } else {
            if (Config.DEBUG) {
                Config.Log.logger.severe("识别出柬埔寨语数字：" + text.substring(offset, offset + count));
            }

            return true;
        }
    }

    public static boolean isKhmerDigit(String text) {
        return isKhmerDigit(text, 0, text.length());
    }

    public static boolean isMyanmarDigit(char c) {
        if (c > 4169 && c < 4240) {
            return false;
        } else if (c < 4160) {
            return false;
        } else {
            return c <= 4249;
        }
    }

    public static boolean isMyanmarDigit(String text, int offset, int count) {
        for(int i = offset; i < offset + count; ++i) {
            if (!isMyanmarDigit(text.charAt(i))) {
                return false;
            }
        }

        if (offset > 0 && isMyanmarDigit(text.charAt(offset - 1))) {
            return false;
        } else if (offset + count < text.length() && isMyanmarDigit(text.charAt(offset + count))) {
            return false;
        } else {
            if (Config.DEBUG) {
                Config.Log.logger.severe("识别出柬埔寨语数字：" + text.substring(offset, offset + count));
            }

            return true;
        }
    }

    public static boolean isMyanmarDigit(String text) {
        return isMyanmarDigit(text, 0, text.length());
    }

    public static void main(String[] args) {
        Config.BaseConf.enableDebug();
        System.out.println(isPlusAndMinus('-'));
        System.out.println(isInteger("9896"));
        System.out.println(isFraction("0.9"));
        System.out.println(isDigit(" "));
    }
}

