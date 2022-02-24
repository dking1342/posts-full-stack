import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortingButtonTrayComponent } from './sorting-button-tray.component';

describe('SortingButtonTrayComponent', () => {
  let component: SortingButtonTrayComponent;
  let fixture: ComponentFixture<SortingButtonTrayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SortingButtonTrayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SortingButtonTrayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
