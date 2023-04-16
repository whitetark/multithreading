package schedule;

public class Group {
    private String name;
    private Student[] students;

    public Group(String name){
        this.name = name;
        this.students = generateGroup();
    }
    private Student[] generateGroup(){
        Student[] result = new Student[10];
        return result;
    }
}
