# Workout

## Приложение для составления тренировок в спортзале, отслеживания тренировочного прогресса.

### ok-202105-workout-kkh

Workout, Константин Хан

Ключевая идея: Приложение позволяет составить тренировочный протокол и помогает придерживаться системных занятий за счет
учета прогресса и системы оповещений (*последнее опционально в рамках учебного проекта*).

![img.png](https://github.com/otuskotlin/ok-202105-workout-kkh/blob/m2-init-project/workout%20training%20day.png)

### Базовая функциональность:

* фиксация антропометрических данных и хранение истории их изменений;
* база данных упражнений с возможностью добавления своих упражнений, в т.ч. на основе имеющихся;
* база данных тренировочных программ с описанием техники выполнения;
* наличие возможности добавления своей тренировочной программы, в т.ч. на основе имеющихся;
* тренировочные программы должны иметь возможность дополнительной настройки;
* наличие "Избранного" для быстрого доступа к упражнениям;
* тренировочный календарь с системой оповещений (*опционально в рамках учебного проекта*);
* наличие статистики выполнения тренировочных программ (учет веса отягощения, количества подходов и повторений на
  конкретную дату);
* таймер;
* мотивационные уведомления (*опционально в рамках учебного проекта*).

### Потенциальное развитие функциональности:

* возможность авторизации через соц. сети;
* социальная составляющая;
* наличие фотодневника;
* наличие дневника питания;
* Расчет индекса массы тела;
* интеграция с мессенджерами для обмена сообщениями и контентом;
* интеграция с соц. сетями для обмена сообщениями и контентом;
* интеграция с фитнес-приложениями (Google Fit, Health Fit, Mi Fit...);
* интеграция встроенного тренировочного календаря с Google Calendar;
* возможность загрузки на устройство только отдельных тренировочных программ;
* возможность загрузки на устройство только отдельных упражнений;
* возможность использования приложения без регистрации (должна быть ограниченная функциональность);
* автоматическая синхронизация данных с сервером;
* анализатор программ тренировок (проверка на принципы построения тренировочных программ);

### Функции

* CRUDS (create, read, update, delete, search) для:
    * упражнения - exercise;
    * тренировки - workout.
* workout.chainOfExercise - последовательность выполнений упражнений - необходимо при использовании сложных модификаций
  выполнения (дроп-сеты, супер-сеты, кластеры, без последнего, круговая);
* workout.notification - получение мотивационного уведомления (*опционально в рамках учебного проекта*).

### Описание сущности exercise

* title
* description;
* targetMuscleGroup;
* synergisticMuscleGroup;
* executionTechnique.

### Описание сущности workout

* date;
* duration;
* recoveryTime;
* exercises Map<Exercise, List\<Performance\>>

Performance - подход, выполнение

* status;
* sets List<weight, repetition> (Double, Double)
* modification;

status:

* PLAN
* ACTIVE
* DONE
* SKIP

modification:

* CIRCUIT
* CLASSIC
* CLUSTER
* DROP_SET
* SUPER_SET
* WITHOUT_LAST

### Портрет пользователя:

* Мужчина или женщина (*анатомия у всех одинаковая*).
* Возраст 23-55 лет.
* Высшее образование (*высокая вероятность, что работа не связана с тяжелым физическим трудом*).
* Имеет тренировочный стаж от 1 года с возможными перерывами.
* Пробовал/пробовала заниматься в зале/дома/на улице самостоятельно, в группах, с персональным тренером, но желаемого
  результата не достигли и/или не смог/не смогла сохранить результат.
* Имеет опыт участия в различных фитнес-марафонах и/или фитнес-проектах.
* Офисный работник, ведет сидячий образ жизни, имеет низкую бытовую активность.
* Недоволен/недовольна качеством своего тела и/или уровнем здоровья.
* Не имеет критических хронических заболеваний опорно-двигательного аппарата.
* Хочет похудеть/накачаться, чтобы произвести впечатление на свое окружение.
* Активный пользователь социальных сетей: Instagram, TikTok, VK, регулярно постит свои фотографии.
* В социальных сетях подписаны на блогеров из beauty-индустрии (диетологи, нутрициологи, психологи, проф. спортсмены,
  фитнес-модели и проч.).
* Тратит от 5000 рублей ежемесячно на товары и услуги из фитнес-индустрии.

### Ключевые потребности пользователей:

* Получить готовую систему организации тренировок в тренажерном зале, следование которой позволит получить и
  поддерживать на длительном периоде времени желаемое качество тела и уровень здоровья. Система должна обеспечивать:
    * составление корректных программ тренировок под цели пользователя (набор мышечной массы, похудение, жиросжигание,
      поддержка физической активности и проч.);
    * учет тренировочного объема с возможностью наблюдать свой прогресс и корректировать тренировочные протоколы;
    * возможность отмечать выполненный тренировочный объем, фиксировать время выполнения и отслеживать время отдыха
      между подходами в одном месте;
    * возможность гибкой коррекции тренировочных протоколов и набора доступных упражнений.
    * возможность быстрой и обоснованной замены упражнений в тренировочном протоколе;
    * дополнительную внешнюю мотивацию вести активный образ жизни - мотивационные уведомления (опционально в рамках
      учебного проекта).

### Монетизация: на начальном этапе необходима капитализация целевой аудитории, активно и регулярно использующей приложение для последующего предложения платных услуг:

* предоставление возможности сопровождения или консультирования - подписка / % со сделок по продаже услуг тренеров;
* предоставление автоматического анализа статистики / тренировочной программы (если функционал будет достаточно развит
  для обоснования ценности);
* предоставление доступа к расширенной функциональности приложения (из раздела на будущее) за подписку.

### Анализ конкурентов. Данные с сайтов:

* https://rskrf.ru/tips/obzory-i-topy/7-luchshikh-fitnes-prilozheniy-dlya-android/

* https://rskrf.ru/tips/obzory-i-topy/7-luchshikh-fitnes-prilozheniy-na-ios/

### На что обращают внимание пользователи:

Что нравится?                                                        | Что не нравится?
-------------------------------------------------------------------- | -------------
Большое количество программ тренировок                               | Необходимость авторизации при первом запуске
Поддержка Google Fit                                                 | Недостаточная простота использования
Детальное аудиосопровождение                                         | Отсутствие русификации
Возможность загрузки только отдельных тренировок                     | Средний уровень безопасности
Возможность создать свою тренировку                                  | Невозможность добавления упражнения или тренировок в избранное
Возможность добавлять упражнения в избранное                         | Отсутствие социальной составляющей
Наличие справочника упражнений                                       | Отсутствие аудиосопровождения
Возможность создать свое упражнение                                  | Невозможность создать свою тренировку
Продвинутая статистика                                               | Отсутствие справочника упражнений
Наличие экспорта данных                                              | Большой вес приложения
Отсутствие встроенных покупок                                        | Плохое качество русификации
Наличие раздела с полезными статьями                                 | Недостаточная адаптация для людей с ограниченными возможностями
Отсутствие рекламных материалов                                      | Демонстрация упражнений в виде отдельных изображений
Привлекательный дизайн                                               | Отсутствие автоматической синхронизации
Наличие программы тренировок с возможностью настройки                | Небольшое количество программ тренировок
Поддержка ориентаций                                                 | Долгое время запуска приложения
Возможность загрузки видео только отдельных упражнений               | Рекламные материалы, отвлекающие от основного контента
Наличие дневника питания                                             | Обязательная авторизация
Малый вес приложения                                                 | Невозможность планирования тренировки
Наличие фотодневника                                                 | Невозможность удаления скачанных видеоматериалов
Использование без регистрации                                        | Отсутствие мотивационной составляющей
Высокий уровень безопасности                                         | Отсутствие синхронизации данных
Простота использования                                               |
Расчет ИМТ                                                           |
Степень (ср., высок.) научности объяснения упражнений                |
Поддержка Health Kit («Здоровье»)                                    |
Виджет                                                               |
Грамотность описания упражнений                                      |
Поддержка 3D Touch                                                   |
Возможность удаления скачанного контента                             |
Видео для каждого упражнения с советами по выполнению                |
Деление упражнений по типам (базовый, продвинутый, профессиональный) |
Возможность исключить «шумные» упражнения                            |
Подробные описания упражнений и советы по их выполнению              |
