/// <reference types="cypress" />

describe("Infos utilisateur connecté (vraie auth UI)", () => {
  it("devrait afficher les infos de l’utilisateur connecté", () => {
    cy.visit('/login');

    cy.get('input[formControlName="email"]').type('shirley-bismuth@hotmail.fr');
    cy.get('input[formControlName="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    cy.location('pathname', { timeout: 10000 }).should('include', '/sessions');

    cy.wait(1000);

    cy.visit('/me');

    cy.get('h1', { timeout: 10000 }).should('contain.text', 'User information');
    cy.contains('Name:').should('exist');
    cy.contains('Email:').should('exist');
    cy.contains('Create at:').should('exist');
  });
});
