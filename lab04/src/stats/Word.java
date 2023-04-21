package stats;

public class Word {
    public String value;
    public int length;
    Word(String value){
        this.value = value;
        this.length = value.length();
    }
}
