# Yoga App - Frontend

Application Angular pour la gestion de sessions de yoga.

## Installation du projet

1. Clonez le repository :
   ```bash
   git clone https://https://github.com/Shishifoxy/Testez-une-application-full-stack
   cd yoga-app/front
   ```

2. Installez les dépendances :
   ```bash
   npm install
   ```

## Lancer l'application

Pour démarrer le serveur de développement :
```bash
npm start
```

L'application sera disponible sur `http://localhost:4200`.

## Lancer les tests

### Tests unitaires et d’intégration (avec Jest)

```bash
npm test
```

Pour lancer les tests en mode watch :
```bash
npm run test:watch
```

### Tests end-to-end (avec Cypress)

Ouvrir l'interface Cypress :
```bash
npm run cypress:open
```

Lancer en mode headless :
```bash
npm run cypress:run
```

## Génération des rapports de couverture

Un rapport de couverture Jest peut être généré automatiquement après les tests :
```bash
npm test -- --coverage
```

Les résultats se trouvent dans le dossier `coverage/`.

Pour afficher le rapport dans le navigateur :
```bash
npx http-server ./coverage -o
```

## Objectif de couverture

- Couverture globale minimum : **80 %**
- Tests E2E : **30 % des tests**

## 📁 Structure du projet

```
/src
  ├── app
  │   ├── components
  │   ├── services
  │   ├── pages
  │   └── ...
  ├── assets
  └── environments
```

---

Ce projet utilise [Jest](https://jestjs.io/) pour les tests unitaires/intégration et [Cypress](https://www.cypress.io/) pour les tests E2E.
