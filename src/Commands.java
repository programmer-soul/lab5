import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.*;
import java.io.*;
import javax.xml.stream.*;
import java.time.format.DateTimeFormatter;
/**
 * Класс оперирует коллекцией и исполняет команды
 * @author Matvei Baranov
 */
public class Commands {
    //** Коллекция */
    private final LinkedHashMap<Integer,Person> person_collection;
    //** Дата/время инициализации */
    private final ZonedDateTime InitDateTime;
    //** Дата/время последнего сохранения */
    private ZonedDateTime lastSaveDateTime;
    //** Дата/время последней загрузки */
    private ZonedDateTime lastLoadDateTime;
    //** Открытый буфер для чтения файла скрипта */
    private BufferedReader scriptreader;
    //** Текущая строка скрипта */
    private String line;
    //** Массив строк для параметров добавления */
    private final String[] xmlstr =new String[11];
    //** Индекс в Массиве строк */
    private int xmlindex;
    private int historyconst = 14;
    //** Массив строк для истории */
    private final String[] historystr=new String[historyconst];
    //** Текущий размер истории */
    private int historysize=0;
    //** Индекс в истории */
    private int historyindex=0;
    /**
     * Конструктор класса
     */
    Commands(){
        person_collection=new LinkedHashMap<>();
        InitDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
    }
    /**
     * @param title подсказа для ввода
     * @param CanBeZero - может принимать значение нуля
     * @param CanBeNeg - может быть отрицательным
     * @return введённое с клавиатуры число типа int
     */
    public static int inputInt(String title, boolean CanBeZero, boolean CanBeNeg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                int n = scanner.nextInt();
                if (n == 0) {
                    if (CanBeZero)
                        return n;
                    else
                        System.out.println("Введите корректное значение (не нуль)!");
                }
                if (n<0) {
                    if (CanBeNeg)
                        return n;
                    else
                        System.out.println("Введите корректное значение (не отрицательное)!");
                }
                else {
                    return n;
                }
            } catch(InputMismatchException ex) {
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * @param title подсказа для ввода
     * @param CanBeZero - может принимать значение нуля
     * @param CanBeNeg - может быть отрицательным
     * @return введённое с клавиатуры число типа long
     */
    public static long inputLong(String title, boolean CanBeZero, boolean CanBeNeg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                long n = scanner.nextLong();
                if (n == 0) {                  if (CanBeZero)
                        return n;
                    else
                        System.out.println("Введите корректное значение (не нуль)!");
                }
                if (n<0) {
                    if (CanBeNeg)
                        return n;
                    else
                        System.out.println("Введите корректное значение (не отрицательное)!");
                }
                else {
                    return n;
                }
            } catch(InputMismatchException ex) {
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * @param title подсказа для ввода
     * @param CanBeZero - может принимать значение нуля
     * @return введённое с клавиатуры число типа float
     */
    public static float inputFloat(String title, boolean CanBeZero) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                float n = scanner.nextFloat();
                if (CanBeZero || n != 0)
                    return n;
                else
                    System.out.println("Введите корректное значение!-");
            } catch(InputMismatchException e) {
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * @param title подсказа для ввода
     * @param CanBeZero - может принимать значение нуля
     * @return введённое с клавиатуры число типа double
     */
    public static double inputDouble(String title, boolean CanBeZero) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                double n = scanner.nextDouble();
                if (CanBeZero || n != 0)
                    return n;
                else
                    System.out.println("Введите корректное значение!-");
            } catch(InputMismatchException e) {
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * @param title подсказа для ввода
     * @param CanBeZero строка может быть пустой
     * @return введённое с калвиатуры число типа String
     */
    public static String inputString(String title, boolean CanBeZero) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                String str = scanner.nextLine();
                if (CanBeZero || !str.isEmpty())
                    return str;
                else
                    System.out.println("Введите корректное значение!-");
            } catch(InputMismatchException e) {
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * @param title подсказа для ввода
     * @return введённый с клавиатуры цвет типа Color
     */
    public static Color inputColor(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        while (true) {
            try {
                String str = scanner.nextLine();
                if (!str.isEmpty())
                    return Color.valueOf(str.toUpperCase());
                else
                    System.out.println("Введите корректное значение!-");
            } catch(IllegalArgumentException e) {//InputMismatchException
                System.out.println("Введите корректное значение!");
            }
        }
    }
    /**
     * вывести в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public void Show(){
        Set<Map.Entry<Integer,Person>> set = person_collection.entrySet();// Получаем набор элементов
        for (Map.Entry<Integer, Person> me : set) {
            Person per = me.getValue();
            per.Show(); //Отображаем элементы
        }
    }
    /**
     * вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public void Info(){
        System.out.println("Сведения о коллекции:");
        System.out.println("  Тип коллекции: " + person_collection.getClass().getName());
        System.out.println("  Количество элементов: " + person_collection.size());
        System.out.println("  Дата/время последнего сохранения: " + lastSaveDateTime);
        System.out.println("  Дата/время последней загрузки: " + lastLoadDateTime);
        System.out.println("  Дата/время инициализации: " + InitDateTime);
    }
    /**
     * Сохраняет коллекцию в файл persons.xml.
     */
    private void Save(){
        Save("persons.xml");
    }
    /**
     * Сохраняет коллекцию в файл
     * @param filename имя файла
     */
    private void Save(String filename){
        try {
            lastSaveDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
            File xmlFile = new File(filename);
            OutputStream outputStream = new FileOutputStream(xmlFile);
            XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            out.writeStartDocument("UTF-8", "1.0");
            out.writeCharacters(System.getProperty("line.separator"));
            out.writeStartElement("Persons");
            out.writeCharacters(System.getProperty("line.separator"));
            Set<Map.Entry<Integer, Person>> set = person_collection.entrySet();// Получаем набор элементов
            for (Map.Entry<Integer, Person> me : set) {
                Person per = me.getValue();
                per.SaveXML(out);
            }
            out.writeEndElement();
            out.writeEndDocument();
            out.flush();
            out.close();
            outputStream.close();;
        } catch (Exception e) {
            System.out.println("Ошибка сохранения! "+e);
        }
    }
    /**
     * Загружает коллекцию из файла persons.xml.
     */
    private void Load(){
        Load("persons.xml");
    }
    /**
     * Загружает коллекцию из файла.
     * @param filename имя файла
     */
    private void Load(String filename){
        lastLoadDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
        Clear();
        try {
            File xmlFile = new File(filename);
            FileInputStream inputStream = new FileInputStream(xmlFile);
            BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
            bufferedStream.mark(bufferedStream.available( ));

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(bufferedStream);
            String currentElement;
            String currentText = "";
            String x="";
            String y="";
            String strid="";
            while(reader.hasNext()) {
                int next = reader.next();
                if(next == XMLStreamConstants.CHARACTERS){
                    currentText=reader.getText();
                }
                else
                if(next == XMLStreamConstants.END_ELEMENT){
                    currentElement = reader.getLocalName();
                    switch(currentElement){
                        case "id":
                            strid=currentText;
                            break;
                        case "name":
                            xmlstr[0]=currentText;
                            break;
                        case "x":
                            x=currentText;
                            break;
                        case "y":
                            y=currentText;
                            break;
                        case "Coordinates":
                            xmlstr[1]=x;
                            xmlstr[2]=y;
                            break;
                        case "creationDate":
                            xmlstr[10]=currentText;
                            break;
                        case "height":
                            xmlstr[3]=currentText;
                            break;
                        case "weight":
                            xmlstr[4]=currentText;
                            break;
                        case "passportID":
                            xmlstr[5]=currentText;
                            break;
                        case "eyeColor":
                            xmlstr[6]=currentText;
                            break;
                        case "z":
                            xmlstr[9]=currentText;
                            break;
                        case "Location":
                            xmlstr[7]=x;
                            xmlstr[8]=y;
                            break;
                        case "Person":
                            if (Person.validate_ID(strid)){
                                xmlindex=0;
                                Insert(Integer.parseInt(strid),false,false,false,true);
                            }
                            else{
                                System.out.println("XML Загрузка. Ошибка ID "+strid);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            reader.close();
            bufferedStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки! "+e);
        }
    }
    /**
     * Добавляет команду в историю
     * @param S команда.
     */
    private void AddToHistory(String S){
        if (historysize<historyconst){
            historysize++;
        }
        historystr[historyindex]=S;
        historyindex++;
        if (historyindex==historyconst){
            historyindex=0;
        }
    }
    /**
     * Выдаёт на экран последние 14 выполненных команд
     */
    private void History(){
        if (historysize<historyconst){
            for(int i=0;i<historysize;i++){
                System.out.println(historystr[i]);
            }
        }
        else{
            for(int i=historyindex;i<historyconst;i++){
                System.out.println(historystr[i]);
            }
            for(int i=0;i<historyindex;i++){
                System.out.println(historystr[i]);
            }

        }
    }
    /**
     * вывести сумму значения поля height для всех элементов коллекции
     */
    private void sum_of_height(){
        Set<Map.Entry<Integer, Person>> set = person_collection.entrySet();// Получаем набор элементов
        Iterator<Map.Entry<Integer, Person>> iter = set.iterator();// Получаем итератор
        long totalheight=0;
        while(iter.hasNext()) {
            Map.Entry<Integer, Person> me = iter.next();
            Person per=me.getValue();
            totalheight+=per.getHeight();
        }
        System.out.println("Сумма роста всех людей "+totalheight);
    }
    /**
     * вывести среднее значение поля weight для всех элементов коллекции
     */
    private void average_of_weight(){
        Set<Map.Entry<Integer,Person>> set = person_collection.entrySet();// Получаем набор элементов
        Iterator<Map.Entry<Integer,Person>> iter = set.iterator();// Получаем итератор
        long totalweight=0;
        while(iter.hasNext()) {
            Map.Entry<Integer,Person> me = iter.next();
            Person per=me.getValue();
            totalweight+=per.getWeight();
        }
        if (!person_collection.isEmpty()) {
            totalweight /= person_collection.size();
            System.out.println("Средний вес "+totalweight);
        }
        else{
            System.out.println("Средний вес 0");
        }
    }
    /**
     * @return максимальный id в коллекции
     */
    private Integer getMaxID(){
        Integer id=0;
        if (!person_collection.isEmpty()) {
            Set<Integer> setkeys = person_collection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            id = Arrays.stream(keys).max(Integer::compare).get();
        }
        return id;
    }
    /**
     * @param script - вызывается из скрипта
     * @return возвращает признак, был ли добавлен элемент
     */
    private boolean Insert(boolean script){
        return Insert(getMaxID()+1,script,false,false,false);
    }
    /**
     * @param id - уникальный номер (ключ) в коллекции
     * @param script - вызывается из скрипта
     * @param update - это обновление элемента
     * @param lower - replace_if_lower - заменить значение по ключу, если значение меньше заданного id
     * @param usexml - вызывается для загрузки XML
     * @return возвращает признак, был ли добавлен/изменён элемент с таким id
     */
    private boolean Insert(Integer id,boolean script,boolean update,boolean lower,boolean usexml){
        String name;
        long height;
        Integer weight;
        String passportID;
        Color eyeColor;
        double X;
        int Y;
        double locationX;
        float locationY;
        double locationZ;
        if (script || usexml){
            if (GetScriptLine(usexml) && Person.validate_name(line)){
                name=line;
            }
            else{
                System.out.println("Ошибка поля Имени");
                return false;
            }
            if (GetScriptLine(usexml) && Coordinates.validate_X(line)){
                X=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля Координата X");
                return false;
            }
            if (GetScriptLine(usexml) && Coordinates.validate_Y(line)){
                Y=Integer.parseInt(line);
            }
            else{
                System.out.println("Ошибка поля Координата Y");
                return false;
            }
            if (GetScriptLine(usexml) && Person.validate_height(line)){
                height=Long.parseLong(line);
            }
            else{
                System.out.println("Ошибка поля рост");
                return false;
            }
            if (GetScriptLine(usexml) && Person.validate_weight(line)){
                weight=Integer.parseInt(line);
            }
            else{
                System.out.println("Ошибка поля вес");
                return false;
            }
            if (GetScriptLine(usexml) && Person.validate_passportID(line)){
                passportID=line;
            }
            else{
                System.out.println("Ошибка поля Имени");
                return false;
            }
            if (GetScriptLine(usexml) && Color.validate_color(line.toUpperCase())){
                eyeColor = Color.valueOf(line.toUpperCase());
            }
            else{
                System.out.println("Ошибка поля цвет глаз");
                return false;
            }
            if (GetScriptLine(usexml) && Location.validate_X(line)){
                locationX=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }

            if (GetScriptLine(usexml) && Location.validate_Y(line)){
                locationY=Float.parseFloat(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }
            if (GetScriptLine(usexml) && Location.validate_Z(line)){
                locationZ=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }
        }
        else{
            name=inputString("Введите имя:",false);
            X=inputDouble("Введите координату X:",true);
            Y=inputInt("Введите координату Y:",true,true);
            height=inputLong("Введите рост:",false,false);
            weight=inputInt("Введите вес:",false,false);
            passportID=inputString("Введите серия-номер паспорта:",false);
            eyeColor=inputColor("Введите цвет глаз (BLACK,BLUE,ORANGE,WHITE):");
            locationX=inputDouble("Введите локацию X:",true);
            locationY=inputFloat("Введите локацию Y:",true);
            locationZ=inputDouble("Введите локацию Z:",true);
        }
        Coordinates coordinates=new Coordinates(X,Y);
        Location location=new Location(locationX,locationY,locationZ);
        ZonedDateTime creationDate = ZonedDateTime.now(ZoneOffset.systemDefault());
        if (usexml){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            creationDate = ZonedDateTime.parse(xmlstr[10],formatter);
        }
        if (!lower){
            Person per= new Person(id,name,coordinates,creationDate,height,weight,passportID,eyeColor,location);
            person_collection.put(per.getID(),per);
            if (update){
                System.out.print("Обновлена запись ");
            }else{
                System.out.print("Добавлена запись ");
            }
            per.Show();
        }
        else{
            if (!person_collection.isEmpty()) {
                Set<Integer> setkeys = person_collection.keySet();
                Integer[] keys = setkeys.toArray(new Integer[0]);
                for (Integer key : keys) {
                    if (key < id) {
                        Person per = new Person(key, name, coordinates, creationDate, height, weight, passportID, eyeColor, location);
                        person_collection.put(per.getID(), per);
                        System.out.print("Обновлена запись ");
                        per.Show();

                    }
                }
            }

        }
        return true;
    }
    /**
     * Очищает коллекцию
     */
    private void Clear(){
        person_collection.clear();
    }
    /**
     * Удалить из коллекции все элементы, ключ которых превышаюет заданный
     */
    private void remove_greater_key(Integer id){
        if (!person_collection.isEmpty()) {
            Set<Integer> setkeys = person_collection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            for (Integer key : keys) {
                if (key > id) {
                    person_collection.remove(key);
                }
            }
        }
    }
    /**
     * @param usexml для загрузки XML файла.
     * @return следующая строка скрипта обработана
     */
    private boolean GetScriptLine(boolean usexml){
        if (usexml){
            if (xmlindex<10){
                line= xmlstr[xmlindex++];
                return true;
            }
            else{
                line="";
                return false;
            }
        }
        else{
            try{
                if (line !=null){
                    line=scriptreader.readLine();
                    System.out.println(line);
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
    }
    /**
     * Cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме
     * @param filename файл скрипта.
     * @return возращает Истина если была команда exit и надо выйти.
     */
    private boolean execute_script(String filename){
        try{
            FileReader scriptfile = new FileReader(filename);
            scriptreader = new BufferedReader(scriptfile);
            line=scriptreader.readLine();
            while (line !=null)
            {
                System.out.println(line);
                if (Command(line,true)){
                    scriptfile.close();
                    return true;
                }
                line=scriptreader.readLine();
            }
            scriptreader.close();
            scriptfile.close();
            return false;
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /**
     * @param comstr команда для выполнения
     * @param script команда из скрипта
     * @return Найденный элемент (null если нен найден).
     */
    public boolean Command(String comstr,boolean script){
        AddToHistory(comstr);
        boolean enter=true;
        switch(comstr){
            case "exit":
                return true;
            case "help":
                System.out.println("help : вывести справку по доступным командам");
                System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
                System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
                System.out.println("insert {id} {element} : добавить новый элемент с заданным ключом");
                System.out.println("update {id} {element} : обновить значение элемента коллекции, id которого равен заданному");
                System.out.println("remove_key {id} : удалить элемент из коллекции по его ключу");
                System.out.println("clear : очистить коллекцию");
                System.out.println("save : сохранить коллекцию в файл (xml)");
                System.out.println("load : загрузить коллекцию из файла (xml)");
                System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
                System.out.println("exit : завершить программу (без сохранения в файл)");
                System.out.println("remove_greater_key {id} : удалить из коллекции все элементы, ключ которых превышаюет заданный");
                System.out.println("replace_if_lower {id} {element} : заменить значение по ключу, если значение меньше заданного");
                System.out.println("remove_greater_key {id} : удалить из коллекции все элементы, ключ которых превышает заданный");
                System.out.println("sum_of_height : вывести сумму значения поля height для всех элементов коллекции");
                System.out.println("average_of_weight : вывести среднее значение поля weight для всех элементов коллекции");
                System.out.println("max_by_id : вывести объект из коллекции с максимальным id");
                break;
            case "info":
                Info();
                break;
            case "show":
                Show();
                break;
            case "insert":
                if (!Insert(script)){
                    System.out.println("Запись не создана!");
                }
                break;
            case "clear":
                Clear();
                System.out.println("Все данные в коллекции удалены!");
                break;
            case "save":
                Save();
                break;
            case "load":
                Load();
                break;
            case "history":
                History();
                break;
            case "sum_of_height":
                sum_of_height();
                break;
            case "average_of_weight":
                average_of_weight();
                break;
            case "max_by_id":
                Integer maxID=getMaxID();
                if (maxID>0){
                    Person per=person_collection.get(maxID);
                    per.Show();
                }
                break;
            default:
                enter=false;
                break;
        }
        if (!enter){
            int i=comstr.indexOf(' ');
            if (i>=0){
                String pars=comstr.substring(i+1);
                comstr=comstr.substring(0,i);
                switch(comstr){
                    case "insert":
                        if (Person.validate_ID(pars)){
                            Insert(Integer.parseInt(pars),script,false,false,false);
                        }
                        else{
                            System.out.println("Ошибка ID");
                        }
                        break;
                    case "update":
                        if (Person.validate_ID(pars)){
                            Insert(Integer.parseInt(pars),script,true,false,false);
                        }
                        else{
                            System.out.println("Ошибка ID");
                        }
                        break;
                    case "remove_key":
                        if (Person.validate_ID(pars)){
                            person_collection.remove(Integer.parseInt(pars));
                        }
                        else{
                            System.out.println("Ошибка ID");
                        }
                        break;
                    case "replace_if_lower":
                        if (Person.validate_ID(pars)){
                            Insert(Integer.parseInt(pars),script,true,true,false);
                        }
                        else{
                            System.out.println("Ошибка ID");
                        }
                        break;
                    case "remove_greater_key":
                        if (Person.validate_ID(pars)){
                            remove_greater_key(Integer.parseInt(pars));
                        }
                        else{
                            System.out.println("Ошибка ID");
                        }
                        break;
                    case "execute_script":
                        if (script){
                            System.out.println("Скрипт уже исполняется");
                        }
                        else{
                            return execute_script(pars);
                        }
                        break;
                    case "save":
                        Save(pars);
                        break;
                    case "load":
                        Load(pars);
                        break;
                    default:
                        System.out.println("Неверная команда");
                        break;
                }
            }
            else{
                System.out.println("Неверная команда");
            }
        }
        return false;
    }
}
