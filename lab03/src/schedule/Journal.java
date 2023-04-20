package schedule;

import java.util.concurrent.locks.ReentrantLock;

public class Journal {
    public Group[] groups;
    public int numOfWeeks;
    private final ReentrantLock lock = new ReentrantLock();

    public Journal(Group[] groups, int numOfWeeks){
        this.groups = groups;
        this.numOfWeeks = numOfWeeks;
    }
    public void addMark(Mark mark, Student student) {
        lock.lock();
        try {
            student.marks.add(mark);
        } finally {
            lock.unlock();
        }
    }
    public void printJournal() {
        lock.lock();
        try {
            for(Group group: groups){
                System.out.println();
                group.print();
                System.out.println();
                for(Student student: group.students){
                    student.print();
                    System.out.println();
                    for(Mark mark: student.marks){
                        mark.print();
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
