# FAQ:

1. При использовании **volumes** в **docker-compose** лучше монтировать папки из WSL, а не из Windows, иначе могут быть
   проблемы с правами доступа. Для этого надо использовать абсолютные пути в формате Linux,
   например `/docker-compose-keycloak` и вручную деплоить в WSL необходимые файлы/папки и назначать им права доступа.
   
   [//]: # (TODO Проверить монтирование с путями Windows, т.е. D:\java\prj\securDemo\docker\docker-compose-keycloak\postgresql_data)
   Если же использовать относительные пути, например `./docker-compose-keycloak`, то IDEA преобразует его в путь вида
   `/mnt/d/java/prj/securDemo/docker/docker-compose-keycloak` и у приложений внутри WSL будут проблемы с доступом.
   See https://github.com/docker-library/postgres/issues/116 , https://github.com/docker/for-win/issues/445

2. SSH
   1. Поднять SSH внутри WSL: `sudo service ssh start`.
   2. Или надо установить `systemd` в WSL `/etc/wsl.conf`:
      ```
      [boot]
      systemd=true
      ```
      See https://learn.microsoft.com/ru-ru/windows/wsl/wsl-config#wslconf
      И затем настроить примерно так: https://linuxconfig.org/ubuntu-20-04-ssh-server
   3. Узнать свой внешний IP: `echo $(wget -qO - https://api.ipify.org)`

3. Если при открытии Admin Console постоянно крутится "_Loading the admin console_",
   то надо в браузере открыть DevTools (**F12**), найти запрос с ошибкой, проверить, что URL в нем валидный.
   Т.е. при локальной разработке он должен быть прописан в `etc/hosts` и пинговаться.