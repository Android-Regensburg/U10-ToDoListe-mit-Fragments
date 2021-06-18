# U10 | ToDo-Liste mit Fragmenten

## Aufgabe

Das Ziel dieser Aufgabe ist die Erweiterung der ToDo App aus dem 5. Übungsblatt. Die Anwendung soll dabei so erweitert werden, dass einzelne Layoutabschnitte ihrer App mithilfe von Fragmenten umgesetzt werden. Fragmente haben den Vorteil, dass sie dynamisch und einfach wiederverwendbar sind, was sich vor allem beim Erstellen eines User Interfaces für unterschiedliche Displaygrößen bemerkbar macht. In dieser Aufgabe werden sie daher die ToDo App so erweitern, dass sie nicht nur für Smartphones, sondern auch für Tablets ebenso gut benutzbar ist, indem die ToDo-Liste und die Detailansicht, je nach Auflösung, zusammen oder getrennt voneinander angezeigt werden. Einen Überblick über das finale Resultat erhalten sie in den unten beigefügten Screenshots.

## Hinweise

* Für die Umsetzung der dynamischen Anzeige werden mehrere [Fragments](https://developer.android.com/guide/components/fragments) verwendet. Das `TaskListFragment` ist für die Anzeige aller Tasks mithilfe einer RecyclerView zuständig. Das `DetailFragment` ermöglicht eine detailliertere Ansicht eines einzelnen ausgewählten Tasks. Das `CreateTaskDialogFragment` ist ein [DialogFragment](https://developer.android.com/guide/fragments/dialogs) zum Erstellen eines neuen Tasks.  

* Um die App sowohl auf dem Smartphone, als auch auf einem Tablet testen zu können, müssen Sie sich über den AVD-Manager ein passendes Tablet (z.B. Pixel C) einrichten. 

## Vorgaben
Im Startercode finden Sie den Lösungsvorschlag zum [5. Übungsblatt (Persistente ToDo-Liste)](https://android-regensburg.github.io/AssignmentViewer/index.html#Android-Regensburg/U05-Persistente-ToDo-Liste), sowie einige Ergänzungen:
- Überarbeitete Room-Datenbank, welche ihre Abragen nun in separaten Threads handhabt. Die Datenbank muss in dieser Aufgabe nicht verändert werden.
- Vorgefertigte XML-Layouts für die drei zu erstellenden Fragmente
- Zwei Versionen der activity_main.xml, eine für Smarphones und eine für größere Geräte (z.B. Tablets). Beachten Sie dabei, dass die jeweils geeignete Layout-Datei für das von Ihnen verwendete Gerät automatisch verwendet wird, sodass Sie sich darüber keine Gedanken machen müssen. Einen Überblick darüber, wie Sie selbst alternative Layouts für unterschiedliche Gerätegrößen definieren können, finden Sie [hier](https://developer.android.com/training/multiscreen/screensizes#alternative-layouts).

## Vorgehen

### Schritt 1

### Schritt 2


## Screenshots der Anwendung auf unterschiedlichen Gerättypen
