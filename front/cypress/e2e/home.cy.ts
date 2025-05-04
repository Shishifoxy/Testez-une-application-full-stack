/// <reference types="cypress" />

describe('Page d\'accueil', () => {
  it('devrait afficher la page d\'accueil avec les sessions', () => {
    // Visit the home page
    cy.visit('/');

    // Verify the toolbar exists with app name
    cy.get('mat-toolbar')
      .should('exist')
      .contains('Yoga app');

    // Verify navigation links for non-logged in users
    cy.contains('Login').should('exist');
    cy.contains('Register').should('exist');

    // If you want to test the logged-in state
    it('devrait afficher les liens de navigation quand connecté', () => {
      // First login (you'll need to implement this command)
      cy.login('test@example.com', 'password');

      cy.visit('/');

      // Verify navigation links for logged-in users
      cy.contains('Sessions').should('exist');
      cy.contains('Account').should('exist');
      cy.contains('Logout').should('exist');
    });
  });
});
