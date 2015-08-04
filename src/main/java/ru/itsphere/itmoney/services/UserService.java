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
     * @throws Exception
     */
    User getById(int id) throws Exception;

    /**
     * Сохраняет нового пользователя
     *
     * @param user пользователь
     * @return сохраненный пользователь
     * @throws Exception
     */
    User save(User user) throws Exception;

    /**
     * Обновление данных о пользователе
     *
     * @param user пользователь
     * @return тот же пользователь но уже обновленный
     * @throws Exception
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
