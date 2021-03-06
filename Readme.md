# U10 | ToDo-Liste mit Fragmenten

## Aufgabe

In dieser Aufgabe erweitern Sie die _ToDo App_ aus dem [5. Übungsblatt (Persistente ToDo-Liste)](https://android-regensburg.github.io/AssignmentViewer/index.html#Android-Regensburg/U05-Persistente-ToDo-Liste). Durch den bewussten Einsatz von [Fragments](https://developer.android.com/guide/fragments) werden die verschiedenen Funktionen der App auf separate UI-Komponenten aufteilt. Fragmente haben u.a. den Vorteil, dass sie dynamisch und einfach wiederverwendbar sind, was sich vor allem beim Erstellen eines adaptiven _User Interfaces_ für unterschiedliche Displaygrößen bemerkbar macht. In dieser Aufgabe optimieren Sie damit die bestehende App: Auf Smartphones werden die verschiedenen Bereiche des UIs auf unterschiedlichen Screens angezeigt, während auf einem Tablet mit größerem Bildschirm, der verfügbare Platz zur gleichzeitigen Anzeige aller Elemente ausgenutzt wird. Einen Überblick über das finale Resultat erhalten sie in den unten beigefügten Screenshots.

## Allgemeine Hinweise

Für die Umsetzung der dynamischen Anzeige sollen drei [Fragments](https://developer.android.com/guide/components/fragments) verwendet werden:

- Das `TaskListFragment` ist für die Anzeige aller Tasks mithilfe einer RecyclerView zuständig.
- Das `CreateTaskDialogFragment` ist ein [DialogFragment](https://developer.android.com/guide/fragments/dialogs) zum Erstellen eines neuen Tasks und kann über den FloatingActionButton des TaskListFragments aufgerufen werden.  
- Das `DetailFragment` ermöglicht eine detailliertere Ansicht eines einzelnen ausgewählten Tasks.

**Achtung**: Um die App sowohl auf dem Smartphone, als auch auf einem Tablet testen zu können, müssen Sie sich über den AVD-Manager ein passendes Tablet (z.B. Pixel C) einrichten. 

## Verwendung der Fragments in beiden Versionen (Smartphone und Tablet)
Auf dem **Smartphone** soll zunächst nur das `TaskListFragment` angezeigt werden. Sobald der Nutzer auf den _Add_-Button klickt, soll sich ein `DialogFragment` öffnen, über den die Nutzer\*innen einen neuen Task erstellen können. Ist dies erfolgreich, so soll der Task in der Datenbank gespeichert und in der `RecyclerView` angezeigt werden. Bei einem langen Klick auf ein Listenelement wird dessen Status umgeschaltet (_toggle_: offen vs. geschlossen). Über einen normalen Klick auf einen Task in der `RecyclerView` sollen die Nutzer\*innen in die Detailansicht gelangen. Dazu soll das aktuell angezeigte `TaskListFragment` durch das `DetailFragment` ersetzt werden und dort "Titel" und "Beschreibung" des ausgewählten Tasks anzeigen werden. 

Der grundlegende Aufbau der **Tablet**-Version bleibt gleich. Der wesentliche Unterschied besteht darin, dass das `TaskList`-Fragment und das `DetailFragment` gleichzeitig nebeneinander angezeigt werden und somit der Wechsel zwischen diesen erspart bleibt. 

## Vorgaben
Im Startercode finden Sie den Lösungsvorschlag zum [5. Übungsblatt (Persistente ToDo-Liste)](https://android-regensburg.github.io/AssignmentViewer/index.html#Android-Regensburg/U05-Persistente-ToDo-Liste). Gegenüber dem ursprünglichen Lösungsvorschlag haben wir folgende Änderungen bzw. Ergänzungen vorgenommen:
- Die Room-Datenbank arbeitet jetzt in separaten _Threads_. **Die Datenbank muss in dieser Aufgabe nicht verändert werden**.
- Neben dem originalen Layout für das _User Interface_ (activity_main_old.xml) befinden sich bereits drei zusätzliche, vorgefertigte XML-Layouts für die zu erstellenden Fragments im Projekt.
- Für beide Gerätetypen (Smartphone und Tablet) existiert außerdem jeweils eine eigene Version der neuen `activity_main.xml`-Datei. Beachten Sie dabei, dass die jeweils geeignete Layout-Datei für das von Ihnen verwendete Gerät automatisch anhand des vergebenen Dateinamens geladen wird, sodass Sie sich darüber keine Gedanken machen müssen. Einen Überblick darüber, wie Sie selbst alternative Layouts für unterschiedliche Gerätegrößen definieren können, finden Sie [hier](https://developer.android.com/training/multiscreen/screensizes#alternative-layouts).

## Vorgehen
Versuchen Sie zunächst, die reine Smartphone-App zu entwickeln. Sobald mit dieser Version alles funktioniert, kann anschließend durch Anpassung der `MainActivity` die Tablet-Variante eingebaut werden. 
Mit dem Startercode ist eine grundsätzlich funktionierende App vorgegeben. **Ihre Aufgabe ist es nun, auf Basis diesen Stands, die gegebene Funktionalität auf Fragments aufzuteilen (und teilweise zu erweitern) und den generellen Rahmen der Anwendung (Activity) entsprechend anzupassen.** Sie können sich dabei an folgenden Schritten orientieren:

### Implementieren des TaskListFragments
Erstellen Sie eine Klasse `TaskListFragment`, die von Fragment erbt. Laden Sie dann die passende XML-Layout-Datei, indem sie die passende Lifecycle-Methode des Fragments überschreiben. Wie auch schon aus den Activities bekannt, können Sie die benötigten Views aus der XML-Datei per ID im Code referenzieren. Die `RecyclerView` muss zudem auch noch mit dem `TaskListRecyclerAdapter` verknüpft werden, um die Inhalte entsprechend anzeigen zu können. Ergänzen Sie das Fragment zudem um eine öffentliche Methode, über die dem `RecyclerAdapter` eine neue Datenliste übergeben werden kann.

### Implementieren des Dialog-Fragments zur Erstellung von neuen Tasks
Erstellen Sie eine Klasse `CreateTaskDialogFragment`, die von `DialogFragment` erbt und laden Sie auch hier die passende Layout-Datei (siehe [hier](https://developer.android.com/guide/fragments/dialogs#custom)). Belegen Sie die beiden Buttons des Layouts mit Klick-Listenern und überlegen Sie sich sinnvolle Aktionen, die bei einem jeweiligen Klick ausgeführt werden sollen. Erstellen Sie zudem ein Listener-Interface, das es Ihnen ermöglicht, einen fertig erstellten Task an die `MainActivity` zu senden. 

Um die `MainActivity` innerhalb der `CreateTaskDialogFragment`-Klasse als Listener registrieren zu können, überschreiben Sie die onAttach()-Methode. Diese wird aufgerufen, wenn der Dialog an seinen Kontext (in diesem Fall die `MainActivity`) geheftet wird. Dem Listener kann dann der `context` Parameter zugewiesen werden (Hier müssen Sie den Datentypen der Activity lokal durch _Casting_ anpassen). 

Indem Sie eine neue Instanz der `CreateTaskDialogFragment`-Klasse erstellen und auf dieser die show() Methode aufrufen, können sie das DialogFragment anzeigen lassen. Überlegen Sie sich, an welcher Stelle in ihrem Code das sinnvoll ist.

### Implementieren des Detail-Fragments
Erstellen Sie eine Klasse `DetailFragment`, die von Fragment erbt. Laden Sie wie auch schon beim `TaskListFragment` zunächst die passende XML-Layout-Datei und referenzieren sie benötigte Views im Code. Das `DetailFragment` soll zudem über eine öffentliche Methode verfügen, die ein Task-Objekt übergeben bekommt und die Attribute (Titel und Description) von diesem in den entsprechenden Views anzeigt. 

### Einbinden der Fragmente in die MainActivity
Ersetzen Sie zunächst im Code das Laden der "alten" Layout-Datei ohne Fragmente durch die neuere Version. 
Erstellen Sie sich nun an den entsprechenden Stellen der `MainActivity` eine Instanz des `DetailFragments` und des `TaskListFragments`. Laden Sie das passende Fragment in den FragmentContainer des Layouts, damit es im UI angezeigt werden kann. 
Übergeben Sie dem `TaskListFragment` über seine öffentliche Methode an geeigneten Stellen die aktualisierte Task-Liste.
Überlegen Sie sich zudem, an welcher Stelle im Code es Sinn macht, das `TaskListFragment` durch das `DetailFragment` zu ersetzten und Implementieren sie diesen Mechanismus, indem sie den Inhalt des `FragmentContainers` ersetzten.

### Einbauen der Tablet-Variante
Nachdem die Smartphone Version nun lauffähig ist, können Sie durch entsprechende Erweiterungen in der `MainActivity` die Tablet-Variante mit einbauen. Beachten Sie, dass sie hier nicht nur einen FragmentContainer zur Verfügung haben, bei dem Sie das aktuelle Fragment immer mit dem neuen ersetzen müssen, sondern beide Fragmente sind gleichzeitig verfügbar. Passen Sie vor diesem Hintergrund die Reaktion Ihrer Anwendung auf die Auswahl eines Eintrags an. Unterscheiden Sie hier beide Fälle (z.B. über den _Check_ ob das `DetailFragment` aktuell verfügbar ist) und Ergänzen Sie eine Option, die den ausgewählten Task direkt im Fragment anzeigt, statt dieses zuerst zu erstellen bzw. anzuzeigen.

## Screenshots der Anwendung auf unterschiedlichen Gerättypen
### Smartphone
|  Tasklist auf dem Handy   |   Detailansicht auf dem Handy    |   Dialogfragment auf dem Handy    |
|:------:|:-------:|:-------:|
| ![Screenshots des UI am Handy](./docs/tasklist_phone.PNG)  | ![Screenshots des UI am Handy](./docs/detailansicht_phone.PNG)  |![Screenshots des UI am Handy](./docs/dialog_phone.PNG)  |

### Tablet
|  Taskliste und Detailansicht auf dem Tablet   |   Dialogfragment auf dem Tablet   |
|:------:|:-------:|
| ![Screenshots des UI am Tablet](./docs/ui_tablet.PNG)  | ![Screenshots des UI am Tablet](./docs/dialog_tablet.PNG)  |
