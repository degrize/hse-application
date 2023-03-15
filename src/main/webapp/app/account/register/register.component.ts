import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/config/error.constants';
import { RegisterService } from './register.service';

import villes from '../../../content/villes.json';
import { UserManagementService } from '../../admin/user-management/service/user-management.service';
import { Authority } from '../../config/authority.constants';
import { numeroCIValidator } from '../../shared/validators/valid.validator';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('nom', { static: false })
  login?: ElementRef;

  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;
  authorities: string[] = [];

  villesList: { ville: string }[] = villes;

  registerForm = new FormGroup({
    /*login: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),*/
    email: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
    confirmPassword: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),

    lastName: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
    }),
    firstName: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(2), Validators.maxLength(100)],
    }),
    contact: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        numeroCIValidator(),
        Validators.pattern('^((\\+225-?)|0)?[0-9]{10}$'),
        Validators.maxLength(10),
        Validators.minLength(10),
      ],
    }),
    ville: new FormControl('', {
      nonNullable: true,
      validators: [],
    }),
    communeOuQuartier: new FormControl('', {
      nonNullable: true,
      validators: [],
    }),
    authorities: new FormControl([], {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  mainForm!: FormGroup;
  contact!: FormControl;

  constructor(
    private translateService: TranslateService,
    private registerService: RegisterService,
    private userService: UserManagementService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
    this.initMainForm();
  }

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }
  }

  initMainForm(): void {
    this.mainForm = this.formBuilder.group({
      contact: this.contact,
    });
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const { password, confirmPassword } = this.registerForm.getRawValue();
    if (password !== confirmPassword) {
      this.doNotMatch = true;
    } else {
      let droits = [];
      droits.push(Authority.USER);
      const { email, lastName, firstName, contact, ville, communeOuQuartier, authorities } = this.registerForm.getRawValue();
      // console.log({login, email, lastName, firstName, contact, ville, communeOuQuartier, authorities})
      droits.push(authorities.toString());
      // console.log(droits);
      this.registerService
        .save({
          login: email,
          email,
          password,
          langKey: this.translateService.currentLang,
          lastName,
          firstName,
          contact,
          ville,
          communeOuQuartier,
          authorities: droits,
        })
        .subscribe({ next: () => (this.success = true), error: response => this.processError(response) });
    }
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
