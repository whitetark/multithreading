package schedule;

import java.util.ArrayList;

public class Group {
    public String name;
    public ArrayList<Student> students = new ArrayList<>();

    public Group(String name, int countStudents){
        this.name = name;
        generateGroup(countStudents);
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public int getCapacity(){
        return students.size();
    }
    public void print(){
        System.out.print("Group " + name + ": ");
    }
    private void generateGroup(int countStudents){
        for(int i = 0; i < countStudents;i++){
            students.add(new Student());
        }
    }
}
