import java.util.UUID;

/**
 * Creates a unique int ID
 * @auther DeForestt Thompson
 * @version 1.0
 */
public class GenID {

    /**
     * Creates a unique int ID
     * @return unique int ID
     */
    public static int POP() {
        UUID idOne = UUID.randomUUID();
        String str = "" + idOne;
        int uid = str.hashCode();
        String filterStr = "" + uid;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
}
