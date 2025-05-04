/// <reference types="cypress" />

Cypress.Commands.add('login', (email: string, password: string) => {
  cy.visit('/login');
  cy.get('input[formControlName="email"]').type(email);
  cy.get('input[formControlName="password"]').type(password);
  cy.get('button[type="submit"]').click();
});

