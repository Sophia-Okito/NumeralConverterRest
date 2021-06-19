package com.sophia.numeralconverterrest.service;

import lombok.Data;
import org.springframework.stereotype.Service;


@Data
@Service
public class ConversionRequestService {
    private String finalResult;
    public String conversion(int sourceBase, String num, int targetBase) {
        if (num.isEmpty()) {
            return "";
        }
        if (isBase(sourceBase, num)) {
            StringBuilder decimalNum = new StringBuilder();
            int dpLoc = num.indexOf(".");
            System.out.println(dpLoc);
            String intNum;
            String decNum = "";
            if (dpLoc >= 0) {
                intNum = num.substring(0, dpLoc);
                String deciNum = num.substring(dpLoc);
                decNum = decimalNum.append(deciNum).toString();
            } else
                intNum = num;


            String intResult = IntBaseConverter(sourceBase, intNum, targetBase);
            String decResult;
            if (!decNum.equals("")) {
                decResult = fractionBaseConverter(sourceBase, decNum, targetBase);
                finalResult = intResult + decResult;
            } else
                finalResult = intResult;
            finalResult += " = ";

            for (int i = 0; i < dpLoc; i++) {
                String string = String.format("(%c * %d^%d) ", intNum.charAt(i), targetBase, (intNum.length() - (i + 1)));
                finalResult += string;
                if (num.length() != dpLoc)
                    finalResult += "+ ";
            }

            if (!decNum.equals("")) {
                for (int i = 1; i < decNum.length(); i++) {
                    String string = String.format("(%c * %d^%d) ", decNum.charAt(i), targetBase, -i);
                    finalResult += string;
                    if (i != (decNum.length() - 1))
                        finalResult += "+ ";
                }
            }

                return finalResult;
        }

            return "";
    }

    private boolean isBase(int sourceBase, String num) {
        char numChar = (char) (sourceBase + '0');
        for(int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            if (c == '.')
                continue;
            if(c >= numChar)
                return false;
        }
        return true;
    }


    private String fractionBaseConverter(int sourceBase, String decNum, int targetBase) {
        double deciNumFloat = 0;
        if (sourceBase == 10) {
            deciNumFloat += Double.parseDouble(decNum);
        }else {
            for (int i = 1; i < decNum.length(); i++)
                deciNumFloat += (Character.getNumericValue(decNum.charAt(i)) / Math.pow(sourceBase, i));
        }

        StringBuilder decResult = new StringBuilder(".");
        for (int i = 0; i < 5; i++) {
            deciNumFloat *= targetBase;
            int number = (int) (deciNumFloat);
            deciNumFloat -= number;
            String number1 = Integer.toString(number, targetBase);
            decResult.append(number1);
        }
        return decResult.toString();
    }

    private String IntBaseConverter(int sourceBase, String num, int targetBase) {
        if (sourceBase == 1 && targetBase != 1) {
            long numlength = num.length();
            String numLengthString = Long.toString(numlength);
            if (sourceBase == 1) {
                long decNum = Long.parseLong(numLengthString, 10);
                return Long.toString(decNum, targetBase);
            } else {
                long numParse = Long.parseLong(num);
                return Long.toString(numParse);
            }
        } else if (targetBase == 1) {
            StringBuilder sb = new StringBuilder();
            int decNum = Integer.parseInt(num);
            sb.append("1".repeat(Math.max(0, decNum)));
            return sb.toString();
        } else {
            int decNum = Integer.parseInt(num, sourceBase);
            return Integer.toString(decNum, targetBase);
        }
    }

}
