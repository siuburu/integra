import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardSistemaComponent } from './card-sistema.component';

describe('CardSistemaComponent', () => {
  let component: CardSistemaComponent;
  let fixture: ComponentFixture<CardSistemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardSistemaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CardSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
