import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RegleComponent } from './list/regle.component';
import { RegleDetailComponent } from './detail/regle-detail.component';
import { RegleUpdateComponent } from './update/regle-update.component';
import { RegleDeleteDialogComponent } from './delete/regle-delete-dialog.component';
import { RegleRoutingModule } from './route/regle-routing.module';

@NgModule({
  imports: [SharedModule, RegleRoutingModule],
  declarations: [RegleComponent, RegleDetailComponent, RegleUpdateComponent, RegleDeleteDialogComponent],
})
export class RegleModule {}
