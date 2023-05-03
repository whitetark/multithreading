package schedule;

public class Main {
    public static void main(String[] args) {
        Group it02 = new Group("IT-02", 2);
        Group it03 = new Group("IT-03", 2);
        Group it04 = new Group("IT-04", 2);
        Group[] groups = { it02, it03, it04 };

        Journal journal = new Journal(groups, 2);

        Teacher teacher1 = new Teacher(0, journal);
        Teacher assistant1 = new Teacher(1, journal);
        Teacher assistant2 = new Teacher(2, journal);
        Teacher assistant3 = new Teacher(3, journal);

        teacher1.start();
        assistant1.start();
        assistant2.start();
        assistant3.start();

        try {
            teacher1.join();
            assistant1.join();
            assistant2.join();
            assistant3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        journal.printJournal();
    }
}
