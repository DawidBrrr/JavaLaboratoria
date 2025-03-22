import java.util.EmptyStackException;

/**
 * Klasa implementująca stos za pomocą tablicy
 */
public class TabStack {
    private static final int MAX_SIZE = 20;
    private String[] stack = new String[MAX_SIZE];
    private int size = 0;
    /**
     * Metoda zdejmująca wartość ze stosu
     * @return wartość z góry stosu
     */
    public String pop(){
        if (size == 0) {
            throw new EmptyStackException();
        }
        size--;
        return stack[size];
    }
    /**
     * metoda dokładająca na stos
     * @param a - wartość dokładana do stosu
     */
    public void push(String a){
        if (size == MAX_SIZE) {
            throw new StackOverflowError("Przekroczono maksymalny rozmiar stosu");
        }
        stack[size] = a;
        size++;
    }
    /**
     * Metoda zwraca tekstową reprezentację stosu
     */
    public String toString(){
        String tmp = "";
        for(int i = 0; i < size; i++)
            tmp += stack[i] + " ";
        return tmp;
    }
    /**
     * Metoda zwraca rozmiar stosu
     * @return size rozmiar stosu
     */
    public int getSize(){
        return size;
    }
    /**
     * Metoda zwraca maksymalny rozmiar stosu
     * @return MAX_SIZE maksymalny rozmiar stosu
     */
    public int getMaxSize(){
        return MAX_SIZE;
    }
    /**
     * Ustawia wartość stosu
     * @param i
     */
    public void setSize(int i){
        size = i;
    }
    /**
     * Metoda zwraca wartość z określonej pozycji stosu
     * @param i pozycja parametru do zobaczenia
     * @return wartość stosu
     */
    public String showValue(int i){
        if(i < size)
            return stack[i];
        else return null;
    }
}