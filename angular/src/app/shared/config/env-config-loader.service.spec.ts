/* eslint-disable no-unused-vars, @typescript-eslint/no-unused-vars */

import { TestBed, inject } from '@angular/core/testing';
import { EnvConfigLoaderService } from './env-config-loading.service';

describe('Service: Loading', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EnvConfigLoaderService]
    });
  });

  it('should ...', inject([EnvConfigLoaderService], (service: EnvConfigLoaderService) => {
    expect(service).toBeTruthy();
  }));
});
