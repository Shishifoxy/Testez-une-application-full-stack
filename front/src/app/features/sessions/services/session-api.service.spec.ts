import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const mockSession: Session = {
    id: 1,
    name: 'Yoga',
    date: new Date('2024-01-01'),
    teacher_id: 1,
    description: 'Relaxing session',
    users: [],
    createdAt: new Date(),
    updatedAt: new Date()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });

    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all sessions', () => {
    service.all().subscribe((sessions) => {
      expect(sessions).toEqual([mockSession]);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush([mockSession]);
  });

  it('should create a new session', () => {
    service.create(mockSession).subscribe((res) => {
      expect(res).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    req.flush(mockSession);
  });

  it('should update a session', () => {
    service.update('1', mockSession).subscribe((res) => {
      expect(res).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockSession);
  });

  it('should delete a session', () => {
    service.delete('1').subscribe((res) => {
      expect(res).toEqual({});
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should get session detail', () => {
    service.detail('1').subscribe((res) => {
      expect(res).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });
});
