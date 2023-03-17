import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';

import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { registerRoute } from './register/register.route';
import { settingsRoute } from './settings/settings.route';
import { Injectable } from '@angular/core';
import { IUser } from '../admin/user-management/user-management.model';
import { UserManagementService } from '../admin/user-management/service/user-management.service';
import { Observable, of } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

const ACCOUNT_ROUTES = [activateRoute, passwordRoute, passwordResetFinishRoute, passwordResetInitRoute, registerRoute, settingsRoute];

@Injectable({ providedIn: 'root' })
export class UserProfileResolve implements Resolve<IUser | null> {
  constructor(private service: UserManagementService, private accountService: AccountService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser | null> {
    return of(null);
  }
}

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES,
  },
];
