package Patterns;

import Tokens.Reserved;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternBegin {
    String patternBegin;
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();

    public PatternBegin() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        patternBegin = "\\s*begin\\s*";
    }

    public boolean IsValidBegin(String conteudo){
        Pattern pattern = Pattern.compile(patternBegin);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}
