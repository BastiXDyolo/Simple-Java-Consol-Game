# Simple-Java-Consol-Game


Aufgaben:

Ja — und ich würde dir jetzt bewusst keine Schritt-für-Schritt-Lösung geben. 😄
Die Aufgabe soll so sein, dass du einiges googeln musst und dabei lernst, wie man sich selbst durch ein Problem arbeitet.

🧙 Java-Konsolenprojekt: Dungeon-Manager

Du programmierst ein kleines textbasiertes Dungeon-Spiel.

Der Spieler besitzt eine Gruppe von Charakteren und muss gegen verschiedene Gegner kämpfen.

Dabei sollst du Klassen, Vererbung, Arrays, Schleifen, Methoden, ArrayList, enum, Zufallszahlen und Exceptions miteinander verbinden.


---

🎯 Ziel

Beim Start sieht das Programm ungefähr so aus:

=== DUNGEON MANAGER ===

1. Charaktere anzeigen
2. Charakter erstellen
3. Dungeon betreten
4. Inventar anzeigen
5. Programm beenden

Auswahl:

Der Benutzer kann sich durch das Programm bewegen.


---

1. 🧑 Charakter-System

Erstelle eine abstrakte Klasse:

Character

Sie soll mindestens besitzen:

Name
Health
MaxHealth
AttackDamage

Außerdem soll es mindestens drei Charaktertypen geben:

Warrior
Mage
Archer

Alle drei sollen von Character erben.

Aber jeder soll sich anders verhalten.

Beispielsweise:

Warrior → hoher Schaden, viel Leben
Mage    → wenig Leben, hoher Schaden
Archer  → mittlere Werte

Du entscheidest selbst über die Werte.

Schwierigkeit

Baue mindestens eine abstrakte Methode ein.

Zum Beispiel etwas in Richtung:

attack()

Jede Unterklasse soll ihre eigene Version davon besitzen.


---

2. ⚔️ Kampfsystem

Der Spieler kann einen Charakter auswählen.

Dann wird ein zufälliger Gegner erzeugt.

Auch Gegner sollen über Vererbung funktionieren.

Zum Beispiel:

Enemy
├── Zombie
├── Goblin
└── Dragon

Der Kampf könnte ungefähr so aussehen:

=== KAMPF ===

Dein Charakter:
Arthas
HP: 80 / 100

Gegner:
Goblin
HP: 35 / 35

1. Angreifen
2. Heilen
3. Fliehen

Auswahl:

Der Kampf läuft so lange, bis:

Spieler HP <= 0

oder

Gegner HP <= 0

oder der Spieler flieht.


---

3. 🎲 Zufällige Gegner

Der Gegner soll zufällig ausgewählt werden.

Beispielsweise:

Goblin
Zombie
Skeleton
Dragon

Du sollst selbst herausfinden, wie du aus mehreren Möglichkeiten zufällig eine auswählst.

💡 Google-Suchbegriffe wären zum Beispiel:

Java Random
Java random number array
Java Array zufälliges Element


---

4. 🧺 Charaktere speichern

Der Spieler soll mehrere Charaktere besitzen können.

Zum Beispiel:

Meine Charaktere:

[0] Arthas – Warrior
[1] Gandalf – Mage
[2] Legolas – Archer

Du sollst entscheiden, ob du dafür ein Array oder eine ArrayList verwendest.

Aber: Versuche zuerst selbst herauszufinden, warum eine ArrayList hier möglicherweise praktischer wäre.


---

5. ❤️ Heilung

Jeder Charakter soll sich heilen können.

Aber:

Health darf niemals größer als MaxHealth werden.

Beispiel:

HP: 70 / 100

Heile 50

HP: 100 / 100

Nicht:

HP: 120 / 100


---

6. 🎒 Inventar

Jeder Charakter soll ein kleines Inventar besitzen.

Zum Beispiel:

Inventar:

0: Heiltrank
1: Großer Heiltrank
2: Mana-Trank

Du kannst dafür zunächst einfache Strings verwenden.

