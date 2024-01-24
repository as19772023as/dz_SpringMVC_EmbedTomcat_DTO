### 1. Spring Web MVC с Embed Tomcat

### 2. Данные не удаляются

**Легенда**

Самое плохое, что можно сделать с пользовательскими данными, — это безвозвратно их удалить. Пользователи позже 
всегда звонят с просьбой их восстановить и утверждают, что они точно ничего сами не удаляли. Поэтому чаще 
всего пользовательские данные не удаляют, а помечают на удаление, т. е. добавляют какое-то поле, например, 
removed.

Для простоты мы будем считать, что /api/posts — это API для клиентов, где они не смогут реализовать 
восстановление удалённых постов и даже просмотреть удалённые посты. Для этого позже будет отдельное API.

Получается, что removeById всего лишь выставляет это поле. Работа остальных методов тоже кардинально меняется:

- all возвращает все посты, кроме тех, у которых выставлен флаг removed;
- getById возвращает пост, только если у него не выставлен флаг removed, в противном случае кидает NotFoundException*;
- save обновляет существующий пост, только если у него не выставлен флаг removed, в противном случае кидает NotFoundException*.

Примечание.* Здесь нет идеального решения, разные люди могут вам говорить, что так можно или так нельзя и т. 
д. Мы же лишь скажем, что любая категоричность — это всегда плохо, и вы должны понимать, что возможны разные 
варианты. Всё зависит от того, какое решение примет проектировщик API или команда.

Остаётся единственный вопрос со статус-кодами. По логике, NotFoundException должен приводить к статус-коду 
404 Изучите документацию на @ResponseStatus и подумайте, как её применить для выставления статус-кода 404 при 
возникновении указанного нами Exception.
- Использовать её нужно в формате @ResponseStatus(code = HttpStatus.NOT_FOUND), при этом импортировать и 
ResponseStatus, и HttpStatus.

Решение по тому, на каком именно слое реализовать эту логику, остаётся за вами. Но это точно должен быть не Controller.

Обратите внимание, что вся функциональность (CRUD), реализованная до этого, должна по-прежнему работать.