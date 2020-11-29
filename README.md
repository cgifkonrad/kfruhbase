# kfruhbase
HBase-Projekt der KFRU für die Vorlesung Datenbanken

## XML-File auf lokale HBase-Datenbank hochladen

1. Port 8080 auf dem Docker-Container freigeben (vgl hierzu die Anleitung, die Vorab per Mail verteilt wurde)
2. Auf dem Docker-Container den Befehl hbase rest start ausführen, dies geschieht mit folgendem Shell-Befehl auf dem Host-Rechner:
```$ docker exec -it hbase hbase rest start```
3. Das Shell-Skript ausführen
4. Nun ist es möglich, die erste Aufgabengruppe zu bearbeiten. Die in den Aufgaben genannte Tabelle nennt sich ```countries```


## Google-Cloud-Bigtable
In der Google Cloud Shell kann der Cloud Bigtable über den Befehl cbt angesteuert werden. Dieser ist das Äquivalent zum hbase-Befehl auf Apache HBase.
