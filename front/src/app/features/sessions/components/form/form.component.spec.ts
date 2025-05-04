import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { SessionApiService } from '../../services/session-api.service';
import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let mockRouter = { navigate: jest.fn(), url: '/sessions/create' };
  let mockSnackBar = { open: jest.fn() };

  const fakeSession = {
    id: '1',
    name: 'Yoga Flow',
    date: '2024-05-10',
    teacher_id: 2,
    description: 'Relax and stretch'
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  };

  const mockApiService = {
    detail: jest.fn().mockReturnValue(of(fakeSession)),
    create: jest.fn().mockReturnValue(of(fakeSession)),
    update: jest.fn().mockReturnValue(of(fakeSession)),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      declarations: [FormComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockApiService },
        { provide: MatSnackBar, useValue: mockSnackBar },
        { provide: Router, useValue: mockRouter },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        },
        {
          provide: TeacherService,
          useValue: { all: () => of([]) }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect non-admin users', () => {
    mockSessionService.sessionInformation.admin = false;
    const comp = fixture.componentInstance;
    comp.ngOnInit();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should initialize the form in create mode', () => {
    component.ngOnInit();
    expect(component.sessionForm?.controls['name']).toBeDefined();
  });

  it('should initialize the form in update mode and patch data', fakeAsync(() => {
    mockRouter.url = '/sessions/update/1';
    component.ngOnInit();
    tick();
    expect(mockApiService.detail).toHaveBeenCalledWith('1');
    expect(component.sessionForm?.value.name).toBe('Yoga Flow');
  }));

  it('should submit a new session', () => {
    component.onUpdate = false;
    component.sessionForm?.setValue({
      name: 'Yoga Flow',
      date: '2024-05-10',
      teacher_id: 2,
      description: 'Nice'
    });
    component.submit();
    expect(mockApiService.create).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
  });

  it('should update a session', () => {
    component.onUpdate = true;
    component['id'] = '1';
    component.sessionForm?.setValue({
      name: 'Yoga Flow',
      date: '2024-05-10',
      teacher_id: 2,
      description: 'Nice'
    });
    component.submit();
    expect(mockApiService.update).toHaveBeenCalledWith('1', expect.any(Object));
    expect(mockSnackBar.open).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 });
  });
});
