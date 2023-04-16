package schedule;

import java.util.UUID;

public class Student {
    private UUID id;
    private Mark[] marks;
    public Student(){
        id = UUID.randomUUID();
    }
}
