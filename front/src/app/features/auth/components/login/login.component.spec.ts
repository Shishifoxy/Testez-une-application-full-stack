import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule, FormGroup } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of, throwError } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;
  let sessionService: SessionService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
      imports: [
        RouterTestingModule.withRoutes([{ path: 'sessions', redirectTo: '' }]),
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values', () => {
    expect(component.form).toBeInstanceOf(FormGroup);
    expect(component.form.value).toEqual({
      email: '',
      password: ''
    });
  });

  it('should have required validators for email and password', () => {
    const emailControl = component.form.get('email');
    const passwordControl = component.form.get('password');

    emailControl?.setValue('');
    passwordControl?.setValue('');

    expect(emailControl?.valid).toBeFalsy();
    expect(passwordControl?.valid).toBeFalsy();
    expect(emailControl?.errors?.['required']).toBeTruthy();
    expect(passwordControl?.errors?.['required']).toBeTruthy();
  });

  it('should have email validator', () => {
    const emailControl = component.form.get('email');
    emailControl?.setValue('invalid-email');
    expect(emailControl?.errors?.['email']).toBeTruthy();
  });

  it('should have min length validator for password', () => {
    const passwordControl = component.form.get('password');
    passwordControl?.setValue('12');
    expect(passwordControl?.errors?.['minlength']).toBeTruthy();
  });

  it('should toggle password visibility', () => {
    expect(component.hide).toBe(true);
    component.hide = false;
    fixture.detectChanges();
    expect(component.hide).toBe(false);
  });

  it('should call authService.login and navigate on successful login', () => {
    const loginRequest = { email: 'test@test.com', password: 'password' };
    const sessionInfo: SessionInformation = {
      token: 'token',
      username: 'test',
      id: 1,
      type: 'user',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };

    component.form.setValue(loginRequest);

    const authServiceSpy = jest.spyOn(authService, 'login').mockReturnValue(of(sessionInfo));
    const routerSpy = jest.spyOn(router, 'navigate');
    jest.spyOn(console, 'warn').mockImplementation();
    component.submit();

    expect(authServiceSpy).toHaveBeenCalledWith(loginRequest);
    expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set onError to true when login fails', () => {
    jest.spyOn(authService, 'login').mockReturnValue(throwError(() => new Error('')));
    component.submit();
    expect(component.onError).toBe(true);
  });

  it('should disable submit button when form is invalid', () => {
    component.form.setValue({ email: '', password: '' });
    fixture.detectChanges();

    const submitButton = fixture.nativeElement.querySelector('button[type="submit"]');
    expect(submitButton.disabled).toBe(true);

    component.form.setValue({ email: 'test@test.com', password: 'password' });
    fixture.detectChanges();
    expect(submitButton.disabled).toBe(false);
  });

  it('should show error message when onError is true', () => {
    component.onError = true;
    fixture.detectChanges();

    const errorMessage = fixture.nativeElement.querySelector('.error');
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toContain('An error occurred');

    component.onError = false;
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('.error')).toBeFalsy();
  });
});
