it('devrait afficher une erreur si l’inscription échoue', () => {
  cy.visit('/register');

  cy.get('input[formControlName="firstName"]').type('Test');
  cy.get('input[formControlName="lastName"]').type('User');
  cy.get('input[formControlName="email"]').type('shirley57000@gmail.com');
  cy.get('input[formControlName="password"]').type('test123');

  cy.get('button[type="submit"]').click();
  cy.contains('An error occurred').should('exist');
});
