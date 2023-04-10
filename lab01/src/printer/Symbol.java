package printer;

public class Symbol {
    private char character;

    public Symbol(char character) {
        this.character = character;
    }

    public void print() {
        System.out.print(character);
    }
}
