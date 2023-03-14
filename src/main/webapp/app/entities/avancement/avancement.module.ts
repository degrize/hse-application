import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvancementComponent } from './list/avancement.component';
import { AvancementDetailComponent } from './detail/avancement-detail.component';
import { AvancementUpdateComponent } from './update/avancement-update.component';
import { AvancementDeleteDialogComponent } from './delete/avancement-delete-dialog.component';
import { AvancementRoutingModule } from './route/avancement-routing.module';

@NgModule({
  imports: [SharedModule, AvancementRoutingModule],
  declarations: [AvancementComponent, AvancementDetailComponent, AvancementUpdateComponent, AvancementDeleteDialogComponent],
})
export class AvancementModule {}
