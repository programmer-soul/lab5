import javax.xml.stream.*;
/**
 * Класс Координаты
 * @author Matvei Baranov
 */
public class Coordinates {
    private double x;
    private Integer y;
    /**
     * Конструктор класса
     */
    public Coordinates(double x,Integer y){
        this.x=x;
        this.y=y;
    }
    /**
     * @return возвращает координаты строкой
     */
    public String toString()
    {
        return "("+x+";"+y+")";
    }
    /**
     * Сохраняет свой данные в открытый стрим в формате XML
     * @param out открытый стрим для записи
     */
    public void SaveXML (XMLStreamWriter out){
        try {
            out.writeStartElement("Coordinates");

            out.writeStartElement("x");
            out.writeCharacters(Double.toString(x));
            out.writeEndElement();

            out.writeStartElement("y");
            out.writeCharacters(Integer.toString(y));
            out.writeEndElement();

            out.writeEndElement();
        }
        catch (XMLStreamException e) {
            System.out.println("Ошибка сохранения! "+e);
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность координаты X.
     */
    public static boolean validate_X(String S){
        try
        {
            Double num = Double.parseDouble(S);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность координаты Y.
     */
    public static boolean validate_Y(String S){

        try
        {
            Integer num = Integer.parseInt(S);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
