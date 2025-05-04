/// <reference types="cypress" />

describe("Détail d'une session (vraie auth UI)", () => {
  it("doit se connecter puis afficher les détails de la session", () => {
    cy.visit('/login');

    cy.get('input[formControlName="email"]').type('shirley-bismuth@hotmail.fr');
    cy.get('input[formControlName="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    cy.location('pathname', { timeout: 10000 }).should('include', '/sessions');

    cy.wait(1000);

    cy.visit('/sessions/detail/151');

    cy.get('h1', { timeout: 10000 }).should('exist');
    cy.contains('Description:').should('exist');
    cy.get('mat-card-subtitle', { timeout: 10000 }).should('contain.text', 'Mario ROSSI');
  });
});
