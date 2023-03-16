import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeSolutionsComponent } from './home-solutions.component';

describe('HomeSolutionsComponent', () => {
  let component: HomeSolutionsComponent;
  let fixture: ComponentFixture<HomeSolutionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeSolutionsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeSolutionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
