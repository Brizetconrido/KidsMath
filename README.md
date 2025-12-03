#  KidsMath – Aplicación de Matemáticas para Niños

KidsMath es una aplicación educativa desarrollada en Android Studio diseñada para que los niños de 4 a 7 años aprendan matemáticas de forma divertida.  
Incluye juegos, puntajes, recompensas, perfil del niño y uso de hardware como cámara y micrófono.

---

##  Objetivo del Proyecto
Brindar a los niños una herramienta interactiva que les permita practicar operaciones matemáticas básicas mediante actividades entretenidas y motivación positiva.

---

##  Funcionalidades Principales

###  Juegos Matemáticos
- Sumás  
- Restas  
- Multiplicación  
- División  
- Contar objetos  
- Número faltante  
- Formas  
- Colores  

###  Sistema de Puntajes
- Se suma 1 punto por respuesta correcta.  
- Se guarda el historial en SQLite.  
- Se muestra puntaje por categoría y puntaje total.

### Sistema de Recompensas
- Al llegar a **30 puntos**, aparece una recompensa especial.  
- Incluye botón para que la mamá “borre la recompensa” sin borrar los puntos reales.  

###  Perfil del Niño
- Nombre  
- Edad  
- Avatar con foto tomada desde la cámara  

###  Uso de Micrófono
- Grabar y reproducir audio.  
- Lista de grabaciones guardadas.  

---

##  Arquitectura del Proyecto

### Activities principales:
- `MainMenuActivity`  
- `GameSelectionActivity`  
- `SumGameActivity`, `SubGameActivity`, `MultiGameActivity`, etc.  
- `RewardActivity`  
- `ProfileActivity`  
- `VoiceRecorderActivity`  
- `ScoreActivity`

### Lógica:
- `DatabaseManager.java` → control de operaciones  
- `DBHelper.java` → conexión SQLite  

### Base de Datos (SQLite):
Tablas utilizadas:
- **user** → datos del niño  
- **scores** → puntajes por juego  
- **rewards** → recompensas  

---

## Capturas de Pantalla

Agrega aquí tus imágenes (puedes subirlas a `/assets` o usar imágenes del repositorio).

---

## Tecnologías Utilizadas
- Android Studio (Java)
- XML (Interfaz)
- SQLite
- Android Camera API
- MediaRecorder (Micrófono)
- Git / GitHub

---

## Instalación

1. Clonar el repositorio:
2. git clone https://github.com/Brizetconrido/KidsMath.git
3.  Abrir en Android Studio  
4. Ejecutar desde un dispositivo o emulador  

---

##  Estado del Proyecto
 Proyecto completado  
Funciones implementadas  
Base de datos operativa  
Recompensas funcionando  
Rama `development` fusionada en `main`  

---

## Autor
**Brizet Conrido**  
Proyecto académico – Aplicación KidsMath  


