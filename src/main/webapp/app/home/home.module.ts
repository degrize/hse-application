import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { HeroPageComponent } from './hero-page/hero-page.component';
import { StyleClassModule } from 'primeng/styleclass';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { HomeProjetsComponent } from './home-projets/home-projets.component';
import { HomeSolutionsComponent } from './home-solutions/home-solutions.component';

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild([HOME_ROUTE]),
    StyleClassModule,
    IvyCarouselModule,
    CarouselModule,
    ButtonModule,
    ToastModule,
    RippleModule,
  ],
  declarations: [HomeComponent, HeroPageComponent, HomeProjetsComponent, HomeSolutionsComponent],
})
export class HomeModule {}
