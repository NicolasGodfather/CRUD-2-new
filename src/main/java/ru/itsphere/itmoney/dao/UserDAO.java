package ru.itsphere.itmoney.dao;

import ru.itsphere.itmoney.domain.User;

import java.util.List;

/**
 * Этот интерфейс содержит все элементарные операции связанные с классом User
 * Создание, чтение, добавление, удаление и т. д.
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface UserDAO {
    /**
     * Возвращает пользователя по идентификатору
     *
     * @param id идентификатор
     * @return пользователь
     * @throws Exception
     */
    User getById(int id) throws Exception;

    /**
     * Сохраняет нового пользователя и возвращает его же но уже с идентификатором
     *
     * @param user новый пользователь
     * @return тот же пользователь но уже с айди
     * @throws Exception
     */
    User save(User user) throws Exception;

    /**
     * Обновление данных о пользователе
     *
     * @param user пользователь
     * @return тот же пользователь но уже обновленный
     * @throws Exception - если пользователь не существует
     */
    User update(User user) throws Exception;

    /**
     * Возвращает список всех пользователей
     *
     * @return список всех пользователей
     * @throws Exception
     */
    List<User> getAll() throws Exception;

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param id идентификатор
     * @throws Exception - если пользователь не существует
     */
    void deleteById(int id) throws Exception;
}
