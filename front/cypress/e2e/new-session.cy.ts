/// <reference types="cypress" />

describe("Création d'une session (compte admin)", () => {
  it("doit se connecter et afficher le formulaire de création de session", () => {
    cy.visit('/login');

    cy.get('input[formControlName="email"]').type('shiirley57000@gmail.com');
    cy.get('input[formControlName="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    cy.location('pathname', { timeout: 10000 }).should('include', '/sessions');

    // Aller sur la page de création
    cy.visit('/sessions/create');

    cy.get('form', { timeout: 10000 }).should('exist');

    cy.get('input[formControlName="name"]').type('Session E2E');
    cy.get('textarea[formControlName="description"]').type('Session créée via test E2E');
    cy.get('input[formControlName="date"]').type('2025-06-30');

    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').first().click();

    cy.get('button[type="submit"]').click();

    cy.contains('Session created !', { timeout: 5000 }).should('exist');
    cy.location('pathname').should('include', '/sessions');
  });
});
