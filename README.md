# Getting Started

## TODO
+ add tests
+ add picture's upload
+ 




---
Проект  
+ master - небольшой интернет-магазин
+ dev ветка - добавление различного доп. функционала


###  Documentation

---
Бэк:
+ spring boot
+ spring security
+ mySQL
+ liquidbase
+ lombok
+ docker
+ swagger


### Guides information
Перед запуском
Указать VM Options параметр Secret.key, например так:
-Dsecret.key=o1JRppeF1JlCPQEunPd8Da7W3Yx4zK1o2XKY38LHe87NKmTejYL8ITdeephSfYTbf6Gw8cyQBeab6am71-pPRTqWxO4Qeg3IPcELwAQFaS0UwPjb_I8G7NYNWugl8Si867OdUkEK8FZL4zI3BOFSnVO8cZr8bj_yOiFr2PoKjcHTaKXzslQhI-MEZlWhd7ucCLGkgtWRZg1gg69JjVTYtRGebeByCaHrKarAFOzwSs4D05BrbdHfQBbknWGs8q3MqRjF2xJH_9QTh1UqC2TcNDQG3moiq209WiTBg2kBcbFBPAUr6dk1_oR_oZeU2QOa0hJfULDSmLy49Lod9P0mSQ

*Токен для авторизации -  CRM_HA + "сгенерированный токен" (через пробел): сам токен получить из token controller (в разворачиваемой бд прописан только "админ", остальные персонажи с ролями заполняются ручками если нужно)
** Создание НОВОГО пользователя незарегистрированным в системе пользователем (у админа такие возможности есть в User controller) - использовать external-user-controller^ 
создается новый External user с ограниченными правами (просмотр товаров, заполнение корзины (токены хранятся в бд)).


'User System admin@mail.ru' 
--159753CFThn
--0D05A319E82A1A461079CFEC354A3E00

swagger на 8080
http://localhost:8080/swagger-ui.html
