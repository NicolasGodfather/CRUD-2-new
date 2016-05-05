package ru.itsphere.itmoney.dao;

import ru.itsphere.itmoney.domain.User;

import java.util.List;

/**
 * Этот интерфейс содержит все элементарные операции связанные с классом User
 * Создание, чтение, добавление, удаление и т. д.
 */
public interface UserDAO {
    /**
     * Возвращает пользователя по идентификатору
     *
     * @param id идентификатор
     * @return пользователь
     */
    User getById(int id);

    /**
     * Сохраняет нового пользователя
     *
     * @param user новый пользователь
     */
    void save(User user);

    /**
     * Обновление данных о пользователе
     *
     * @param user пользователь
     */
    void update(User user);

    /**
     * Возвращает список всех пользователей
     *
     * @return список всех пользователей
     */
    List<User> getAll();

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param id идентификатор
     */
    void deleteById(int id);

    /**
     * Находит пользователя в поисковике
     * @param query строка запроса
     * @return Users которые соответсвуют запросу
     */
    List<User> findUsersByQuery(String query);

    /**
     * Подсчитывает всех пользователей
     */
    int getCount();

}
