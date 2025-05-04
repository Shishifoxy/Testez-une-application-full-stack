import { expect } from '@jest/globals';
import { SessionService } from './session.service';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    service = new SessionService();
  });

  it('should start logged out', () => {
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should log in the user', () => {
    const mockUser = {
      id: 1,
      token: 'token',
      type: 'Bearer',
      username: 'user',
      firstName: 'first',
      lastName: 'last',
      admin: true
    };
    service.logIn(mockUser);
    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(mockUser);
  });

  it('should log out the user', () => {
    const mockUser = {
      id: 1,
      token: 'token',
      type: 'Bearer',
      username: 'user',
      firstName: 'first',
      lastName: 'last',
      admin: true
    };
    service.logIn(mockUser);
    service.logOut();
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });
});
