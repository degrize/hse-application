import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SignalementComponent } from './list/signalement.component';
import { SignalementDetailComponent } from './detail/signalement-detail.component';
import { SignalementUpdateComponent } from './update/signalement-update.component';
import { SignalementDeleteDialogComponent } from './delete/signalement-delete-dialog.component';
import { SignalementRoutingModule } from './route/signalement-routing.module';

@NgModule({
  imports: [SharedModule, SignalementRoutingModule],
  declarations: [SignalementComponent, SignalementDetailComponent, SignalementUpdateComponent, SignalementDeleteDialogComponent],
})
export class SignalementModule {}
