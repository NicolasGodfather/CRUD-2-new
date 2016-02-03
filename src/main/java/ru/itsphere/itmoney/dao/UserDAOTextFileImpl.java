package ru.itsphere.itmoney.dao;

import ru.itsphere.itmoney.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Реализация UserDAO для работы через текстовый файл
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserDAOTextFileImpl implements UserDAO {

    public static final String SEPARATOR = "/";
    public static final String CHARSET_NAME = "UTF-8";
    public final String FILE_NAME;

    public UserDAOTextFileImpl(String filePath) {
        FILE_NAME = filePath;
    }

    @Override
    public User getById(int id) throws Exception {
        // Находим необходимую строку которая представляет пользователя с идентифик кот пришел в метод
        String[] splittedLine = getSplittedLineById(id);
        // если строки нет, то возвр null
        if (splittedLine == null) {
            return null;
        }
        // если строка есть то извлекаем из нее имя и идентиф и созд юзера
        return new User(getId(splittedLine), getName(splittedLine));
    }

    private int getId(String[] splittedLine) {
        // преобразуем строку(идентификатор) в тип int
        return Integer.parseInt(splittedLine[0]);
    }

    private String getName(String[] splittedLine) {
        // получаем имя оно хранится во 2 элементе массива
        return splittedLine[1];
    }

    private String[] getSplittedLineById(int id) throws Exception {
        // созд obj class LineNumberReader кот будет построчно вычитывать строки из файла
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET_NAME);
             LineNumberReader reader = new LineNumberReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                // если 1 строка - то прервать эту итерацию и начать след. т.к. 1 строка - шапка
                if (reader.getLineNumber() == 1) {
                    continue;
                }
                // каждую строку разбиваем на части - на выходе получ массив строк
                String[] splittedLine = line.split(SEPARATOR);
                int lineId = getId(splittedLine);
                // check относится ли данная строка к пользователю с идентиф. равным id
                if (lineId == id) {
                    return splittedLine;
                }
            }
            // если цикл закончился значит соответствия строки из файла и переданного идентиф.
            // не было найдено - вернем null
            return null;
        }
    }

    @Override
    public User update(User user) throws Exception {
        // получаем строки файла в виде списка и заменяем в нем строки
        List<String> lines = replaceWithNewData(user, getLinesOfFile());
        // перезаписываем файл
        updateFile(lines);
        // возвр обновленного пользователя
        return user;
    }

    private List<String> getLinesOfFile() throws Exception {
        List<String> lines = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET_NAME);
             LineNumberReader reader = new LineNumberReader(inputStreamReader)) {
            String line;
            // цикл - где переберается каждая строка файла
            // на каждой итерации line новую строку
            while ((line = reader.readLine()) != null) {
                //и сохраняем
                lines.add(line);
            }
        }
        return lines;
    }

    private int getUserIndexInLines(int id, List<String> lines) {
        // получили итератор с его помощью перебираем строки, и сразу пропускаем шапку
        ListIterator<String> iterator = lines.listIterator(1);
        while (iterator.hasNext()) {
            // получаем текущую строку
            String line = iterator.next();
            // забиваем на массив строк
            String[] splittedLine = line.split(SEPARATOR);
            // из массива строк получ id и сравниваем с его идентификатором пользователя кот. собираемся изменить
            if (getId(splittedLine) == id) {
                // если идентификаторы равны то это та строка кот ищем - возвращаем ее индекс
                return iterator.nextIndex() - 1;
            }
        }
        // если цикл завершился то это значит что в списке юзера нет
//        return -1;
        throw new RuntimeException("The user was not found");
    }

    private List<String> replaceWithNewData(User user, List<String> lines) {
        // определяем нужного пользователя в списке
        int userIndexInFile = getUserIndexInLines(user.getId(), lines);
        // if user not exist in list it does return unmodified list
        if (userIndexInFile < 0)
            return lines;
        // удаляем старую строку
        lines.remove(userIndexInFile);
        // создаем нов и ставим на место старой
        lines.add(userIndexInFile, createNewLineForFile(user));
        // возвращаем измененный список
        return lines;
    }

    private String createNewLineForFile(User user) {
        return user.getId() + SEPARATOR + user.getName();
    }

    private void updateFile(List<String> lines) throws Exception {
        // append = false - означает что писать будем в начало файла
        // append = true - означает что писать будем в конец файла (старые данные не затрутся)
        boolean append = false;
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, append);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET_NAME);
             PrintWriter writer = new PrintWriter(outputStreamWriter)) {
            // каждую строку последовательно записываем в файл
            // лямбда
            lines.forEach(writer::println);
        }
    }
    //    @Override
//    public User save(User user) throws Exception {
//        List<String> lines = getLinesOfFile();
//        int maxId = 0;
//        if (lines.isEmpty()) {
//            throw new RuntimeException("Store of users is damaged");
//        }
//        if (lines.size() != 1) {
//            ListIterator<String> iterator = lines.listIterator(1);
//            while (iterator.hasNext()) {
//                int currentId = getId(iterator.next().split(SEPARATOR));
//                if (maxId < currentId) {
//                    maxId = currentId;
//                }
//            }
//        }
//        User generatedUser = new User(++maxId, user.getName());
//        lines.add(createNewLineForFile(generatedUser));
//        updateFile(lines);
//        return generatedUser;
//    }
    @Override
    public User save(User user) throws Exception {
        List<String> lines = getLinesOfFile();
        ListIterator<String> iterator = lines.listIterator(1);
        if (lines.isEmpty()) {
            throw new RuntimeException("Store of users is damaged");
        }
        if (lines.size() != 1) {
            user.setId(generateMaxId(lines) + 1);
        }
        lines.add(createNewLineForFile(user));
        updateFile(lines);
        return user;
    }
    private Integer generateMaxId(List<String> list) {
        int maxId = 0;
        for (String lines : list) {
            ListIterator<String> iterator = list.listIterator(1);
            while (iterator.hasNext()) {
                int currentId = getId(iterator.next().split(SEPARATOR));
                if (maxId < currentId) {
                    maxId = currentId;
                }
            }
        }
        return maxId;
    }

    @Override
    public List<User> getAll() throws Exception {
        List<User> result = new ArrayList<>();
        ListIterator<String> iterator = getLinesOfFile().listIterator(1);
        while (iterator.hasNext()) {
            result.add(convertToUser(iterator.next()));
        }
        return result;
    }
    private User convertToUser(String userLine) {
        String[] splittedLine = userLine.split(SEPARATOR);
        return new User(getId(splittedLine), getName(splittedLine));
    }

    @Override
    public void deleteById(int id) throws Exception {
        List<String> lines = getLinesOfFile();
        int userIndexInLines;
        try {
            userIndexInLines = getUserIndexInLines(id, lines);
        } catch (RuntimeException e){
            throw new RuntimeException("Can not delete, user not exist");
        }
        lines.remove(userIndexInLines);
        updateFile(lines);
    }
}
