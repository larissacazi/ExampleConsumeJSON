package zimmermann.larissa.legislativoteste.rest;

/**
 * Created by gms19 on 06/08/2017.
 */

public class StringUtils {
    public String format(String in){
        if(in.toUpperCase() == in){
            in = in.toLowerCase();
        }

        int pos = 0;
        boolean capitalize = true;
        StringBuilder sb = new StringBuilder(in);

        while (pos < sb.length()) {
            if (sb.charAt(pos) == '.') {
                capitalize = true;
            } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
                capitalize = false;
            }
            pos++;
        }

        return sb.toString();
    }
}
