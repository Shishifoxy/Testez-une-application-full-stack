/// <reference types="cypress" />

describe('Liste des sessions', () => {
  it('devrait permettre d\'accéder à la liste des sessions', () => {
    cy.visit('/sessions');
    cy.get('mat-card').should('have.length.greaterThan', 0);
  });
});
