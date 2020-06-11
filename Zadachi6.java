import java.util.*;
public class Zadachi6 {
    public static void main(String[] args) {
        System.out.println();
    }
    //1 задача
    private int bell(int n) {

        int[] Line1 = new int[n];
        int[] Line2 = new int[n];
        Line1[0] = 1;
        int result;

        for (int i = 2; i <= n; i += 1) {
            Line2[0] = Line1[i - 2];
            for (int j = 1; j < i; j += 1) {
                Line2[j] = Line2[j - 1] + Line2[j - 1];
            }
            Line1 = Line2.clone();
        }
        result = Line1[n - 1];
        return result;
    }
    //2 задача
    private String translateWord(String line) {
        String vowels = "AEIOUaeiou";
        String translatedLetters = "";
        if (vowels.contains(line.substring(0, 1))) {
            return line + "yay";
        }
        for (int i = 0; i < line.length(); i += 1) {
            if (vowels.contains(line.substring(i, i + 1))) {
                translatedLetters = line.substring(i) + line.substring(0, i) + "ay";
                break;
            }
        }
        return translatedLetters;
    }

    private int findSplitter(String line) {
        String splitter = ".,?!\"':;<>\\|/";
        int index = -1;
        for (int i = 0; i < line.length(); i += 1) {
            if (splitter.contains(line.substring(i, i + 1))) {
                index = i;
            }
        }
        return index;
    }

