/// <reference types="cypress" />

describe("Détail d'une session (vraie auth UI)", () => {
  it("doit se connecter puis afficher les détails de la session", () => {
    cy.visit('/login');

    // Connexion avec des identifiants réels
    cy.get('input[formControlName="email"]').type('shirley-bismuth@hotmail.fr');
    cy.get('input[formControlName="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    // On attend la redirection vers /sessions
    cy.location('pathname', { timeout: 10000 }).should('include', '/sessions');

    // Petite pause pour s’assurer que tout est chargé
    cy.wait(1000);

    // Visite la session avec un professeur valide
    cy.visit('/sessions/detail/151');

    // Vérifications visuelles
    cy.get('h1', { timeout: 10000 }).should('exist');
    cy.contains('Description:').should('exist');
    cy.get('mat-card-subtitle', { timeout: 10000 }).should('contain.text', 'Mario ROSSI');
  });
});
