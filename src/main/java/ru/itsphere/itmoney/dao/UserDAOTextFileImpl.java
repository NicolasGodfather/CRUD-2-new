package ru.itsphere.itmoney.dao;

import ru.itsphere.itmoney.domain.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        String[] splittedLine = getSplittedLineById(id);
        if (splittedLine == null) {
            return null;
        }
        return new User(getId(splittedLine), getName(splittedLine));
    }

    @Override
    public User update(User user) throws Exception {
        List<String> lines = replaceWithNewData(user, getLinesOfFile());
        updateFile(lines);
        return user;
    }

    private String getName(String[] splittedLine) {
        return splittedLine[1];
    }

    private int getId(String[] splittedLine) {
        return Integer.parseInt(splittedLine[0]);
    }

    private String[] getSplittedLineById(int id) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET_NAME);
             LineNumberReader reader = new LineNumberReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (reader.getLineNumber() == 1) {
                    continue;
                }
                String[] splittedLine = line.split(SEPARATOR);
                int lineId = getId(splittedLine);
                if (lineId == id) {
                    return splittedLine;
                }
            }
            return null;
        }
    }

    private void updateFile(List<String> lines) throws Exception {
        boolean append = false;
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, append);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET_NAME);
             PrintWriter writer = new PrintWriter(outputStreamWriter);) {
            lines.forEach(writer::println);
        }
    }

    private List<String> replaceWithNewData(User user, List<String> lines) {
        int userIndexInFile = getUserIndexInLines(user.getId(), lines);
        lines.remove(userIndexInFile);
        lines.add(userIndexInFile, createNewLineForFile(user));
        return lines;
    }

    private String createNewLineForFile(User user) {
        return user.getId() + SEPARATOR + user.getName();
    }

    private List<String> getLinesOfFile() throws Exception {
        List<String> lines = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET_NAME);
             LineNumberReader reader = new LineNumberReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private int getUserIndexInLines(int id, List<String> lines) {
        ListIterator<String> iterator = lines.listIterator(1);
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] splittedLine = line.split(SEPARATOR);
            if (getId(splittedLine) == id) {
                return iterator.nextIndex() - 1;
            }
        }
        throw new RuntimeException("The user was not found");
    }

    @Override
    public List<User> getAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public User save(User user) throws Exception {
        return user;
    }

    @Override
    public void deleteById(int id) throws Exception {
    }
}
