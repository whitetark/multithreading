package schedule;

import java.util.ArrayList;
import java.util.UUID;

public class Student {
    private UUID id;
    public ArrayList<Mark> marks = new ArrayList<>();
    public Student(){
        id = UUID.randomUUID();
    }
    public String getId(){
        return id.toString();
    }
    public void print(){
        System.out.print("Student " + this.getId() + ": ");
    }
}
