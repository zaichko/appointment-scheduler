# Appointment Scheduler

REST API для записи пациентов к врачам: пользователи, профили врачей, специализации, слоты расписания, бронирование и отмена приёмов, поиск свободных слотов.

## Стек

Java 17 · Spring Boot 4 · Spring Data JPA / Hibernate · PostgreSQL 16 · Flyway · Docker / Docker Compose · Maven

## Основные решения

- **Целостность данных на уровне БД.** Слоты одного врача не могут пересекаться (`EXCLUDE USING gist` по `tstzrange`), а один слот нельзя занять дважды (частичный уникальный индекс по активным записям). Защита работает и при одновременных запросах.
- **Optimistic locking** (`@Version`) на слотах защищает от конкурентного изменения времени слота.
- **Пациент блокируется на время бронирования**, поэтому проверка пересечения его записей по времени не даёт гонки.
- **Миграции Flyway**, схема валидируется Hibernate (`ddl-auto=validate`).
- **Многослойная архитектура** Controller → Service → Repository, DTO и мапперы, валидация запросов, единая обработка ошибок через `@RestControllerAdvice`.
- Пароли хешируются BCrypt.

## Запуск

Docker:

```bash
cp .env.example .env      # впишите свой DB_PASSWORD
docker compose up --build
```

API доступно на `http://localhost:8080`. PostgreSQL снаружи доступен на порту `5433`.

Остановка: `docker compose down` (`-v` дополнительно удаляет данные БД).

### Запуск без Docker

Нужны Java 17 и PostgreSQL с базой `appointment_scheduler`:

```bash
export DB_PASSWORD=your_password
./mvnw spring-boot:run
```

## Эндпоинты

| Ресурс | Метод и путь | Описание |
|---|---|---|
| Пользователи | `GET/POST /users`, `GET/PUT/DELETE /users/{id}` | CRUD пользователей |
| Врачи | `GET/POST /doctors`, `GET/PUT/DELETE /doctors/{id}`, `PATCH /doctors/{id}/update_specialities` | Профили врачей и их специализации |
| Специализации | `GET/POST /specialities`, `GET/PUT/DELETE /specialities/{id}`, `PATCH /specialities/{id}/status` | Справочник специализаций |
| Слоты | `GET /time-slots`, `GET /time-slots/{id}`, `POST /time-slots/{doctorId}`, `PATCH /time-slots/{id}/change-time`, `DELETE /time-slots/{id}` | Расписание врача |
| Поиск слотов | `GET /time-slots/available?date=2030-01-01` (+ `doctorId`, `specialityId`) | Свободные слоты на дату |
| Записи | `GET /appointments`, `GET /appointments/{id}`, `POST /appointments/book`, `PATCH /appointments/{id}/cancel`, `/complete`, `/no-show` | Бронирование и смена статуса |

### Пример

```bash
# Забронировать слот
curl -X POST http://localhost:8080/appointments/book \
  -H "Content-Type: application/json" \
  -d '{"patientId": 1, "timeSlotId": 1}'
```

Повторная попытка занять тот же слот вернёт `409 Conflict`.

## Ограничения

- Аутентификации и авторизации пока нет: API открыто, `patientId` передаётся в теле запроса.
- Автоматических тестов пока нет.
