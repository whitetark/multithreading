package schedule;

public class Teacher extends Thread {
    private int id;
    private Journal journal;
    public Teacher(int id, Journal journal, int priority){
        this.id = id;
        this.journal = journal;
        switch(priority){
            case -1:
                setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                setPriority(Thread.MAX_PRIORITY);
                break;
            default:
                setPriority(Thread.NORM_PRIORITY);
                break;
        }
    }
    @Override
    public void run() {
        try {
            int week = 1;
            while(week <= journal.numOfWeeks){
                for(Group group: journal.groups){
                    for(Student student: group.students){
                        Mark mark = new Mark((int) (Math.random() * 100),week, this.id);
                        journal.addMark(mark, student);
                    }
                    System.out.println(id + " has graded group " + group.name);
                }
                System.out.println(id + " has graded week " + week);
                week++;
            }
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public long getId(){
        return id;
    }
}
