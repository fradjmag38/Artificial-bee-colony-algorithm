

# Artificial Bee Colony Algorithm (Java Implementation) 🐝

[![Java](https://img.shields.io/badge/Java-11+-blue?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

---

## Description
Ce projet est une **implémentation en Java de l'algorithme Artificial Bee Colony (ABC)**, un algorithme d’optimisation inspiré du comportement des abeilles.  
Il permet de modéliser et résoudre des problèmes d’optimisation via le déplacement et la communication des abeilles dans un environnement simulé.

---

## Structure du projet

ABC/
│
├─ src/
│  ├─ module-info.java
│  └─ projmod/
│     ├─ BeeColony.java
│     ├─ Plot2D.java
│     └─ ProjetModule.java
├─ bin/                     # Fichiers compilés .class
├─ .classpath
├─ .pom
└─ .gitlab-ci.yml
---

## Prérequis
- Java JDK 11 ou supérieur  
- IDE compatible Java (Eclipse, IntelliJ IDEA, VS Code)  

---

## Compilation et exécution
1. Ouvrir un terminal dans le dossier racine du projet `ABC`.
2. Compiler le projet :  
```bash
javac -d bin src/module-info.java src/projmod/*.java
````

3. Exécuter le projet :

```bash
java -cp bin projmod.ProjetModule
```
---

## Fonctionnalités

* Modélisation du comportement des abeilles pour l’optimisation.
* Visualisation 2D des résultats avec `Plot2D.java`.
* Projet structuré en modules Java pour faciliter l’extension.

---

## Auteur

Fradj Dorbez

---

## Licence

Ce projet est sous licence **MIT**. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.

---



