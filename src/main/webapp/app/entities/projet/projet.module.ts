import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjetComponent } from './list/projet.component';
import { ProjetDetailComponent } from './detail/projet-detail.component';
import { ProjetUpdateComponent } from './update/projet-update.component';
import { ProjetDeleteDialogComponent } from './delete/projet-delete-dialog.component';
import { ProjetRoutingModule } from './route/projet-routing.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { AccordionModule } from 'primeng/accordion';

@NgModule({
  imports: [SharedModule, ProjetRoutingModule, NgSelectModule, AccordionModule],
  declarations: [ProjetComponent, ProjetDetailComponent, ProjetUpdateComponent, ProjetDeleteDialogComponent],
})
export class ProjetModule {}
