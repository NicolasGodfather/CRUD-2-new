package ru.itsphere.itmoney.services;

import ru.itsphere.itmoney.domain.User;

import java.util.List;

/**
 * Этот интерфейс содержит все бизнес операции связанные с классом User
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface UserService {
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
     * @param user пользователь
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
     * Подсчитывает всех пользователей
     */
    int getCount();

    /**
     * Находит пользователя в поисковике
     * @param query строка запроса
     * @return Users которые соответсвуют запросу
     */
    List<User> findUsersByQuery(String query);

}
