package ONP;

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
                        } else if (c == '#') {
                            b = Double.parseDouble(stack.pop());
                            stack.push(String.valueOf(Math.sqrt(b)));
                        } else {
                            b = Double.parseDouble(stack.pop());
                            a = Double.parseDouble(stack.pop());
                            switch (c) {
                                case '+':
                                    stack.push(String.valueOf(a + b));
                                    break;
                                case '-':
                                    stack.push(String.valueOf(a - b));
                                    break;
                                case '*':
                                    stack.push(String.valueOf(a * b));
                                    break;
                                case '/':
                                    stack.push(String.valueOf(a / b));
                                    break;
                                case '^':
                                    stack.push(String.valueOf(Math.pow(a, b)));
                                    break;
                                case '%':
                                    stack.push(String.valueOf(a % b));
                                    break;
                                default:
                                    throw new UnexpectedOperatorException();
                            }
                        }
                    }
                }
                return "0.0";
            } else {
                throw new NoEqualsSignException();
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "Brak równania";
        } catch (NoEqualsSignException e) {
            System.out.println(e.getMessage());
            return "Brak znaku = w wyrażeniu";
        } catch (UnexpectedOperatorException e) {
            System.out.println(e.getMessage());
            return "Nieobsługiwany operator";
        } catch (EmptyStackException e) {
            System.out.println(e.getMessage());
            return "Stos jest pusty";
        } catch (StackOverflowError e) {
            System.out.println(e.getMessage());
            return "Przekroczono maksymalny rozmiar stosu";
        } catch (Exception e) {
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
                            case '+':
                            case '-': {
                                while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.push(String.valueOf(c));
                                break;
                            }
                            case '*':
                            case '/': {
                                while (stack.getSize() > 0 && "*/+-".contains(stack.showValue(stack.getSize() - 1))) {
                                    wynik += stack.pop() + " ";
                                }
                                stack.push(String.valueOf(c));
                                break;
                            }
                            case '^':
                            case '#':
                            case '%':
                            case '!': {
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
                            default:
                                throw new UnexpectedOperatorException();
                        }
                    }
                }
                return wynik;
            } else {
                throw new NoEqualsSignException();
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "Brak równania";
        } catch (NoEqualsSignException e) {
            System.out.println(e.getMessage());
            return "Brak znaku = w wyrażeniu";
        } catch (UnexpectedOperatorException e) {
            System.out.println(e.getMessage());
            return "Nieobsługiwany operator";
        } catch (EmptyStackException e) {
            System.out.println(e.getMessage());
            return "Stos jest pusty";
        } catch (StackOverflowError e) {
            System.out.println(e.getMessage());
            return "Przekroczono maksymalny rozmiar stosu";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Błąd konwersji ONP";
        }
    }
}