    private String translateSentence(String line) {
        String[] resultSentence = line.split(" ");
        for (int i = 0; i < resultSentence.length; i += 1) {
            int indexSplitter = findSplitter(resultSentence[i]);
            if (indexSplitter != -1) {
                String preWord = resultSentence[i].substring(0, indexSplitter);
                String postWord = resultSentence[i].substring(indexSplitter + 1);
                String splitter = resultSentence[i].substring(indexSplitter, indexSplitter + 1);
                resultSentence[i] = preWord.length() > 0 ? translateWord(preWord) : "";
                resultSentence[i] += splitter;
                resultSentence[i] += postWord.length() > 0 ? translateWord(postWord) : "";
            } else {
                resultSentence[i] = translateWord(resultSentence[i]);
            }
        }
        return String.join(" ", resultSentence);
    }
    //3 задача
    public static boolean validColor(String color){
        if (color.substring(0, 3).equals("rgb") && color.charAt(3) != 'a') {
            String numbers = color.substring(4, color.length() - 1);
            try {
                int[] numArr = Arrays.stream(numbers.split(",")).mapToInt(Integer::parseInt).toArray();
                for (int i = 0; i < numArr.length; i++) if (numArr[i] < 0 || numArr[i] > 255) return false;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        if (color.substring(0, 4).equals("rgba")){
            String numbers = color.substring(5, color.length() - 1);
            try {
                double[] numArr = Arrays.stream(numbers.split(",")).mapToDouble(Double::parseDouble).toArray();
                for (int i = 0; i < numArr.length - 1; i++) if (numArr[i] < 0 || numArr[i] > 255) return false;
                if (numArr[numArr.length - 1] < 0 || numArr[numArr.length - 1] > 1) return false;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    //4 задача
    private String stripUrlParams(String site, String[] paramsToStrip) {
        if (!site.contains("?")) return site;
        String[] resultArr = site.substring(site.indexOf("?") + 1).split("&");
        String result = "";
        for (int i = 0; i < resultArr.length; i += 1) {
            for (int j = 0; j < paramsToStrip.length; j += 1) {
                if (resultArr[i].substring(0, resultArr[i].indexOf("=")).equals(paramsToStrip[j])) {
                    resultArr[i] = "";
                    break;
                }
            }

            for (int j = 0; j < i; j += 1) {
                if (resultArr[i].equals("")) break;
                String last = resultArr[j].substring(0, resultArr[j].indexOf("="));
                String now = resultArr[i].substring(0, resultArr[i].indexOf("="));
                if (now.equals(last)) {
                    resultArr[j] = resultArr[i];
                    resultArr[i] = "";
                    break;
                }
            }
        }
        for (int i = 0; i < resultArr.length; i += 1) {
            if (!resultArr[i].equals("")) {
                result += resultArr[i] + "&";
            }
        }
        result = result.charAt(result.length() - 1) == '&' ? result.substring(0, result.length() - 1) : result;
        return site.substring(0, site.indexOf("?") + 1) + result;
    }
    //5 задача
    private String getHashTags(String line) {
        String[] all = line.split(" ");
        String[] hashTags = new String[]{"", "", ""};
        //удалям знаки препинания
        for (int i = 0; i < all.length; i += 1) {
            int split = findSplitter(all[i]);
            if (split != -1) {
                all[i] = all[i].substring(0, split) + all[i].substring(split + 1);
            }
            all[i] = all[i].toLowerCase();
            if (all[i].length() > hashTags[0].length()) {
                hashTags[2] = hashTags[1];
                hashTags[1] = hashTags[0];
                hashTags[0] = all[i];
                continue;
            }
            if (all[i].length() > hashTags[1].length()) {
                hashTags[2] = hashTags[1];
                hashTags[1] = all[i];
                continue;
            }
            if (all[i].length() > hashTags[2].length()) {
                hashTags[2] = all[i];
            }
        }
        String result = "[";
        for (int i = 0; i < 3; i += 1) {
            if (!hashTags[i].equals("")) {
                result += "\"#" + hashTags[i] + "\", ";
            }
        }
        result = result.substring(0, result.length() - 1) + "]";
        return result;
    }
    //6 задача
    private int ulam(int n) {
        int[] seq = new int[n];
        seq[0] = 1;
        seq[1] = 2;
        int num = 3;
        for (int i = 2; i < n; i += 1) {
            int counter = 0;
            for (int j = 0; j < i; j += 1) {
                for (int k = j + 1; k < i; k += 1) {
                    if (seq[j] + seq[k] == num) counter += 1;
                }
            }
            if (counter == 1) {
                seq[i] = num;
            } else {
                i -= 1;
            }
            num += 1;
        }
        return seq[n - 1];
    }
    //7 задача
    private String longestNonrepeatingSubstring(String line) {

        String nonRepeat = "";
        String inWork = "";
        int index = 1;
        for (int i = 0; i < line.length(); i += 1) {
            String symbol = line.substring(i, i + 1);
            if (!inWork.contains(symbol)) {
                inWork += symbol;
            } else {
                nonRepeat = inWork.length() > nonRepeat.length() ? inWork : nonRepeat;
                inWork = "";
                i = index;
                index += 1;
            }
        }
        return nonRepeat.length() == 0 ? inWork : nonRepeat;
    }//8 задача
    private String converter(String[] convert, int figure) {
        switch (figure) {
            case 4:
                return convert[0] + convert[1];
            case 5:
                return convert[1];
            case 9:
                return convert[0] + convert[2];
        }
        if (1 <= figure && figure <= 3) {
            return convert[0].repeat(figure);
        }
        if (6 <= figure && figure <= 8) {
            return convert[1] + convert[0].repeat(figure - 5);
        }
        return "";
    }
    private String convertToRoman(int numberArabic) {
        String result = "";
        String[][] convert = new String[][]{{"I", "V", "X"}, {"X", "L", "C"}, {"C", "D", "M"}};
        String number = String.valueOf(numberArabic);
        int index = 0;

        for (int i = number.length() - 1; i >= 0; i -= 1) {
            if (index > 2) {
                result = "M".repeat(Integer.valueOf(number.substring(i, i + 1))) + result;
                break;
            }
            result = converter(convert[index], Integer.valueOf(number.substring(i, i + 1))) + result;
            index += 1;
        }
        return result;
    }
    private double solution(String[] solution) {
        double num = Double.valueOf(solution[0]);
        for (int j = 1; j < solution.length; j += 1) {
            switch (solution[j]) {
                case "+":
                    num += Double.valueOf(solution[j + 1]);
                    break;
                case "-":
                    num -= Double.valueOf(solution[j + 1]);
                    break;
                case "*":
                    num *= Double.valueOf(solution[j + 1]);
                    break;
                case "/":
                    num /= Double.valueOf(solution[j + 1]);
                    break;
            }
            j += 1;
        }
        return num;
    }
    //9 задача
    private boolean formula(String line) {
        String[] computation = line.split("=");
        double firstSolution = solution(computation[0].trim().split(" "));
        for (int i = 1; i < computation.length; i += 1) {
            double num = solution(computation[i].trim().split(" "));
            if (firstSolution != num){
                return false;
            }
        }
        return true;
    } //10 задача
    public static boolean palindromedescendant(long n){
        String number = Long.toString(n);
        String reverseNumber = new StringBuffer(number).reverse().toString();
        if (number.equals(reverseNumber)) return true;
        else {
            while (number.length() >= 2) {
                int[] nums = Arrays.stream(number.split("")).mapToInt(Integer::parseInt).toArray();
                number = "";
                for (int i = 0; i < nums.length - 1; i += 2){
                    nums[i] += nums[i + 1];
                    number += Integer.toString(nums[i]);
                }
                reverseNumber = new StringBuffer(number).reverse().toString();
                if (number.equals(reverseNumber) && number.length() >= 2) return true;
            }
        }
        return false;
    }
}

