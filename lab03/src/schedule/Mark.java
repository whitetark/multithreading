package schedule;

public class Mark {
    private int mark;
    private String reason;
    public Mark(int mark, String reason){
        this.mark = mark;
        this.reason = reason;
    }
    public Mark(int mark){
        this.mark = mark;
        this.reason = "Lab";
    }
}
