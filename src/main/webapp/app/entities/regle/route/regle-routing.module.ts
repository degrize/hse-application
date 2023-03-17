import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RegleComponent } from '../list/regle.component';
import { RegleDetailComponent } from '../detail/regle-detail.component';
import { RegleUpdateComponent } from '../update/regle-update.component';
import { RegleRoutingResolveService } from './regle-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { UserManagementResolve } from '../../../admin/user-management/user-management.route';

const regleRoute: Routes = [
  {
    path: '',
    component: RegleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegleDetailComponent,
    resolve: {
      regle: RegleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegleUpdateComponent,
    resolve: {
      regle: RegleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegleUpdateComponent,
    resolve: {
      regle: RegleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(regleRoute)],
  exports: [RouterModule],
})
export class RegleRoutingModule {}
