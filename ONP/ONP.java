import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
*/
public class ONP {
    private TabStack stack = new TabStack();

    boolean czyPoprawneRownanie(String rownanie) {
        return rownanie.endsWith("=");
    }

    public String obliczOnp(String rownanie) {
        try {
            if (rownanie == null || rownanie.equals("null") || rownanie.equals("")) {
                throw new NoSuchElementException("Brak równania");
            }
            if (czyPoprawneRownanie(rownanie)) {
                stack.setSize(0);
                String wynik = "";
                Double a, b;

                for (int i = 0; i < rownanie.length(); i++) {
                    char c = rownanie.charAt(i);
                    if (Character.isDigit(c)) {
                        wynik += c;
                        if (i + 1 < rownanie.length() && !Character.isDigit(rownanie.charAt(i + 1))) {
                            stack.push(wynik);
                            wynik = "";
                        }
                    } else if (c == '=') {
                        return stack.pop();
                    } else if (c != ' ') {
                        if (stack.getSize() < (c == '!' || c == '#' ? 1 : 2)) {
                            throw new Exception("Zbyt malo operandow dla operacji: " + c);
                        }

                        if (c == '!') {
                            b = Double.parseDouble(stack.pop());
                            int cFactorial = 1;
                            for (int j = 1; j <= b; j++) {
                                cFactorial *= j;
                            }
                            stack.push(String.valueOf(cFactorial));
                        }
                        else if (c == '#') {
                            b = Double.parseDouble(stack.pop());
                            stack.push(String.valueOf(Math.sqrt(b)));
                        }
                        else {
                            b = Double.parseDouble(stack.pop());
                            a = Double.parseDouble(stack.pop());
                            switch (c) {
                                case '+': stack.push(String.valueOf(a + b)); break;
                                case '-': stack.push(String.valueOf(a - b)); break;
                                case '*': stack.push(String.valueOf(a * b)); break;
                                case '/': stack.push(String.valueOf(a / b)); break;
                                case '^': stack.push(String.valueOf(Math.pow(a, b))); break;
                                case '%': stack.push(String.valueOf(a % b)); break;
                                default: throw new UnexpectedOperatorException();
                            }
                        }
                    }
                }
                return "0.0";
            } else {
                throw new NoEqualsSignException();
            }
        }
        catch(NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "Brak równania";
        }
        catch (NoEqualsSignException e) {
            System.out.println(e.getMessage());
            return "Brak znaku = w wyrażeniu";
        }
        catch (UnexpectedOperatorException e) {
            System.out.println(e.getMessage());
            return "Nieobsługiwany operator";
        }
        catch (EmptyStackException e) {
            System.out.println(e.getMessage());
            return "Stos jest pusty";
        }
        catch (StackOverflowError e) {
            System.out.println(e.getMessage());
            return "Przekroczono maksymalny rozmiar stosu";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Błąd obliczeń";
        }
    }

    public String przeksztalcNaOnp(String rownanie) {
        try {
            if (rownanie == null || rownanie.equals("null") || rownanie.equals("")) {
                throw new NoSuchElementException("Brak równania");
            }
            if (czyPoprawneRownanie(rownanie)) {
                String wynik = "";
                for (int i = 0; i < rownanie.length(); i++) {
                    char c = rownanie.charAt(i);
                    if (Character.isDigit(c)) {
                        wynik += c;
                        if (i + 1 < rownanie.length() && !Character.isDigit(rownanie.charAt(i + 1))) {
                            wynik += " ";
                        }
                    } else {
                        switch (c) {
                            case '+': case '-': {
                                while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.push(String.valueOf(c));
                                break;
                            }
                            case '*': case '/': {
                                while (stack.getSize() > 0 && "*/+-".contains(stack.showValue(stack.getSize() - 1))) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.push(String.valueOf(c));
                                break;
                            }
                            case '^': case '#': case '%': case '!': {
                                while (stack.getSize() > 0 && "^#%!".contains(stack.showValue(stack.getSize() - 1))) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.push(String.valueOf(c));
                                break;
                            }
                            case '(':
                                stack.push(String.valueOf(c));
                                break;
                            case ')': {
                                while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.pop(); // Remove '('
                                break;
                            }
                            case '=': {
                                while (stack.getSize() > 0) {
                                    wynik += stack.pop() + " ";
                                }
                                wynik += "=";
                                break;
                            }
                            default: throw new UnexpectedOperatorException();
                        }
                    }
                }
                return wynik;
            } else {
                throw new NoEqualsSignException();
            }
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "Brak równania";
        }
        catch (NoEqualsSignException e) {
            System.out.println(e.getMessage());
            return "Brak znaku = w wyrażeniu";
        }
        catch (UnexpectedOperatorException e) {
            System.out.println(e.getMessage());
            return "Nieobsługiwany operator";
        }
        catch (EmptyStackException e) {
            System.out.println(e.getMessage());
            return "Stos jest pusty";
        }
        catch (StackOverflowError e) {
            System.out.println(e.getMessage());
            return "Przekroczono maksymalny rozmiar stosu";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Błąd konwersji ONP";
        }
    }


public static void main(String[] args) {
        // 7 1 + 4 2 - 2 ^ * =
        String tmp1 = "";
        String tmp2 = "";
        String tmp3 = "";
        String tmp4 = "";
        String tmp5 = "";
        String tmp6 = "";
        if (args.length == 0) {
            tmp1 = "(2+3)*(6-2)^2=";
            tmp2 = "(7+1)*((4-2)^2)=";
            tmp3 = "(2+3)*(2-3)+8=";
            tmp4 = "(4+5)*3!=";
            tmp5 = "(#4+5)*3=";
            tmp6 = "(22+3)/(2-34)*3+8)=";
        } else {
            for (int i = 0; i < args.length; i++) {
                tmp1 += args[i];
            }
        }
        ONP onp = new ONP();
        System.out.print(tmp1 + " ");
        String rownanieOnp1 = onp.przeksztalcNaOnp(tmp1);
        System.out.print(rownanieOnp1);
        String wynik1 = onp.obliczOnp(rownanieOnp1);
        System.out.println(" " + wynik1);

        System.out.print(tmp2 + " ");
        String rownanieOnp2 = onp.przeksztalcNaOnp(tmp2);
        System.out.print(rownanieOnp2);
        String wynik2 = onp.obliczOnp(rownanieOnp2);
        System.out.println(" " + wynik2);

        System.out.print(tmp3 + " ");
        String rownanieOnp3 = onp.przeksztalcNaOnp(tmp3);
        System.out.print(rownanieOnp3);
        String wynik3 = onp.obliczOnp(rownanieOnp3);
        System.out.println(" " + wynik3);

        System.out.print(tmp4 + " ");
        String rownanieOnp4 = onp.przeksztalcNaOnp(tmp4);
        System.out.print(rownanieOnp4);
        String wynik4 = onp.obliczOnp(rownanieOnp4);
        System.out.println(" " + wynik4);

        System.out.print(tmp5 + " ");
        String rownanieOnp5 = onp.przeksztalcNaOnp(tmp5);
        System.out.print(rownanieOnp5);
        String wynik5 = onp.obliczOnp(rownanieOnp5);
        System.out.println(" " + wynik5);

        System.out.print(tmp6 + " ");
        String rownanieOnp6 = onp.przeksztalcNaOnp(tmp6);
        System.out.print(rownanieOnp6);
        String wynik6 = onp.obliczOnp(rownanieOnp6);
        System.out.println(" " + wynik6);

    }
}