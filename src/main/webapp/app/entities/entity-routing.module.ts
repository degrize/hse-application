import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'projet',
        data: { pageTitle: 'hseApplicationApp.projet.home.title' },
        loadChildren: () => import('./projet/projet.module').then(m => m.ProjetModule),
      },
      {
        path: 'regle',
        data: { pageTitle: 'hseApplicationApp.regle.home.title' },
        loadChildren: () => import('./regle/regle.module').then(m => m.RegleModule),
      },
      {
        path: 'signalement',
        data: { pageTitle: 'hseApplicationApp.signalement.home.title' },
        loadChildren: () => import('./signalement/signalement.module').then(m => m.SignalementModule),
      },
      {
        path: 'avancement',
        data: { pageTitle: 'hseApplicationApp.avancement.home.title' },
        loadChildren: () => import('./avancement/avancement.module').then(m => m.AvancementModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
