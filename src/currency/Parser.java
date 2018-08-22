package currency;

import logger.Logger;

public class Parser {
	
	protected Logger logger  = new Logger( this.getClass() );
	
    //  Объявление лексем
    final int NONE = 0;         //  FAIL
    final int DELIMITER = 1;    //  Разделитель(+-*/^=, ")", "(" )
    final int FUNCTION = 2;     //  Переменная
    final int NUMBER = 3;       //  Число
    
    //  Объявление констант синтаксических ошибок
    final int SYNTAXERROR = 0;  //  Синтаксическая ошибка (10 + 5 6 / 1)
    final int UNBALPARENS = 1;  //  Несовпадение количества открытых и закрытых скобок
    final int NOEXP = 2;        //  Отсутствует выражение при запуске анализатора
    final int UNKNOWNFUNC = 5;
    
    //  Лексема, определяющая конец выражения
    final String EOF = "\0";
    
    private String exp;     //  Ссылка на строку с выражением
    private int explds;     //  Текущий индекс в выражении
    private String token;   //  Сохранение текущей лексемы
    private int tokType;    //  Сохранение типа лексемы
    
    
    public String toString() {
        return String.format("Exp = {0}\nexplds = {1}\nToken = {2}\nTokType = {3}", exp.toString(), explds,
                token.toString(), tokType);
    }
    
    //  Получить следующую лексему
    private void getToken() {
        tokType = NONE;
        token = "";
        
        //  Проверка на окончание выражения
        if(explds == exp.length()){
            token = EOF;
            return;
        }
        //  Проверка на пробелы, если есть пробел - игнорируем его.
        while(explds < exp.length() && Character.isWhitespace(exp.charAt(explds))) 
            ++ explds;
        //  Проверка на окончание выражения
        if(explds == exp.length()){
            token = EOF;
            return;
        }
        if(isDelim(exp.charAt(explds))){
            token += exp.charAt(explds);
            explds++;
            tokType = DELIMITER;
        }
        else if(Character.isLetter(exp.charAt(explds))){
            while(!isDelim(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length())
                    break;
                }
            tokType = FUNCTION;
        }
        else if (Character.isDigit(exp.charAt(explds)) || (exp.charAt(explds)=='$')){
            while(!isDelim(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length())
                    break;
                }
            tokType = NUMBER;
        }
        else {
            token = EOF;
            return;
        }
    }
 
    private boolean isDelim(char charAt) {
        if((" +-=()".indexOf(charAt)) != -1)
            return true;
        return false;
    }
 
    //  Точка входа анализатора
    public Currency evaluate(String expstr) throws ParserException, CurrencyException {
        
        Currency result;
        
        exp = expstr;
        explds = 0;
        getToken();
        
        if(token.equals(EOF))
            handleErr(NOEXP);   //  Нет выражения
 
        //  Анализ и вычисление выражения
        result = evalExp2();
        
        if(!token.equals(EOF))
            handleErr(SYNTAXERROR);
        
        logger.logInfo(expstr + " = " + result);

        return result;
    }
    
    //  Сложить или вычислить два терма
    private Currency evalExp2() throws ParserException, CurrencyException {
        
        char op;
        Currency result;
        Currency partialResult;
        result = evalExp4();
        while((op = token.charAt(0)) == '+' || 
                op == '-'){
            getToken();
            partialResult = evalExp4();
            switch(op){
                case '-':
                	result = CurrencyUtils.Subtract(result, partialResult);
                    break;
                case '+':
                	result = CurrencyUtils.Add(result, partialResult);
                    break;
            }
        }
        return result;
    }
    
    //  Выполнить возведение в степень
    private Currency evalExp4() throws ParserException, CurrencyException {
        
    	Currency result;
    	Currency partialResult;
        String funcName = new String(token);
        
        if(tokType == FUNCTION) {
            getToken();
            partialResult = evalExp6();
            switch(funcName) {
            	case "toDollar":
//            		System.out.println("Ok! " + funcName + "(" + partialResult + ")");
            		return CurrencyUtils.convert(partialResult, "$");
            	case "toEuro":
//            		System.out.println("Ok! " + funcName + "(" + partialResult + ")");
            		return CurrencyUtils.convert(partialResult, "eur");
            	default:
            		handleErr(SYNTAXERROR);
            }
        }
        result = evalExp5();

        return result;
    }
 
    //  Определить унарные + или -
    private Currency evalExp5() throws ParserException, CurrencyException {
    	Currency result;
        
        String op;
        op = " ";
        
        if((tokType == DELIMITER) && token.equals("+") ||
            token.equals("-")){
            op = token;
            getToken();
        }
        result = evalExp6();
        if(op.equals("-"))
        	result.setValue(-result.getValue());

        return result;
    }
    
    //  Обработать выражение в скобках
    private Currency evalExp6() throws ParserException, CurrencyException {
    	Currency result;
        
        if(token.equals("(")){
            getToken();
            result = evalExp2();
            if(!token.equals(")"))
                handleErr(UNBALPARENS);
            getToken();
        }
        else
            result = atom();
        return result;
    }
    
    //  Получить значение числа
    private Currency atom()   throws ParserException, CurrencyException {
        
    	Currency result = null;
        String buffer;
        
    	
        switch(tokType){
            case NUMBER:
                try{
                	String ctype; 
                	if(token.charAt(0) == '$') {
                		ctype = token.substring(0, 1);
                		buffer = token.substring(1);
                	}
                	else {
                		ctype = token.substring(token.length()-3);
                		buffer = token.substring(0, token.length()-3);
                	}
                	
              		Currency.checkType(ctype);
               		result = new Currency(ctype, Double.parseDouble(buffer));
                }
                catch(NumberFormatException exc){
                    handleErr(SYNTAXERROR);
                } 
                catch (CurrencyException exc) {
                	throw new ParserException(exc.getLocalizedMessage());
				}
                catch(Exception e) {
                	throw new ParserException("Error parsing of: " + token);
                }
                getToken();
    
            break;
            default:
                handleErr(SYNTAXERROR);
                break;
        }
        return result;
    }
    
    //  Кинуть ошибку
    private void handleErr(int nOEXP2) throws ParserException {
        
        String[] err  = {
                "Syntax error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by zero",
                "Unknown function"
        };
        throw new ParserException(err[nOEXP2]);
    }

}