Später kannst du daraus eine eigene Klasse machen:

Item
├── Potion
├── Weapon
└── Armor

Das ist optional für die erste Version.


---

7. 🏆 Erfahrungssystem

Nach einem gewonnenen Kampf bekommt der Charakter XP.

Zum Beispiel:

Goblin besiegt!

+25 XP

XP: 75 / 100

Wenn genug XP vorhanden sind:

LEVEL UP!

Arthas ist jetzt Level 2!

Dabei soll sich beispielsweise MaxHealth und AttackDamage erhöhen.


---

8. 💾 Speichern

Jetzt kommt der schwierigere Teil.

Wenn das Programm beendet wird:

Programm wird beendet...
Charaktere werden gespeichert...

Beim nächsten Start:

Charaktere werden geladen...

Die Daten sollen erhalten bleiben.

Hier darfst du definitiv googeln.

Suchbegriffe könnten sein:

Java Datei schreiben
Java Datei lesen
Java BufferedWriter
Java BufferedReader
Java Files.write
Java Files.readAllLines

Du musst nicht sofort eine perfekte Speicherlösung bauen.

Eine einfache Textdatei reicht.


---

9. 🛡️ Fehlerbehandlung

Dein Programm darf nicht sofort abstürzen, wenn jemand sowas eingibt:

Bitte Charakter auswählen:

> abc

oder:

> 999

oder:

> -5

Stattdessen:

Ungültige Eingabe!
Bitte versuche es erneut.

Hier darfst du deine Kenntnisse über try, catch und Exceptions einsetzen.


---

10. 🧠 Eine zusätzliche Herausforderung

Jetzt kommt der Teil, bei dem ich sehen möchte, wie du selbst denkst.

Erstelle eine Methode:

selectCharacter()

Die Methode soll dem Benutzer die vorhandenen Charaktere anzeigen und anschließend den ausgewählten Charakter zurückgeben.

Also ungefähr:

Character selected = selectCharacter();

Du musst selbst herausfinden:

Welchen Datentyp muss die Methode zurückgeben?


---

🔥 Bonus-Challenge

Wenn dir das noch nicht schwer genug ist:

Baue ein Klassensystem mit Fähigkeiten.

Zum Beispiel:

Warrior

1. Normaler Angriff
2. Heavy Strike

Mage

1. Normaler Angriff
2. Fireball

Archer

1. Normaler Angriff
2. Double Shot

Jetzt kannst du überlegen, wie du das programmierst, ohne überall sowas zu schreiben:

if (character instanceof Warrior) {
    ...
}

if (character instanceof Mage) {
    ...
}

if (character instanceof Archer) {
    ...
}

Versuche stattdessen deine Kenntnisse über Vererbung und Polymorphie zu benutzen.


---

🧩 Deine Regeln

Und jetzt kommt das Wichtigste für deine Übung:

❌ Nicht machen

Problem
↓
ChatGPT
↓
kompletten Code kopieren
↓
fertig

✅ So machen

Problem
↓
selbst überlegen
↓
Google
↓
Dokumentation / Stack Overflow / Beispiele
↓
Code verstehen
↓
selbst schreiben
↓
testen
↓
Fehler analysieren
↓
weiter

Und wenn du festhängst, kannst du mir deinen aktuellen Code und die Fehlermeldung zeigen.

Dann werde ich dir nicht sofort die fertige Lösung hinschreiben, sondern erstmal fragen bzw. erklären, wo dein Denkfehler liegt und welche Java-Funktion du wahrscheinlich brauchst.

Wenn du irgendwann sagst:

> „Ich habe jetzt 30 Minuten gesucht und komme absolut nicht weiter.“



Dann können wir gemeinsam einen Schritt zurückgehen und das Problem zerlegen.

Das ist meiner Meinung nach gerade die perfekte Übung für dich, weil du inzwischen weit über if, for und Arrays hinaus bist, aber noch nicht an einem Projekt arbeiten solltest, das 5.000 Zeilen Code benötigt. 😄