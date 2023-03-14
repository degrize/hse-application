import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvancementComponent } from '../list/avancement.component';
import { AvancementDetailComponent } from '../detail/avancement-detail.component';
import { AvancementUpdateComponent } from '../update/avancement-update.component';
import { AvancementRoutingResolveService } from './avancement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const avancementRoute: Routes = [
  {
    path: '',
    component: AvancementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvancementDetailComponent,
    resolve: {
      avancement: AvancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvancementUpdateComponent,
    resolve: {
      avancement: AvancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvancementUpdateComponent,
    resolve: {
      avancement: AvancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avancementRoute)],
  exports: [RouterModule],
})
export class AvancementRoutingModule {}
