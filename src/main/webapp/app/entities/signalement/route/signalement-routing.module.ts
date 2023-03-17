import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SignalementComponent } from '../list/signalement.component';
import { SignalementDetailComponent } from '../detail/signalement-detail.component';
import { SignalementUpdateComponent } from '../update/signalement-update.component';
import { SignalementRoutingResolveService } from './signalement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { UserManagementResolve } from '../../../admin/user-management/user-management.route';

const signalementRoute: Routes = [
  {
    path: '',
    component: SignalementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SignalementDetailComponent,
    resolve: {
      signalement: SignalementRoutingResolveService,
      user: UserManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SignalementUpdateComponent,
    resolve: {
      signalement: SignalementRoutingResolveService,
      user: UserManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SignalementUpdateComponent,
    resolve: {
      signalement: SignalementRoutingResolveService,
      user: UserManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(signalementRoute)],
  exports: [RouterModule],
})
export class SignalementRoutingModule {}
