import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeProjetsComponent } from './home-projets.component';

describe('HomeProjetsComponent', () => {
  let component: HomeProjetsComponent;
  let fixture: ComponentFixture<HomeProjetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeProjetsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeProjetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
