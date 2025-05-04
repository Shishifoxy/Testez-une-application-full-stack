/// <reference types="cypress" />

describe('Contrôle des accès admin', () => {
  it('devrait rediriger un utilisateur non-admin', () => {
    cy.login('user@email.com', 'user123');
    cy.visit('/sessions/create');
    cy.url().should('include', '/login');
  });
});
