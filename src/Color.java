/**
 * Перечисление Цвет
 * @author Matvei Baranov
 */
public enum Color{
    BLACK,BLUE,ORANGE,WHITE;
    /**
     * @param str строка для проверки
     * @return Проверка строки на валидность (входит в перечисление).
     */
    public static boolean validate_color(String str){
        try {
            if (!str.isEmpty()){
                Color color = Color.valueOf(str.toUpperCase());
                return true;
            }
            else
                return false;
        } catch(IllegalArgumentException e) {//InputMismatchException
            return false;
        }
    }
}
