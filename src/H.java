public class H {

    public static int getH(State state) {
        String hash = state.hash();
        int black_and_red = countChar(hash, 'r') + countChar(hash, 'b');
        return black_and_red;
    }
    
    public static int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
                count++;
        }

        return count;
    }
}