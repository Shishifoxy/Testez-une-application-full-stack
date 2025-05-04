/// <reference types="cypress" />

describe('Page d\'accueil', () => {
  it('devrait afficher la page d\'accueil avec les sessions', () => {
    cy.visit('/');
    cy.get('mat-toolbar')
      .should('exist')
      .contains('Yoga app');
    cy.contains('Login').should('exist');
    cy.contains('Register').should('exist');
    it('devrait afficher les liens de navigation quand connecté', () => {
      cy.login('test@example.com', 'password');

      cy.visit('/');

      cy.contains('Sessions').should('exist');
      cy.contains('Account').should('exist');
      cy.contains('Logout').should('exist');
    });
  });
});
