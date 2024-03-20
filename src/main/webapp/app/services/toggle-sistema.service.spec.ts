import { TestBed } from '@angular/core/testing';

import { ToggleSistemaService } from './toggle-sistema.service';

describe('ToggleSistemaService', () => {
  let service: ToggleSistemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToggleSistemaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
